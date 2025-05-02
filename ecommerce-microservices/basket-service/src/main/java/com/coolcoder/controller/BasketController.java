package com.coolcoder.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coolcoder.dto.BasketItemDTO;
import com.coolcoder.service.BasketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {
	private final BasketService service;

	@PostMapping
	public ResponseEntity<BasketItemDTO> add(@Valid @RequestBody BasketItemDTO dto) {
		return ResponseEntity.ok(service.addItem(dto));
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<BasketItemDTO>> getByCustomer(@PathVariable Long customerId) {
		return ResponseEntity.ok(service.getItemsByCustomer(customerId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BasketItemDTO> update(@PathVariable Long id, @RequestParam Integer quantity) {
		return ResponseEntity.ok(service.updateItem(id, quantity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		service.removeItem(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<Void> clear(@PathVariable Long customerId) {
		service.clearCart(customerId);
		return ResponseEntity.noContent().build();
	}
}