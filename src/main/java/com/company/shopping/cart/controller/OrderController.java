package com.company.shopping.cart.controller;

import com.company.shopping.cart.model.CreateOrderRequest;
import com.company.shopping.cart.model.CreateOrderResponse;
import com.company.shopping.cart.model.FindOrderByIdResponse;
import com.company.shopping.cart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Rest api controller to work with orders.
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Creates order.
     *
     * @param request create order request
     * @return create order response
     */
    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse create(@RequestBody CreateOrderRequest request) {
        return orderService.create(request);
    }

    /**
     * Finds order by id.
     *
     * @param id id of the order
     * @return find by id order response
     */
    @GetMapping("/api/orders/{id}")
    public ResponseEntity<FindOrderByIdResponse> getById(@PathVariable("id") long id) {
        Optional<FindOrderByIdResponse> data = orderService.findById(id);
        return data
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
