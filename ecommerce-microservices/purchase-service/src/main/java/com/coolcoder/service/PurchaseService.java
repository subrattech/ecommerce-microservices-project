package com.coolcoder.service;

import java.util.List;

import com.coolcoder.dto.PurchaseDTO;

public interface PurchaseService {
	PurchaseDTO place(PurchaseDTO dto);

	PurchaseDTO getById(Long id);

	List<PurchaseDTO> getByCustomer(Long customerId);

	void cancel(Long id);
}