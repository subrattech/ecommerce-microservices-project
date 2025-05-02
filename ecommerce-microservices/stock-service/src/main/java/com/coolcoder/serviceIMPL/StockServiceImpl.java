package com.coolcoder.serviceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.coolcoder.dto.StockDTO;
import com.coolcoder.exception.ResourceNotFoundException;
import com.coolcoder.model.Stock;
import com.coolcoder.repository.StockRepository;
import com.coolcoder.service.StockService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
	private final StockRepository repo;

	@Override
	@Transactional
	public StockDTO createOrUpdate(StockDTO dto) {
		Stock stock = repo.findByProductId(dto.getProductId()).orElse(new Stock());
		BeanUtils.copyProperties(dto, stock, "id");
		Stock saved = repo.save(stock);
		StockDTO out = new StockDTO();
		BeanUtils.copyProperties(saved, out);
		return out;
	}

	@Override
	public StockDTO getByProductId(Long productId) {
		Stock stock = repo.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found for product: " + productId));
		StockDTO dto = new StockDTO();
		BeanUtils.copyProperties(stock, dto);
		return dto;
	}

	@Override
	public List<StockDTO> getAll() {
		return repo.findAll().stream().map(s -> {
			StockDTO dto = new StockDTO();
			BeanUtils.copyProperties(s, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException("Stock not found: " + id);
		}
		repo.deleteById(id);
	}
}
