package com.company.shopping.cart.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Request to create order.
 */
@Value
@Builder
@Jacksonized
public class CreateOrderRequest {
    List<Course> products;
}
