package com.coolcoder.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDTO {
	private Long id;
	private Long customerId;
	private Long productId;
	private Integer quantity;
	private Double unitPrice;
	private LocalDateTime purchaseDate;
}