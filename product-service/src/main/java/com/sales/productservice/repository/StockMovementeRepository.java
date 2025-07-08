package com.sales.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sales.productservice.entity.StockMovement;

public interface StockMovementeRepository extends JpaRepository<StockMovement, Long> {
	List<StockMovement> findByProductId(Long productId);
}
