package com.company.shopping.cart.service;

import com.company.shopping.cart.dao.model.ProductIdentifier;
import com.company.shopping.cart.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts product identifiers between rest api model and database model.
 */
@Component
public class ProductIdentifierConverter {

    /**
     * Converts product identifiers from rest api model to database model.
     *
     * @param source the list courses
     * @return the list of product identifiers
     */
    public List<ProductIdentifier> toProductIdentifiers(List<Course> source) {
        return source.stream().map(this::convert).toList();
    }

    private ProductIdentifier convert(Course source) {
        return ProductIdentifier.valueOf(source.name());
    }

    /**
     * Converts product identifiers from database model to rest api model.
     *
     * @param source the list of product identifiers
     * @return the list of courses
     */
    public List<Course> fromProductIdentifiers(List<ProductIdentifier> source) {
        return source.stream().map(this::convert).toList();
    }

    private Course convert(ProductIdentifier source) {
        return Course.valueOf(source.name());
    }

}
