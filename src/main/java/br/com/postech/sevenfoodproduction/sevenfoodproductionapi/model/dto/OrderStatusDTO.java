package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Restaurant object")
public class OrderStatusDTO {
    private String orderId;
    private Integer statusPedido;
}
