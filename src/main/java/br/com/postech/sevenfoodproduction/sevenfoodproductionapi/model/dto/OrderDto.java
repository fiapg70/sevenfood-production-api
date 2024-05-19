package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Restaurant object")
public class OrderDto {
    private Long id;
    private String code;
    private String clientId;
    private List<ProductDto> products;
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;
    private BigDecimal totalPrice;
}
