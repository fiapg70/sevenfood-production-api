package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionOrderRepository extends MongoRepository<ProductionOrder, String>{
    ProductionOrder findByOrderNumber(final String orderNumber);
    Object findByOrderStatus(Long status);
}