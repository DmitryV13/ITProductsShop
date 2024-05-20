package com.productshop.orderservice.service;

import com.productshop.orderservice.dto.InventoryResponse;
import com.productshop.orderservice.dto.OrderItemDto;
import com.productshop.orderservice.dto.OrderRequest;
import com.productshop.orderservice.model.Order;
import com.productshop.orderservice.model.OrderItem;
import com.productshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.UUID;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public String placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream()
                .map(e -> mapToModel(e))
                .toList();
        
        order.setOrderItemList(orderItemList);
        
        List<String> skuCodes = order.getOrderItemList().stream().map(OrderItem::getSkuCode).toList();
        
        InventoryResponse[] results = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        
        Boolean allInStock = Arrays.stream(results).allMatch(InventoryResponse::isInStock);
        
        if(allInStock){
            orderRepository.save(order);
            return "Your order was placed!";
        }
        else{
            throw new IllegalArgumentException("Product is not in stock, please try again later!");
        }
    }
    
    private OrderItem mapToModel(OrderItemDto orderItemDto){
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setSkuCode(orderItemDto.getSkuCode());
        return orderItem;
    }
}
