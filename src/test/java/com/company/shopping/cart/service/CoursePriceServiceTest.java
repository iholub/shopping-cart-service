package com.company.shopping.cart.service;

import com.company.shopping.cart.model.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoursePriceServiceTest {

    private CoursePriceService service = new CoursePriceService();

    @Test
    public void testMath() {
        assertEquals(60, service.getPrice(Course.Math));
    }

    @Test
    public void testPhysics() {
        assertEquals(25, service.getPrice(Course.Physics));
    }

    @ParameterizedTest
    @EnumSource(Course.class)
    void testAllEnumValues(Course course) {
        assertTrue(service.getPrice(course) > 0);
    }

}
