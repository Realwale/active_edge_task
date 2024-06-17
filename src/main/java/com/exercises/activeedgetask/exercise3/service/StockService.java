package com.exercises.activeedgetask.exercise3.service;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;


public interface StockService {

    APIResponse<StockResponse> addStock(CreateStockRequest stockRequest);
}
