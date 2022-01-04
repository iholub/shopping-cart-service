package com.company.shopping.cart.service;

import com.company.shopping.cart.model.Course;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
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

        Map<Course, Long> countedWithPromotion = applyPromotion(counted);

        return countedWithPromotion.entrySet().stream()
                .map(item -> (int) (coursePriceService.getPrice(item.getKey()) * item.getValue()))
                .mapToInt(Integer::intValue).sum();
    }

    private Map<Course, Long> applyPromotion(Map<Course, Long> counted) {
        if (!counted.containsKey(Course.Math)) {
            return counted;
        }
        long mathCount = counted.get(Course.Math);
        Map<Course, Long> result = new HashMap<>(counted);
        result.put(Course.Math, (mathCount + 1) / 2);
        return result;
    }

}
