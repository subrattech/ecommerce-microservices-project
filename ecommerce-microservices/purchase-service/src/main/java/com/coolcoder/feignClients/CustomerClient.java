package com.coolcoder.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coolcoder.dto.CustomerDTO;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {
	@GetMapping("/api/customers/{id}")
	CustomerDTO getById(@PathVariable Long id);
}
