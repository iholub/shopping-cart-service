package com.company.shopping.cart.service;

import com.company.shopping.cart.model.Course;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Calculates total cost for specified list of courses (shopping cart).
 */
@RequiredArgsConstructor
public class TotalCostService {

    private final CoursePriceService coursePriceService;

    /**
     * Calculates total cost for specified list of courses.
     *
     * @param courses the list of courses
     * @return total cost
     */
    public int getTotalCost(List<Course> courses) {
        Map<Course, Long> counted = courses.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return counted.entrySet().stream()
                .map(item -> (int) (coursePriceService.getPrice(item.getKey()) * item.getValue()))
                .mapToInt(Integer::intValue).sum();
    }

}
