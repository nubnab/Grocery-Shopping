package com.grocery.backend.services;

import com.grocery.backend.dto.OrderStatusDto;
import com.grocery.backend.entities.Order;
import com.grocery.backend.exceptions.ResourceNotFoundException;
import com.grocery.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailed(Order order) {
        orderRepository.save(order);
    }

    public OrderStatusDto findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Order not found with id: " + id));

        return new OrderStatusDto(order.getId(), order.getOrderStatus());
    }


}



