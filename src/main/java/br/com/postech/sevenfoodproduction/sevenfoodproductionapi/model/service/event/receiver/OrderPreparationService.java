package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.event.receiver;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.exception.ResourceFoundException;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.OrderDto;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.OrderStatusDTO;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.StatusPedido;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories.ProductionOrderRepository;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.event.send.SendOrderStatusMessage;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.util.JsonMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class OrderPreparationService {


    private final SendOrderStatusMessage sendOrderStatusMessage;
    private final ProductionOrderRepository productionOrderRepository;

    @Autowired
    public OrderPreparationService(SendOrderStatusMessage sendOrderStatusMessage, ProductionOrderRepository productionOrderRepository) {
        this.sendOrderStatusMessage = sendOrderStatusMessage;
        this.productionOrderRepository = productionOrderRepository;
    }

    @SqsListener(value = "${app.queue-sqs.name}")
    public void listen(Message<?> message, @Headers Map<String, Object> header, Acknowledgement acknowledgement) {
        try {
            String payload = String.valueOf(message.getPayload());
            log.info("OrderPreparationService.listen {}", payload);
            ObjectMapper objectMapper = JsonMapperUtil.getObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            OrderDto orderStatusDTO = objectMapper.readValue(payload, OrderDto.class);
            ProductionOrder order = ProductionOrder.builder()
                    .id(UUID.randomUUID().toString())
                    .orderNumber(orderStatusDTO.getCode())
                    .clientId(orderStatusDTO.getClientId())
                    .orderStatus(StatusPedido.EM_PREPARACAO.getCode())
                    .totalPrice(orderStatusDTO.getTotalPrice())
                    .products(orderStatusDTO.getProducts())
                    .build();

            ProductionOrder byOrderNumber = productionOrderRepository.findByOrderNumber(orderStatusDTO.getCode());
            if (byOrderNumber != null) {
                throw new ResourceFoundException("Order já criada: {" + byOrderNumber.getOrderNumber() + "}");
            } else {
                ProductionOrder save = productionOrderRepository.save(order);
                sendOrderStatusMessage.sendOrderStatusMessage(save.getOrderNumber(), save.getOrderStatus());
            }
            acknowledgement.acknowledge();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ResourceFoundException e) {
            log.error("Order já foi enviada para a cozinha. {}", e.getMessage());
        }
    }
}