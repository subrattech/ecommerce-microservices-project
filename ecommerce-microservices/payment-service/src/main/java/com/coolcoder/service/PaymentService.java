package com.coolcoder.service;

import java.util.List;

import com.coolcoder.dto.PaymentDTO;

public interface PaymentService {
	PaymentDTO initiate(Long purchaseId, Double amount);

	PaymentDTO getById(Long id);

	List<PaymentDTO> getAll();

	String handleWebhook(String payload, String sigHeader);
}
