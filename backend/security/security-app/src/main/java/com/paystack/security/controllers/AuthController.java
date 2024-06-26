package com.paystack.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paystack.security.services.AuthService;
import com.paystack.security.views.request.LoginRequestView;
import com.paystack.security.views.request.SignupRequestView;

import jakarta.validation.Valid;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final @NonNull AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody LoginRequestView loginRequest) {
		return authService.signin(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestView signUpRequest) {
		return authService.signup(signUpRequest);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(@RequestBody String refreshToken) {
		return authService.logoutUser(refreshToken);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody String refreshToken) {
		return authService.refreshtoken(refreshToken);
	}
}
