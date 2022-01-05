package com.company.shopping.cart.service;

import com.company.shopping.cart.dao.model.OrderEntity;
import com.company.shopping.cart.dao.repository.OrderRepository;
import com.company.shopping.cart.model.CreateOrderRequest;
import com.company.shopping.cart.model.CreateOrderResponse;
import com.company.shopping.cart.model.FindOrderByIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Operations to works with Order.
 */
@Component
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TotalCostService totalCostService;

    @Autowired
    private ProductIdentifierConverter productIdentifierConverter;

    /**
     * Calculates total cost for specified order and saves it to database.
     *
     * @param request order request
     * @return order response
     */
    @Transactional
    public CreateOrderResponse create(CreateOrderRequest request) {

        int totalCost = totalCostService.getTotalCost(request.getProducts());

        OrderEntity saved = orderRepository.save(OrderEntity.builder()
                .totalCost(totalCost)
                .products(productIdentifierConverter.toProductIdentifiers(request.getProducts()))
                .build());

        return CreateOrderResponse.builder()
                .orderId(saved.getId())
                .build();
    }

    /**
     * Finds order by id.
     *
     * @param id id of the order
     * @return find order by id response
     */
    @Transactional(readOnly = true)
    public Optional<FindOrderByIdResponse> findById(Long id) {

        Optional<OrderEntity> entity = orderRepository.findById(id);

        return entity.map(item -> FindOrderByIdResponse.builder()
                .totalCost(item.getTotalCost())
                .products(productIdentifierConverter.fromProductIdentifiers(item.getProducts()))
                .build());
    }

}
