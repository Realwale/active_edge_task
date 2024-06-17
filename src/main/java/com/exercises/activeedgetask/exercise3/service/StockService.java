package com.exercises.activeedgetask.exercise3.service;

import com.exercises.activeedgetask.exercise3.dto.request.CreateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.request.UpdateStockRequest;
import com.exercises.activeedgetask.exercise3.dto.response.APIResponse;
import com.exercises.activeedgetask.exercise3.dto.response.StockResponse;

import java.util.List;


public interface StockService {

    APIResponse<?> addStock(CreateStockRequest stockRequest);

    APIResponse<?> updateStock(Long stockId, UpdateStockRequest stockRequest);

    APIResponse<StockResponse> getSingleStock(Long stockId);

    APIResponse<List<StockResponse>> getStockLists();
}
