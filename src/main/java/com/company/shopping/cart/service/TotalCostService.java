package com.company.shopping.cart.service;

import com.company.shopping.cart.model.Course;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Calculates total cost for specified list of courses (shopping cart).
 */
@RequiredArgsConstructor
@Component
public class TotalCostService {

    private final CoursePriceService coursePriceService;

    /**
     * Calculates total cost for specified list of courses.
     *
     * @param courses the list of courses
     * @return total cost
     */
    public int getTotalCost(List<Course> courses) {
        Map<Course, Long> coursesCount = courses.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        ApplyMathChemistryPromotionResult mathChemistryPromotionResult = applyMathChemistryPromotion(coursesCount);
        int mathChemistryPromotionCost = mathChemistryPromotionResult.getMathChemistryPromotionCost();
        coursesCount = mathChemistryPromotionResult.getCoursesCount();

        coursesCount = applyDuplicateCoursePromotion(coursesCount, Course.Math);
        coursesCount = applyDuplicateCoursePromotion(coursesCount, Course.Chemistry);

        return mathChemistryPromotionCost + coursesCount.entrySet().stream()
                .map(item -> (int) (coursePriceService.getPrice(item.getKey()) * item.getValue()))
                .mapToInt(Integer::intValue).sum();
    }

    @Builder
    @Value
    private static class ApplyMathChemistryPromotionResult {
        int mathChemistryPromotionCost;
        Map<Course, Long> coursesCount;
    }

    private ApplyMathChemistryPromotionResult applyMathChemistryPromotion(Map<Course, Long> coursesCount) {
        if (!(coursesCount.containsKey(Course.Math) && coursesCount.containsKey(Course.Chemistry))) {
            return ApplyMathChemistryPromotionResult.builder()
                    .mathChemistryPromotionCost(0)
                    .coursesCount(coursesCount)
                    .build();
        }
        MathChemistryPromotionCalculateResult mathChemistryPromotionCalculateResult = calculateMathChemistryPromotionCost(coursesCount);
        coursesCount = new HashMap<>(coursesCount);
        coursesCount.put(Course.Math, mathChemistryPromotionCalculateResult.getRemainingMathCount());
        coursesCount.put(Course.Chemistry, mathChemistryPromotionCalculateResult.getRemainingChemistryCount());
        return ApplyMathChemistryPromotionResult.builder()
                .mathChemistryPromotionCost(mathChemistryPromotionCalculateResult.getMathChemistryPromotionCost())
                .coursesCount(coursesCount)
                .build();
    }

    @Builder
    @Value
    private static class MathChemistryPromotionCalculateResult {
        int mathChemistryPromotionCost;
        long remainingMathCount;
        long remainingChemistryCount;
    }

    private MathChemistryPromotionCalculateResult calculateMathChemistryPromotionCost(Map<Course, Long> counted) {
        int mathPrice = coursePriceService.getPrice(Course.Math);
        int chemistryPrice = coursePriceService.getPrice(Course.Chemistry);
        Course minPriceCourse;
        Course maxPriceCourse;
        int maxPrice;
        if (mathPrice > chemistryPrice) {
            minPriceCourse = Course.Chemistry;
            maxPriceCourse = Course.Math;
            maxPrice = mathPrice;
        } else {
            minPriceCourse = Course.Math;
            maxPriceCourse = Course.Chemistry;
            maxPrice = chemistryPrice;
        }

        long maxPriceCourseCount = counted.get(maxPriceCourse);
        long minPriceCourseCount = counted.get(minPriceCourse);
        int totalCost = (int) (Math.min(maxPriceCourseCount, minPriceCourseCount) * maxPrice);
        if (maxPriceCourseCount >= minPriceCourseCount) {
            maxPriceCourseCount = maxPriceCourseCount - minPriceCourseCount;
            minPriceCourseCount = 0;
        } else {
            minPriceCourseCount = minPriceCourseCount - maxPriceCourseCount;
            maxPriceCourseCount = 0;
        }
        Map<Course, Long> remaining = Map.of(minPriceCourse, minPriceCourseCount, maxPriceCourse, maxPriceCourseCount);
        return MathChemistryPromotionCalculateResult.builder()
                .mathChemistryPromotionCost(totalCost)
                .remainingMathCount(remaining.get(Course.Math))
                .remainingChemistryCount(remaining.get(Course.Chemistry))
                .build();
    }

    private Map<Course, Long> applyDuplicateCoursePromotion(Map<Course, Long> counted, Course course) {
        if (!counted.containsKey(course)) {
            return counted;
        }
        long count = counted.get(course);
        Map<Course, Long> result = new HashMap<>(counted);
        result.put(course, (count + 1) / 2);
        return result;
    }

}
