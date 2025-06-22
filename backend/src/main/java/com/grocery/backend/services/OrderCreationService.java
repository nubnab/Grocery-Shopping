package com.grocery.backend.services;

import com.grocery.backend.dto.OrderDto;
import com.grocery.backend.embeds.Location;
import com.grocery.backend.entities.Order;
import com.grocery.backend.entities.OrderItem;
import com.grocery.backend.entities.Product;
import com.grocery.backend.entities.Route;
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
    private final RouteService routeService;

    @Transactional
    public void create(List<OrderDto> productOrderList) {

        Order order = Order.builder()
                .orderStatus(OrderStatus.FAIL)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        try {
            Map<String, Integer> preparedOrder = groupAndNormalize(productOrderList);

            List<Product> products = productService.fetchProducts(preparedOrder);

            for (Product product : products) {
                String normalizedName = product.getNormalizedName();
                int quantity = preparedOrder.get(normalizedName);

                OrderItem orderItem = OrderItem.builder()
                        .product(product)
                        .quantity(quantity)
                        .order(order)
                        .build();

                orderItems.add(orderItem);
                locations.add(product.getLocation());
            }

            productService.updateStock(products, preparedOrder);
            Route route = routeService.createRoute(locations);

            //System.out.println(route.getVisitedLocations());


            order.setOrderItems(orderItems);

            order.setRoute(route);
            route.setOrder(order);

            //System.out.println(order.getRoute().getVisitedLocations());

            order.setOrderStatus(OrderStatus.SUCCESS);

            orderService.save(order);
        } catch (RuntimeException e) {
            orderService.saveFailed(order);
            throw e;
        }
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
