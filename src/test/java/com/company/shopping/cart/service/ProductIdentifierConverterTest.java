package com.company.shopping.cart.service;

import com.company.shopping.cart.dao.model.ProductIdentifier;
import com.company.shopping.cart.model.Course;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductIdentifierConverterTest {

    private ProductIdentifierConverter service = new ProductIdentifierConverter();

    @Test
    public void testFrom() {
        assertEquals(List.of(Course.Math, Course.Physics, Course.Chemistry),
                service.fromProductIdentifiers(List.of(ProductIdentifier.Math, ProductIdentifier.Physics, ProductIdentifier.Chemistry)));
    }

    @Test
    public void testTo() {
        assertEquals(List.of(ProductIdentifier.Math, ProductIdentifier.Physics, ProductIdentifier.Chemistry),
                service.toProductIdentifiers(List.of(Course.Math, Course.Physics, Course.Chemistry)));
    }

}
