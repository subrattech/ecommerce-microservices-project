package com.coolcoder.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coolcoder.dto.ProductDTO;

@FeignClient(name = "CATALOG-SERVICE", path = "/api/catalog")
public interface ProductClient {
	@GetMapping("/{id}")
	ProductDTO getById(@PathVariable("id") Long id);
}