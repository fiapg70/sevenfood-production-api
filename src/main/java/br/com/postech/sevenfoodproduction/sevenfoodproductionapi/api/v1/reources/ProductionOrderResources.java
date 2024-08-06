package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.api.v1.reources;

import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.api.v1.dto.OrderStatus;
import br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.service.ProductionOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/status-production")
public class ProductionOrderResources {

    public final ProductionOrderService productionOrderService;

    public ProductionOrderResources(ProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> update(@PathVariable final String orderId, @RequestBody OrderStatus orderstatus) {
        var orderSaved = productionOrderService.updateStatus(orderId, orderstatus.status());
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
