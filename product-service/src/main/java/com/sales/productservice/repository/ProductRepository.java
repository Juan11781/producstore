package com.sales.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sales.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product>findByStockGreaterThan(int stock);
}
