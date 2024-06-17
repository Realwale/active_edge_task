package com.exercises.activeedgetask.exercise3.request;



import com.exercises.activeedgetask.exercise3.exception.ValidationException;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSockRequest {

    private String name;

    private BigDecimal currentPrice;



    public void validateCreateSockRequest() {
        List<String> errors = new ArrayList<>();


        validateStockName(name, errors);
        validateStockCurrentPrice(currentPrice, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join(", ", errors));
        }

    }


    private static void validateStockName(String name, List<String> errors) {
        if (name == null || name.trim().isEmpty()) {
            errors.add("First name is missing or empty");
        }
    }


    private static void validateStockCurrentPrice(BigDecimal currentPrice, List<String> errors) {
        if (currentPrice == null || currentPrice.compareTo(BigDecimal.valueOf(0.01)) < 0) {
            errors.add("Current price must be greater than 0.00.");
        }
    }
}
