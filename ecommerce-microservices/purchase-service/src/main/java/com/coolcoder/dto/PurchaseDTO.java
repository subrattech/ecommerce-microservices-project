package com.coolcoder.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

	@NotNull(message = "Customer ID required")
	private Long customerId;

	@NotNull(message = "Product ID required")
	private Long productId;

	@NotNull(message = "Quantity required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

	private LocalDateTime purchaseDate;
}
