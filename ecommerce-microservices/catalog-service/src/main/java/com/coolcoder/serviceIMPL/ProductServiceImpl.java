package com.coolcoder.serviceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.coolcoder.dto.ProductDTO;
import com.coolcoder.exception.ResourceNotFoundException;
import com.coolcoder.model.Product;
import com.coolcoder.repository.ProductRepository;
import com.coolcoder.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository repo;

	@Override
	@Transactional
	public ProductDTO create(ProductDTO dto) {
		Product p = new Product();
		BeanUtils.copyProperties(dto, p);
		Product saved = repo.save(p);
		dto.setId(saved.getId());
		return dto;
	}

	@Override
	public ProductDTO getById(Long id) {
		Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(p, dto);
		return dto;
	}

	@Override
	public List<ProductDTO> getAll() {
		return repo.findAll().stream().map(p -> {
			ProductDTO dto = new ProductDTO();
			BeanUtils.copyProperties(p, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
		p.setName(dto.getName());
		p.setDescription(dto.getDescription());
		p.setPrice(dto.getPrice());
		p.setCategory(dto.getCategory());
		Product updated = repo.save(p);
		ProductDTO out = new ProductDTO();
		BeanUtils.copyProperties(updated, out);
		return out;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repo.existsById(id))
			throw new ResourceNotFoundException("Product not found: " + id);
		repo.deleteById(id);
	}
}