package com.grocery.backend.services;

import com.grocery.backend.dto.OrderDto;
import com.grocery.backend.entities.Order;
import com.grocery.backend.entities.OrderItem;
import com.grocery.backend.entities.Product;
import com.grocery.backend.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderCreationService {

    private final ProductService productService;
    private final OrderService orderService;

    @Transactional
    public void create(List<OrderDto> productOrderList) {

        Map<String, Integer> preparedOrder = groupAndNormalize(productOrderList);

        List<Product> products = productService.validateAndFetchProducts(preparedOrder);

        productService.updateStock(products, preparedOrder);

        //TODO: update order status based on result
        Order order = Order.builder()
                .orderStatus(OrderStatus.SUCCESS)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();

        for (Product product : products) {
            String normalizedName = product.getNormalizedName();
            int quantity = preparedOrder.get(normalizedName);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        orderService.save(order);
    }


    private Map<String, Integer> groupAndNormalize(List<OrderDto> productOrderList){
        Map<String, Integer> grouped = new HashMap<>();

        for (OrderDto orderDto : productOrderList) {
            String normalized = productService.normalizeProductName(orderDto.productName());
            grouped.merge(normalized, orderDto.quantity(), Integer::sum);
        }
        return grouped;
    }
}
