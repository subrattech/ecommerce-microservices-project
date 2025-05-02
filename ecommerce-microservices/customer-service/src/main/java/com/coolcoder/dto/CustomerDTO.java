package com.coolcoder.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
	private Long id;

	@NotBlank(message = "Name is required")
	private String name;

	@Email(message = "Valid email required")
	@NotBlank(message = "Email is required")
	private String email;

	private String address;
}
