package com.exercises.activeedgetask.exercise3.controller;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.request.UpdateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;
import com.exercises.activeedgetask.exercise3.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;


    @PostMapping
    public ResponseEntity<APIResponse<?>> addStock(@RequestBody CreateStockRequest stockRequest){
        stockRequest.validateCreateSockRequest();
        APIResponse<?> response = stockService.addStock(stockRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<?>> updateStock(@PathVariable Long id, @RequestBody UpdateStockRequest stockRequest){
        stockRequest.validateUpdateStockRequest();
        APIResponse<?> response = stockService.updateStock(id, stockRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StockResponse>> getSingleStock(@PathVariable Long id){
        APIResponse<StockResponse> response = stockService.getSingleStock(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<StockResponse>>> getStockLists(){
        return new ResponseEntity<>(stockService.getStockLists(), HttpStatus.OK);
    }
}
