package com.company.shopping.cart.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import java.util.List;

/**
 * Represents shopping order in database.
 */
@Entity(name = "shopping_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    int totalCost;

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "product_order")
    @Enumerated(EnumType.STRING)
    @Column(name = "product_id", nullable = false, length = 50)
    List<ProductIdentifier> products;

}
