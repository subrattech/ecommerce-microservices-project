package com.coolcoder.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
	private Long id;

	@NotNull(message = "Purchase ID required")
	private Long purchaseId;

	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String status;
	private Double amount;
	private LocalDateTime createdAt;
}