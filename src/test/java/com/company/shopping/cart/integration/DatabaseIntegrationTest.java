package com.company.shopping.cart.integration;

import com.company.shopping.cart.dao.model.OrderEntity;
import com.company.shopping.cart.dao.model.ProductIdentifier;
import com.company.shopping.cart.dao.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DatabaseIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldCreateAndFindOrder() {
        int totalCost = 100;
        List<ProductIdentifier> productIdentifiers = List.of(ProductIdentifier.Math, ProductIdentifier.Math, ProductIdentifier.Physics);

        OrderEntity order = orderRepository.save(OrderEntity.builder()
                .totalCost(totalCost)
                .products(productIdentifiers)
                .build());

        long orderId = order.getId();

        Optional<OrderEntity> found = orderRepository.findById(orderId);

        assertThat(found).contains(OrderEntity.builder()
                .id(orderId)
                .totalCost(totalCost)
                .products(productIdentifiers)
                .build());
    }

    @Test
    void shouldNotFindOrder() {
        Optional<OrderEntity> found = orderRepository.findById(-1L);

        assertThat(found).isNotPresent();
    }

}
