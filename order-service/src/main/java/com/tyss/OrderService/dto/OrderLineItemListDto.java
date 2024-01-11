package com.tyss.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemListDto {

    private String orderId;
    private String skuCode;
    private double price;
    private int quantity;
}
