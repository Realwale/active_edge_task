package com.exercises.activeedgetask.exercise3.controller;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;


    @PostMapping
    public ResponseEntity<APIResponse<StockResponse>> addStock(@RequestBody CreateStockRequest stockRequest){
        log.info("validating data >>>>>>>>>>>>>>>>>>>>>>");

        stockRequest.validateCreateSockRequest();
        APIResponse<StockResponse> response = stockService.addStock(stockRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
