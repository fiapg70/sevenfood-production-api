package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.api.v1.reources;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto.StatusPedido;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities.ProductionOrder;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.repositories.ProductionOrderRepository;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.ProductionOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status-production")
public class ProductionOrderResources {

    public final ProductionOrderService productionOrderService;

    public ProductionOrderResources(ProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
    }

    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<?> update(@PathVariable final String orderId, @PathVariable final Long status) {
        var orderSaved = productionOrderService.updateStatus(orderId, status);
        if(orderSaved == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderSaved);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> find(@PathVariable final String orderId) {
        var order = productionOrderService.findByOrderNumber(orderId);
        if(order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable final Long status) {
        var orders = productionOrderService.findByStatus(status);
        if(orders == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        var orders = productionOrderService.findAll();
        if(orders == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }
}
