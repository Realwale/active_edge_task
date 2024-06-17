package com.exercises.activeedgetask.service;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import com.exercises.activeedgetask.exercise3.controller.StockController;
import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.request.UpdateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.model.Stock;
import com.exercises.activeedgetask.exercise3.repository.StockRepository;
import com.exercises.activeedgetask.exercise3.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock stock;
    private CreateStockRequest createStockRequest;
    private UpdateStockRequest updateStockRequest;

    @BeforeEach
    void setup() {
        // Initialize test data
        stock = new Stock(1L, "Apple Inc.", BigDecimal.valueOf(150.50), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        createStockRequest = new CreateStockRequest("Apple Inc.", BigDecimal.valueOf(150.50));
        updateStockRequest = new UpdateStockRequest("Apple Inc.", BigDecimal.valueOf(155.00));

        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.findAll()).thenReturn(List.of(stock));
    }

    @Test
    void testAddStock() {
        APIResponse<?> response = stockService.addStock(createStockRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getData()).isNotNull();
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void testUpdateStock() {
        APIResponse<?> response = stockService.updateStock(1L, updateStockRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getMessage()).contains("successfully");
    }

    @Test
    void testGetSingleStock() {
        APIResponse<StockResponse> response = stockService.getSingleStock(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData().getName()).isEqualTo("Apple Inc.");
    }


    @Test
    void testGetStockLists() {
        APIResponse<List<StockResponse>> response = stockService.getStockLists();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData()).hasSize(1);
    }
}
