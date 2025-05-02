package com.coolcoder.service;

import java.util.List;

import com.coolcoder.dto.StockDTO;

public interface StockService {
	StockDTO createOrUpdate(StockDTO dto);

	StockDTO getByProductId(Long productId);

	List<StockDTO> getAll();

	void delete(Long id);
}