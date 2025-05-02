package com.coolcoder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coolcoder.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	List<Purchase> findByCustomerId(Long customerId);
}
