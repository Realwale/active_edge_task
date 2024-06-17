package com.exercises.activeedgetask.controller;


import com.exercises.activeedgetask.exercise3.controller.StockController;
import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.request.UpdateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateStockRequest createStockRequest;
    private UpdateStockRequest updateStockRequest;
    private StockResponse stockResponse;

    @BeforeEach
    void setup() {
        createStockRequest = new CreateStockRequest("Apple Inc.", BigDecimal.valueOf(150.50));
        updateStockRequest = new UpdateStockRequest("Apple Inc.", BigDecimal.valueOf(155.00));
        stockResponse = StockResponse.builder()
                .id(1L)
                .name("Apple Inc.")
                .currentPrice(BigDecimal.valueOf(150.50))
                .createDate(Timestamp.from(Instant.now()))
                .lastUpdate(Timestamp.from(Instant.now()))
                .build();
    }

    @Test
    void testAddStock() throws Exception {
        APIResponse<String> apiResponse = new APIResponse<>(false, 201, "Stock created successfully with ID 1", null);
        given(stockService.addStock(any(CreateStockRequest.class))).willReturn(apiResponse);

        mockMvc.perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createStockRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Stock created successfully with ID 1"));
    }

    @Test
    void testUpdateStock() throws Exception {
        APIResponse<String> apiResponse = new APIResponse<>(false, 200, "Stock updated successfully with ID 1", null);
        given(stockService.updateStock(anyLong(), any(UpdateStockRequest.class))).willReturn(apiResponse);

        mockMvc.perform(put("/api/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Stock updated successfully with ID 1"));
    }

    @Test
    void testGetSingleStock() throws Exception {
        APIResponse<StockResponse> apiResponse = new APIResponse<>(false, 200, "Request processed successfully for stock with ID: 1", stockResponse);
        given(stockService.getSingleStock(anyLong())).willReturn(apiResponse);

        mockMvc.perform(get("/api/stocks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Request processed successfully for stock with ID: 1"))
                .andExpect(jsonPath("$.data.name").value("Apple Inc."));
    }

    @Test
    void testGetStockLists() throws Exception {
        List<StockResponse> stockResponses = Collections.singletonList(stockResponse);
        APIResponse<List<StockResponse>> apiResponse = new APIResponse<>(false, 200, "Stock list retrieved successfully", stockResponses);
        given(stockService.getStockLists()).willReturn(apiResponse);

        mockMvc.perform(get("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Stock list retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Apple Inc."));
    }
}
