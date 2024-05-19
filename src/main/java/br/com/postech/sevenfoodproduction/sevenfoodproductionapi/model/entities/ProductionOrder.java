package br.com.postech.sevenfoodproduction.sevenfoodproductionapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Tag(name = "Resident object")
@Document("production_order")
public class ProductionOrder {
    @Id
    private String id;
    @Field("order_number")
    private String orderNumber;
    @Field("client_id")
    private String clientId;
    @Field("order_date")
    private String orderDate;
    @Field("order_status")
    private Integer orderStatus;
    @Field("order")
    private String order;
    @Field("total_price")
    private BigDecimal totalPrice;
    @Field("products")
    private String products;
}
