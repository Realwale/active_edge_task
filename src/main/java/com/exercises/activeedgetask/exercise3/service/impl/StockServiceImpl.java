package com.exercises.activeedgetask.exercise3.service.impl;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.model.Stock;
import com.exercises.activeedgetask.exercise3.repository.StockRepository;
import com.exercises.activeedgetask.exercise3.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class StockServiceImpl implements StockService {


    private final StockRepository stockRepository;

    @Override
    public APIResponse<StockResponse> addStock(CreateStockRequest stockRequest) {

        log.info("Taking request data >>>>>>>>>>>>>>>>>>>>>>");

        Stock stock = Stock.builder()
                .name(stockRequest.getName())
                .currentPrice(stockRequest.getCurrentPrice())
                .build();
        log.info("saving data >>>>>>>>>>>>>>>>>>>>>>");
        stockRepository.save(stock);

        log.info("saved data >>>>>>>>>>>>>>>>>>>>>>");

        StockResponse response = StockResponse.builder()
                .id(stock.getId())
                .name(stock.getName())
                .currentPrice(stock.getCurrentPrice())
                .createDate(stock.getCreateDate())
                .lastUpdate(stock.getLastUpdate())
                .build();

        log.info("Returning response data >>>>>>>>>>>>>>>>>>>>>>");

        return new  APIResponse <>(false, HttpStatus.CREATED.value(), "Stock created successfully", response);

    }
}
