package com.sales.order_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userEmail;
	private LocalDateTime createAt;
	private Double total;
	
	
	@ElementCollection
	private List<Long> productIds;
	
	@ElementCollection
	private List<OrderedProduct> orderedProducts;

	
	public Order() {
		this.createAt = LocalDateTime.now();
	}

	
	public Order(Long id, String userEmail, LocalDateTime createAt, Double total, List<Long> productIds,
			List<OrderedProduct> orderedProducts) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.createAt = createAt;
		this.total = total;
		this.productIds = productIds;
		this.orderedProducts = orderedProducts;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}


	public List<OrderedProduct> getOrderedProducts() {
		return orderedProducts;
	}


	public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}
	
}
