package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.impl;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.OrderStatusDTO;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories.ProductionOrderRepository;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.ProductionOrderService;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.event.send.SendOrderStatusMessage;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.util.JsonMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductionOrderServiceImpl implements ProductionOrderService {

    private final SendOrderStatusMessage sendOrderStatusMessage;
    private final ProductionOrderRepository productionOrderRepository;

    public ProductionOrderServiceImpl(ProductionOrderRepository productionOrderRepository, SendOrderStatusMessage sendOrderStatusMessage) {
        this.productionOrderRepository = productionOrderRepository;
        this.sendOrderStatusMessage = sendOrderStatusMessage;
    }

    @Override
    public ProductionOrder updateStatus(String orderId, Integer status) {
        try {
            var order = productionOrderRepository.findByOrderNumber(orderId);
            if (order == null) {
                throw new RuntimeException("Order not found");
            }

            order.setOrderStatus(status);
            ProductionOrder saved = productionOrderRepository.save(order);
            if (saved != null) {
                log.info("Order {} updated to status {}", orderId, status);
                sendOrderStatusMessage.sendOrderStatusMessage(orderId, status);
                return saved;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Object findByOrderNumber(String orderId) {
        return productionOrderRepository.findByOrderNumber(orderId);
    }

    @Override
    public Object findByStatus(Long status) {
        return productionOrderRepository.findByOrderStatus(status);
    }

    @Override
    public Object findAll() {
        return productionOrderRepository.findAll();
    }

}