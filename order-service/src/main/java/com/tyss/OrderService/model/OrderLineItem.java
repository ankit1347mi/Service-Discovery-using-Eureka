package com.tyss.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem {
    private String orderId;
    private String skuCode;
    private double price;
    private int quantity;
}
