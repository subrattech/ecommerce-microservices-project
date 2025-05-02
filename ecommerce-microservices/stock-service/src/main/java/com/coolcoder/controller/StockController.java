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

import com.coolcoder.dto.StockDTO;
import com.coolcoder.service.StockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
	private final StockService service;

	@PostMapping
	public ResponseEntity<StockDTO> createOrUpdate(@Valid @RequestBody StockDTO dto) {
		return ResponseEntity.ok(service.createOrUpdate(dto));
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<StockDTO> getByProduct(@PathVariable Long productId) {
		return ResponseEntity.ok(service.getByProductId(productId));
	}

	@GetMapping
	public ResponseEntity<List<StockDTO>> all() {
		return ResponseEntity.ok(service.getAll());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}