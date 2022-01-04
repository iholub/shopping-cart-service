package com.company.shopping.cart.service;

import com.company.shopping.cart.model.Course;

import java.util.Map;

/**
 * Returns prices for courses.
 */
public class CoursePriceService {

    private Map<Course, Integer> prices = Map.of(Course.Math, 60, Course.Physics, 25);

    /**
     * Returns the price of specified course.
     *
     * @param course the course
     * @return the price of specified course
     */
    public int getPrice(Course course) {
        return prices.get(course);
    }

}
