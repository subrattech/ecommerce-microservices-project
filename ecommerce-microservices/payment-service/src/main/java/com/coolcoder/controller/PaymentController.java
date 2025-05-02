package com.coolcoder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcoder.dto.PaymentDTO;
import com.coolcoder.service.PaymentService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService service;

	@PostMapping
	public ResponseEntity<PaymentDTO> create(@RequestParam @NotNull Long purchaseId,
			@RequestParam @NotNull Double amount) {
		return ResponseEntity.ok(service.initiate(purchaseId, amount));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentDTO> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@GetMapping
	public ResponseEntity<List<PaymentDTO>> all() {
		return ResponseEntity.ok(service.getAll());
	}

	@PostMapping("/webhook")
	public ResponseEntity<String> webhook(@RequestBody String payload,
			@RequestHeader("X-Razorpay-Signature") String sigHeader) {
		return ResponseEntity.ok(service.handleWebhook(payload, sigHeader));
	}
}
