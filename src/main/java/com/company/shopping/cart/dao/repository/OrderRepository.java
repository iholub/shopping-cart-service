package com.company.shopping.cart.dao.repository;

import com.company.shopping.cart.dao.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for database operations with shopping order.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
