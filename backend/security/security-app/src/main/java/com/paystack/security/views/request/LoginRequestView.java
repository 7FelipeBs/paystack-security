package com.paystack.security.views.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestView {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
