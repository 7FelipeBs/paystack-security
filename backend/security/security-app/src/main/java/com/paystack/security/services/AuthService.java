package com.paystack.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paystack.security.entitys.Roles;
import com.paystack.security.entitys.Users;
import com.paystack.security.enums.ERole;
import com.paystack.security.repositorys.IRolesRepository;
import com.paystack.security.repositorys.IUsersRepository;
import com.paystack.security.utils.JwtUtils;
import com.paystack.security.views.UserDetailsView;
import com.paystack.security.views.request.LoginRequestView;
import com.paystack.security.views.request.SignupRequestView;
import com.paystack.security.views.response.JwtResponseView;
import com.paystack.security.views.response.MessageResponseView;

import lombok.NonNull;

@Service
public class AuthService {

	private final @NonNull AuthenticationManager authenticationManager;

	private final @NonNull IUsersRepository usersRepository;

	private final @NonNull IRolesRepository rolesRepository;

	private final @NonNull PasswordEncoder encoder;

	private final @NonNull JwtUtils jwtUtils;
	
	private static final String MsgError = "Error: Role is not found.";

	public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
			IUsersRepository usersRepository, IRolesRepository rolesRepository, PasswordEncoder encoder) {

		this.jwtUtils = jwtUtils;
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
	}
	
	public ResponseEntity<?> signin(LoginRequestView loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsView userDetails = (UserDetailsView) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.toList();

		return ResponseEntity.ok(
				new JwtResponseView(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	public ResponseEntity<?> signup(SignupRequestView signUpRequest) {
		if (Boolean.TRUE.equals(usersRepository.existsByUsername(signUpRequest.getUsername()))) {
			return ResponseEntity.badRequest().body(new MessageResponseView("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(usersRepository.existsByEmail(signUpRequest.getEmail()))) {
			return ResponseEntity.badRequest().body(new MessageResponseView("Error: Email is already in use!"));
		}

		// Create new user's account
		Users user = new Users(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Roles> roles = new HashSet<>();

		if (strRoles == null) {
			Roles userRole = rolesRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException(MsgError));
			roles.add(userRole);
		} else {

			strRoles.forEach(role -> {

				switch (role) {
				case "admin":
					Roles adminRole = rolesRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException(MsgError));
					roles.add(adminRole);

					break;
				case "mod":
					Roles modRole = rolesRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException(MsgError));
					roles.add(modRole);

					break;
				default:
					Roles userRole = rolesRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException(MsgError));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		usersRepository.save(user);

		return ResponseEntity.ok(new MessageResponseView("User registered successfully!"));
	}
}
