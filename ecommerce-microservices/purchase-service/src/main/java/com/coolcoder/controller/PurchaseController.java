package com.coolcoder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coolcoder.dto.PurchaseDTO;
import com.coolcoder.service.PurchaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {
	private final PurchaseService service;

	@PostMapping
	public ResponseEntity<PurchaseDTO> place(@Valid @RequestBody PurchaseDTO dto) {
		return ResponseEntity.ok(service.place(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PurchaseDTO> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<PurchaseDTO>> byCustomer(@PathVariable Long customerId) {
		return ResponseEntity.ok(service.getByCustomer(customerId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancel(@PathVariable Long id) {
		service.cancel(id);
		return ResponseEntity.noContent().build();
	}
}
