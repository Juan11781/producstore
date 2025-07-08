package com.sales.stockhistory.repository;

import java.util.List;

import com.sales.stockhistory.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
	List<StockHistory>findByProductId(String productId);
}
