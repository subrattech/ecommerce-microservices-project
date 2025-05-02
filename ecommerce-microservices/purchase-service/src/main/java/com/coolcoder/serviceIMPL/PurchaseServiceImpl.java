package com.coolcoder.serviceIMPL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.coolcoder.dto.PurchaseDTO;
import com.coolcoder.exception.ResourceNotFoundException;
import com.coolcoder.feignClients.CustomerClient;
import com.coolcoder.feignClients.ProductClient;
import com.coolcoder.model.Purchase;
import com.coolcoder.repository.PurchaseRepository;
import com.coolcoder.service.PurchaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
	private final PurchaseRepository repo;
	private final CustomerClient customerClient;
	private final ProductClient productClient;

	private PurchaseDTO toDTO(Purchase p) {
		PurchaseDTO dto = new PurchaseDTO();
		BeanUtils.copyProperties(p, dto);
		return dto;
	}

	private Purchase toEntity(PurchaseDTO dto) {
		Purchase p = new Purchase();
		BeanUtils.copyProperties(dto, p);
		p.setPurchaseDate(LocalDateTime.now());
		return p;
	}

	@Override
	@Transactional
	public PurchaseDTO place(PurchaseDTO dto) {
		// validate customer & product exist
		if (customerClient.getById(dto.getCustomerId()) == null)
			throw new ResourceNotFoundException("Customer not found: " + dto.getCustomerId());
		if (productClient.getById(dto.getProductId()) == null)
			throw new ResourceNotFoundException("Product not found: " + dto.getProductId());
		Purchase saved = repo.save(toEntity(dto));
		return toDTO(saved);
	}

	@Override
	public PurchaseDTO getById(Long id) {
		Purchase p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + id));
		return toDTO(p);
	}

	@Override
	public List<PurchaseDTO> getByCustomer(Long customerId) {
		return repo.findByCustomerId(customerId).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void cancel(Long id) {
		if (!repo.existsById(id))
			throw new ResourceNotFoundException("Purchase not found: " + id);
		repo.deleteById(id);
	}
}
