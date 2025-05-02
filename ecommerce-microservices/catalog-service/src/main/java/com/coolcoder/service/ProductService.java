package com.coolcoder.service;

import java.util.List;

import com.coolcoder.dto.ProductDTO;

public interface ProductService {
	ProductDTO create(ProductDTO dto);

	ProductDTO getById(Long id);

	List<ProductDTO> getAll();

	ProductDTO update(Long id, ProductDTO dto);

	void delete(Long id);
}