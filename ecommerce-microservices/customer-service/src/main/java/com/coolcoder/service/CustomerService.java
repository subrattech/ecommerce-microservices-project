package com.coolcoder.service;

import java.util.List;

import com.coolcoder.dto.CustomerDTO;

public interface CustomerService {
	CustomerDTO create(CustomerDTO dto);

	CustomerDTO getById(Long id);

	List<CustomerDTO> getAll();

	CustomerDTO update(Long id, CustomerDTO dto);

	void delete(Long id);
}
