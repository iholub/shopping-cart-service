package com.company.shopping.cart.service;

import com.company.shopping.cart.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class TotalCostServiceTest {

    private TotalCostService totalCostService;

    @Mock
    private CoursePriceService coursePriceService;

    @BeforeEach
    void setUp() {
        totalCostService = new TotalCostService(coursePriceService);
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testGetTotalCost(Map<Course, Integer> prices, List<Course> input, int expected) {
        prices.forEach((key, value) -> lenient().when(coursePriceService.getPrice(key)).thenReturn(value));

        assertEquals(expected, totalCostService.getTotalCost(input));
    }

    private static Stream<Arguments> testData() {
        // Math price > Chemistry price
        Map<Course, Integer> prices1 = Map.of(Course.Math, 60, Course.Physics, 25, Course.Chemistry, 20);
        // Math price < Chemistry price
        Map<Course, Integer> prices2 = Map.of(Course.Math, 20, Course.Physics, 25, Course.Chemistry, 60);
        return Stream.of(
                Arguments.of(prices1, List.of(), 0),
                Arguments.of(prices1, List.of(Course.Math), 60),
                Arguments.of(prices1, List.of(Course.Math, Course.Math), 60),
                Arguments.of(prices1, List.of(Course.Math, Course.Math, Course.Math), 120),
                Arguments.of(prices1, List.of(Course.Physics, Course.Physics), 50),
                Arguments.of(prices1, List.of(Course.Math, Course.Physics), 85),
                Arguments.of(prices1, List.of(Course.Math, Course.Math, Course.Physics), 85),
                Arguments.of(prices1, List.of(Course.Chemistry), 20),
                Arguments.of(prices1, List.of(Course.Chemistry, Course.Chemistry), 20),
                Arguments.of(prices1, List.of(Course.Chemistry, Course.Chemistry, Course.Chemistry), 40),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Math), 120),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Chemistry), 80),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Chemistry, Course.Chemistry), 80),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Math, Course.Chemistry), 120),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Math, Course.Chemistry, Course.Math), 180),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Math, Course.Chemistry, Course.Math), 180),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Math, Course.Physics), 145),
                Arguments.of(prices1, List.of(Course.Math, Course.Chemistry, Course.Math, Course.Physics, Course.Physics), 170),

                Arguments.of(prices2, List.of(Course.Chemistry, Course.Math, Course.Chemistry), 120),
                Arguments.of(prices2, List.of(Course.Chemistry, Course.Math, Course.Math), 80)
        );
    }
}
