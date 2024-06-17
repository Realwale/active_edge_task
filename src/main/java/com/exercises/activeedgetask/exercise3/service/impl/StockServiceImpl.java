package com.exercises.activeedgetask.exercise3.service.impl;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.request.UpdateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.exception.ResourceNotFoundException;
import com.exercises.activeedgetask.exercise3.model.Stock;
import com.exercises.activeedgetask.exercise3.repository.StockRepository;
import com.exercises.activeedgetask.exercise3.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class StockServiceImpl implements StockService {


    private final StockRepository stockRepository;

    @Override
    public APIResponse<?> addStock(CreateStockRequest stockRequest) {

        log.info("Adding new stock with name: {}", stockRequest.getName());
        Stock stock = Stock.builder()
                .name(stockRequest.getName())
                .currentPrice(stockRequest.getCurrentPrice())
                .build();
        stockRepository.save(stock);

        log.info("Stock created successfully with ID: {}", stock.getId());
        return new  APIResponse <>(false, HttpStatus.CREATED.value(), "Stock created successfully with ID "+ stock.getId(), null);

    }

    @Override
    public APIResponse<?> updateStock(Long stockId, UpdateStockRequest stockRequest) {
        log.info("Updating stock ID: {}", stockId);
        Stock checkStock = stockRepository.findById(stockId).orElseThrow(()->
                new ResourceNotFoundException("Stock not found with ID: " + stockId));

        checkStock.setName(stockRequest.getName());
        checkStock.setCurrentPrice(stockRequest.getCurrentPrice());
        stockRepository.save(checkStock);
        log.info("Stock updated successfully with ID: {}", stockId);
        return new  APIResponse <>(false, HttpStatus.CREATED.value(), "Stock updated successfully with ID "+ stockId, null);
    }

    @Override
    public APIResponse<StockResponse> getSingleStock(Long stockId) {
        log.info("Retrieving stock with ID: {}", stockId);
        Stock findStock = stockRepository.findById(stockId).orElseThrow(()->
                new ResourceNotFoundException("Stock not found with ID: " + stockId));

        StockResponse  response = StockResponse.builder()
                .id(findStock.getId())
                .createDate(findStock.getCreateDate())
                .currentPrice(findStock.getCurrentPrice())
                .lastUpdate(findStock.getLastUpdate())
                .name(findStock.getName())
                .build();
        log.info("Stock retrieved successfully for ID: {}", stockId);
        return APIResponse.<StockResponse>builder()
                .hasError(false)
                .statusCode(HttpStatus.OK.value())
                .message("Request processed successfully for stock with ID: "+ stockId)
                .data(response)
                .build();
    }

    @Override
    public APIResponse<List<StockResponse>> getStockLists() {
        log.info("Retrieving all stocks");
        List<StockResponse> stockList = stockRepository.findAll()
                .stream().map(stock -> StockResponse.builder()
                        .id(stock.getId())
                        .name(stock.getName())
                        .currentPrice(stock.getCurrentPrice())
                        .createDate(stock.getCreateDate())
                        .lastUpdate(stock.getLastUpdate())
                        .build())
                .toList();
        log.info("Stock list retrieved successfully");
        return APIResponse.<List<StockResponse>>builder()
                .hasError(false)
                .statusCode(HttpStatus.OK.value())
                .message("Stock list retrieved successfully")
                .data(stockList)
                .build();
    }
}
