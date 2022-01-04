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
    void testGetTotalCost(List<Course> input, int expected) {
        lenient().when(coursePriceService.getPrice(Course.Math)).thenReturn(60);
        lenient().when(coursePriceService.getPrice(Course.Physics)).thenReturn(25);

        assertEquals(expected, totalCostService.getTotalCost(input));
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(List.of(), 0),
                Arguments.of(List.of(Course.Math), 60),
                Arguments.of(List.of(Course.Math, Course.Math), 120),
                Arguments.of(List.of(Course.Physics, Course.Physics), 50),
                Arguments.of(List.of(Course.Math, Course.Physics), 85)
        );
    }
}
