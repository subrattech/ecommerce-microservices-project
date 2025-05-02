package com.coolcoder.service;

import java.util.List;

import com.coolcoder.dto.BasketItemDTO;

public interface BasketService {
	BasketItemDTO addItem(BasketItemDTO dto);

	List<BasketItemDTO> getItemsByCustomer(Long customerId);

	BasketItemDTO updateItem(Long id, Integer quantity);

	void removeItem(Long id);

	void clearCart(Long customerId);
}
