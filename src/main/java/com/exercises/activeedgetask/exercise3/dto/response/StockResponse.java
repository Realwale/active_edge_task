package com.exercises.activeedgetask.exercise3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private Timestamp createDate;
    private Timestamp lastUpdate;
}