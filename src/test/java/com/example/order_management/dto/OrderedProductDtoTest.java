package com.example.order_management.dto;

import com.example.order_management.ModelUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class OrderedProductDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(longs = {Integer.MIN_VALUE, -100, -1, 0})
    void testOrderedProductDtoValidationWithImpossibleQuantity(Long quantity) {
        OrderedProductDto dto = ModelUtils.getOrderedProductDto();
        dto.setQuantity(quantity);
        Set<ConstraintViolation<OrderedProductDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("must be greater than or equal to 1", violations.stream().findFirst().get().getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {101, 1000, Integer.MAX_VALUE})
    void testOrderedProductDtoValidationWithTooMuchQuantity(Long quantity) {
        OrderedProductDto dto = ModelUtils.getOrderedProductDto();
        dto.setQuantity(quantity);
        Set<ConstraintViolation<OrderedProductDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("must be less than or equal to 100", violations.stream().findFirst().get().getMessage());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 50, 100})
    void testOrderedProductDtoValidationWithValidQuantity(Long quantity) {
        OrderedProductDto dto = ModelUtils.getOrderedProductDto();
        dto.setQuantity(quantity);
        Set<ConstraintViolation<OrderedProductDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}