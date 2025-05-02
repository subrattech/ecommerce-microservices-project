package com.coolcoder.serviceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.coolcoder.dto.CustomerDTO;
import com.coolcoder.exception.ResourceNotFoundException;
import com.coolcoder.model.Customer;
import com.coolcoder.repository.CustomerRepository;
import com.coolcoder.service.CustomerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository repo;

	@Override
	@Transactional
	public CustomerDTO create(CustomerDTO dto) {
		if (repo.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("Email already exists: " + dto.getEmail());
		}
		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);
		Customer saved = repo.save(entity);
		dto.setId(saved.getId());
		return dto;
	}

	@Override
	public CustomerDTO getById(Long id) {
		Customer c = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
		CustomerDTO dto = new CustomerDTO();
		BeanUtils.copyProperties(c, dto);
		return dto;
	}

	@Override
	public List<CustomerDTO> getAll() {
		return repo.findAll().stream().map(c -> {
			CustomerDTO dto = new CustomerDTO();
			BeanUtils.copyProperties(c, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public CustomerDTO update(Long id, CustomerDTO dto) {
		Customer c = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
		c.setName(dto.getName());
		c.setEmail(dto.getEmail());
		c.setAddress(dto.getAddress());
		Customer updated = repo.save(c);
		CustomerDTO out = new CustomerDTO();
		BeanUtils.copyProperties(updated, out);
		return out;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException("Customer not found: " + id);
		}
		repo.deleteById(id);
	}
}