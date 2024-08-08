package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.event.send;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.OrderStatusDTO;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.util.JsonMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendOrderStatusOrderStatusMessageImpl implements SendOrderStatusMessage {

    @Value("${app.queue-sqs.name}")
    private String queueName;

    @Value("${app.queue-status-sqs.name}")
    private String queueStatusName;

    private final SqsTemplate sqsTemplate;

    @PostConstruct
    public void init() {
        log.info("Queue Name: {}", queueName);
        log.info("Queue Status Name: {}", queueStatusName);
    }

    public SendOrderStatusOrderStatusMessageImpl(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendOrderStatusMessage(String  orderId, Integer status) {
        try {
            OrderStatusDTO orderStatus = OrderStatusDTO.builder()
                    .orderId(orderId)
                    .statusPedido(status)
                    .build();

            String orderStatusMessage = JsonMapperUtil.convertToJson(orderStatus);
            log.info("OrderPreparationService.listen {}", orderStatusMessage);
            sendMessage(orderStatusMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendMessage(String message) {
        sqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue(queueStatusName)
                                .payload(message)
                );
    }
}
