package com.sales.stockhistory.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sales.stockhistory.entity.StockHistory;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
	List<StockHistory>findByProductId(String productId);
	List<StockHistory>findByProductIdAndTimeStampBetween(String productId,LocalDateTime start, LocalDateTime end);
}
