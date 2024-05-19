package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;

public interface ProductionOrderService {
    ProductionOrder updateStatus(String orderId, Integer status);

    Object findByOrderNumber(String orderId);

    Object findByStatus(Long status);

    Object findAll();
}
