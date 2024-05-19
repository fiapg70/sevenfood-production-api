package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.impl;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories.ProductionOrderRepository;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.ProductionOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductionOrderServiceImpl implements ProductionOrderService {

    public final ProductionOrderRepository productionOrderRepository;

    public ProductionOrderServiceImpl(ProductionOrderRepository productionOrderRepository) {
        this.productionOrderRepository = productionOrderRepository;
    }

    @Override
    public ProductionOrder updateStatus(String orderId, Integer status) {
        var order = productionOrderRepository.findByOrderNumber(orderId);
        if(order == null) {
            log.error("Order not found"); //TODO - Colocar um throw exception
            return null;
        }
        order.setOrderStatus(status);
        ProductionOrder saved = productionOrderRepository.save(order);
        return saved;
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