package com.exercises.activeedgetask.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.request.UpdateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.exception.ResourceNotFoundException;
import com.exercises.activeedgetask.exercise3.model.Stock;
import com.exercises.activeedgetask.exercise3.repository.StockRepository;
import com.exercises.activeedgetask.exercise3.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
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
        stock = new Stock(1L, "Apple Inc.", BigDecimal.valueOf(150.50), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        createStockRequest = new CreateStockRequest("Apple Inc.", BigDecimal.valueOf(150.50));
        updateStockRequest = new UpdateStockRequest("Apple Inc.", BigDecimal.valueOf(155.00));
    }

    @Test
    void testAddStock() {
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> {
            Stock s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        APIResponse<String> response = stockService.addStock(createStockRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getMessage()).isEqualTo("Stock created successfully with ID 1");
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void testUpdateStock() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);

        APIResponse<String> response = stockService.updateStock(1L, updateStockRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getMessage()).contains("Stock updated successfully with ID 1");
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void testUpdateStock_NotFound() {
        when(stockRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stockService.updateStock(2L, updateStockRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Stock not found with ID: 2");
    }

    @Test
    void testGetSingleStock() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        APIResponse<StockResponse> response = stockService.getSingleStock(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData().getName()).isEqualTo("Apple Inc.");
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSingleStock_NotFound() {
        when(stockRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stockService.getSingleStock(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Stock not found with ID: 2");
    }

    @Test
    void testGetStockLists() {
        when(stockRepository.findAll()).thenReturn(List.of(stock));

        APIResponse<List<StockResponse>> response = stockService.getStockLists();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getData()).hasSize(1);
        verify(stockRepository, times(1)).findAll();
    }
}