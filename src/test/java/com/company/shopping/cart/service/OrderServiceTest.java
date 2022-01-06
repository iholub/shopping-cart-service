package com.company.shopping.cart.service;

import com.company.shopping.cart.dao.model.OrderEntity;
import com.company.shopping.cart.dao.model.ProductIdentifier;
import com.company.shopping.cart.dao.repository.OrderRepository;
import com.company.shopping.cart.model.Course;
import com.company.shopping.cart.model.CreateOrderRequest;
import com.company.shopping.cart.model.CreateOrderResponse;
import com.company.shopping.cart.model.FindOrderByIdResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TotalCostService totalCostService;

    @Mock
    private ProductIdentifierConverter productIdentifierConverter;

    @Test
    public void testCreate() {
        List<Course> courses = List.of(Course.Math, Course.Physics);
        List<ProductIdentifier> productIdentifiers = List.of(ProductIdentifier.Math, ProductIdentifier.Physics);
        int totalCost = 10;
        long orderId = 1L;

        when(productIdentifierConverter.toProductIdentifiers(courses)).thenReturn(productIdentifiers);

        CreateOrderRequest request = CreateOrderRequest.builder()
                .products(courses)
                .build();
        OrderEntity entity = OrderEntity.builder()
                .totalCost(totalCost)
                .products(productIdentifiers)
                .build();

        when(totalCostService.getTotalCost(courses)).thenReturn(totalCost);

        when(orderRepository.save(entity)).thenReturn(OrderEntity.builder()
                .id(orderId)
                .build());

        CreateOrderResponse actual = orderService.create(request);
        assertThat(actual).isEqualTo(CreateOrderResponse.builder()
                .orderId(orderId)
                .build());
    }

    @Test
    public void testFindByIdOrderFound() {
        List<Course> courses = List.of(Course.Math, Course.Physics);
        List<ProductIdentifier> productIdentifiers = List.of(ProductIdentifier.Math, ProductIdentifier.Physics);
        int totalCost = 10;
        long orderId = 1L;

        when(productIdentifierConverter.fromProductIdentifiers(productIdentifiers)).thenReturn(courses);

        OrderEntity entity = OrderEntity.builder()
                .totalCost(totalCost)
                .products(productIdentifiers)
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

        Optional<FindOrderByIdResponse> actual = orderService.findById(orderId);
        assertThat(actual).contains(FindOrderByIdResponse.builder()
                .totalCost(totalCost)
                .products(courses)
                .build());
    }

    @Test
    public void testFindByIdOrderNotFound() {
        long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<FindOrderByIdResponse> actual = orderService.findById(orderId);
        assertThat(actual).isNotPresent();
    }

}
