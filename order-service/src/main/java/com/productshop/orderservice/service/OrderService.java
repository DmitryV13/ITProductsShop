package com.productshop.orderservice.service;

import com.productshop.orderservice.dto.OrderItemDto;
import com.productshop.orderservice.dto.OrderRequest;
import com.productshop.orderservice.model.Order;
import com.productshop.orderservice.model.OrderItem;
import com.productshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        
        List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream()
                .map(e -> mapToModel(e))
                .toList();
        
        order.setOrderItemList(orderItemList);
        orderRepository.save(order);
    }
    
    private OrderItem mapToModel(OrderItemDto orderItemDto){
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setSkuCode(orderItemDto.getSkuCode());
        return orderItem;
    }
}
