package com.coolcoder.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.coolcoder.dto.PurchaseDTO;

@FeignClient(name = "PURCHASE-SERVICE", path = "/api/purchases")
public interface PurchaseClient {
	@GetMapping("/{id}")
	PurchaseDTO getById(@PathVariable("id") Long id);
}
