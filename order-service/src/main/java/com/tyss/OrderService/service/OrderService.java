package com.tyss.OrderService.service;

import com.tyss.OrderService.dto.InventoryResponse;
import com.tyss.OrderService.dto.OrderLineItemListDto;
import com.tyss.OrderService.dto.OrderRequest;
import com.tyss.OrderService.model.Order;
import com.tyss.OrderService.model.OrderLineItem;
import com.tyss.OrderService.repo.OrderRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final WebClient.Builder webClientBuilder;

    private final OrderRespository orderRespository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        if (orderRequest.getOrderLineItemLists() != null) {
            List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemLists().stream().map(this::mapToDto).toList();
            order.setOrderLineItemList(orderLineItems);
        }
        List<String> skuCodes;
        List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();
        if (orderLineItemList != null) {
            skuCodes = orderLineItemList.stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();
        } else {
            skuCodes = null;
        }
        //call inventory service and place order if product is in stock
        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allproduct = Arrays.stream(inventoryResponsArray).allMatch(InventoryResponse::isInStock);
        if (allproduct) {
            orderRespository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock , Please try again later");
        }
    }

    private OrderLineItem mapToDto(OrderLineItemListDto orderLineItemListDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemListDto.getPrice());
        orderLineItem.setQuantity(orderLineItemListDto.getQuantity());
        orderLineItem.setSkuCode(orderLineItemListDto.getSkuCode());
        return orderLineItem;
    }
}
