package com.company.shopping.cart.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Response for find order by id operation.
 */
@Value
@Builder
@Jacksonized
public class FindOrderByIdResponse {
    int totalCost;
    List<Course> products;
}
