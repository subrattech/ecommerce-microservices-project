package com.coolcoder.serviceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.coolcoder.dto.BasketItemDTO;
import com.coolcoder.exception.ResourceNotFoundException;
import com.coolcoder.model.BasketItem;
import com.coolcoder.repository.BasketRepository;
import com.coolcoder.service.BasketService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
	private final BasketRepository repo;

	@Override
	@Transactional
	public BasketItemDTO addItem(BasketItemDTO dto) {
		// Check if existing item for same customer/product
		List<BasketItem> existing = repo.findByCustomerId(dto.getCustomerId()).stream()
				.filter(i -> i.getProductId().equals(dto.getProductId())).collect(Collectors.toList());

		BasketItem item;
		if (!existing.isEmpty()) {
			item = existing.get(0);
			item.setQuantity(item.getQuantity() + dto.getQuantity());
		} else {
			item = new BasketItem();
			BeanUtils.copyProperties(dto, item);
		}
		BasketItem saved = repo.save(item);
		BasketItemDTO out = new BasketItemDTO();
		BeanUtils.copyProperties(saved, out);
		return out;
	}

	@Override
	public List<BasketItemDTO> getItemsByCustomer(Long customerId) {
		return repo.findByCustomerId(customerId).stream().map(i -> {
			BasketItemDTO dto = new BasketItemDTO();
			BeanUtils.copyProperties(i, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public BasketItemDTO updateItem(Long id, Integer quantity) {
		BasketItem item = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + id));
		item.setQuantity(quantity);
		BasketItem updated = repo.save(item);
		BasketItemDTO out = new BasketItemDTO();
		BeanUtils.copyProperties(updated, out);
		return out;
	}

	@Override
	@Transactional
	public void removeItem(Long id) {
		if (!repo.existsById(id))
			throw new ResourceNotFoundException("Cart item not found: " + id);
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public void clearCart(Long customerId) {
		repo.findByCustomerId(customerId).forEach(item -> repo.delete(item));
	}
}
