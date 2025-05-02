package com.coolcoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coolcoder.model.BasketItem;

public interface BasketRepository extends JpaRepository<BasketItem, Long> {
	List<BasketItem> findByCustomerId(Long customerId);
}
