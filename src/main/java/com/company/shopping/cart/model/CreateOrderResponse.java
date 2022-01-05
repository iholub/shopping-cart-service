package com.company.shopping.cart.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Response for create order operation.
 */
@Value
@Builder
@Jacksonized
public class CreateOrderResponse {
    long orderId;
}
