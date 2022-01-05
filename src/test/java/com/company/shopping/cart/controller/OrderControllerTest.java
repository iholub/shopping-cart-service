package com.company.shopping.cart.controller;

import com.company.shopping.cart.model.Course;
import com.company.shopping.cart.model.CreateOrderRequest;
import com.company.shopping.cart.model.CreateOrderResponse;
import com.company.shopping.cart.model.FindOrderByIdResponse;
import com.company.shopping.cart.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @SneakyThrows
    public void testCreate() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .products(List.of(Course.Math, Course.Physics))
                .build();

        long orderId = 1L;

        CreateOrderResponse expected = CreateOrderResponse.builder()
                .orderId(orderId)
                .build();
        when(orderService.create(request)).thenReturn(expected);

        mockMvc.perform(post("/api/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.orderId").value(orderId));
    }

    @Test
    @SneakyThrows
    public void testFindById_found() {
        int totalCost = 10;
        Optional<FindOrderByIdResponse> expected = Optional.of(FindOrderByIdResponse.builder()
                .totalCost(totalCost)
                .products(List.of(Course.Math, Course.Physics))
                .build());

        long orderId = 1L;

        when(orderService.findById(orderId)).thenReturn(expected);

        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.totalCost").value(totalCost))
                .andExpect(jsonPath("$.products.size()").value(2))
                .andExpect(jsonPath("$.products[0]").value(Course.Math.name()))
                .andExpect(jsonPath("$.products[1]").value(Course.Physics.name()));
    }

    @Test
    @SneakyThrows
    public void testFindById_notFound() {
        long orderId = 1L;
        when(orderService.findById(orderId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/{id}", orderId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
