package com.sales.stockhistory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.stockhistory.entity.StockHistory;
import com.sales.stockhistory.repository.StockHistoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/stock-history")

public class StockHistoryController {
	
	private final StockHistoryRepository repository;
	
	public StockHistoryController(StockHistoryRepository repository) {
		super();
		this.repository = repository;
	}



	@Operation(summary = "Consultar historial de movimientos por ID de producto")
	@ApiResponse(responseCode="200", description="Historial obtenido con exito")
	@GetMapping("/{productId}")
	public List<StockHistory> getByProductId(@PathVariable String productId){
		return repository.findByProductId(productId);
	}
}
