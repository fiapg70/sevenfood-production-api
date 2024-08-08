package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.event.send;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SendOrderStatusMessage {
    void sendOrderStatusMessage(String  orderId, Integer status) throws JsonProcessingException;
}
