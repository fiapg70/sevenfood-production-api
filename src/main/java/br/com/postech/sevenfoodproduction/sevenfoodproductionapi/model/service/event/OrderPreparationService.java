package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.event;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.OrderDto;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.OrderStatusDTO;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.StatusPedido;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories.ProductionOrderRepository;
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
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class OrderPreparationService {

    @Value("${app.queue-sqs.name}")
    private String queueName;

    @Value("${app.queue-status-sqs.name}")
    private String queueStatusName;

    private final SqsTemplate sqsTemplate;
    private final ProductionOrderRepository productionOrderRepository;

    @Autowired
    public OrderPreparationService(SqsTemplate sqsTemplate, ProductionOrderRepository productionOrderRepository) {
        this.sqsTemplate = sqsTemplate;
        this.productionOrderRepository = productionOrderRepository;
    }

    @SqsListener(value = "${app.queue-sqs.name}")
    public void listen(String payload, @Headers Map<String, Object> header, Acknowledgement acknowledgement) {
        try {
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
                    .products(orderStatusDTO.getProducts().toString()) //TODO -vericicar como fazer com a lista de produtos
                    .build();

            ProductionOrder save = productionOrderRepository.save(order);
            OrderStatusDTO orderStatus = OrderStatusDTO.builder()
                    .orderId(save.getOrderNumber())
                    .statusPedido(save.getOrderStatus())
                    .build();

            String orderStatusMessage = objectMapper.writeValueAsString(orderStatus);
            log.info("OrderPreparationService.listen {}", orderStatusMessage);
            sendMessage(orderStatusMessage);
            // Acknowledge the message after successful processing
            acknowledgement.acknowledge();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        sqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue(queueStatusName)
                                .payload(message)
                );
    }


}