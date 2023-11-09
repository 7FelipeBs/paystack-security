package com.paystack.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paystack.security.entitys.RefreshToken;
import com.paystack.security.entitys.Roles;
import com.paystack.security.entitys.Users;
import com.paystack.security.enums.ERole;
import com.paystack.security.exceptions.TokenRefreshException;
import com.paystack.security.repositorys.IRolesRepository;
import com.paystack.security.repositorys.IUsersRepository;
import com.paystack.security.utils.JwtUtils;
import com.paystack.security.views.UserDetailsView;
import com.paystack.security.views.request.LoginRequestView;
import com.paystack.security.views.request.SignupRequestView;
import com.paystack.security.views.response.MessageResponseView;
import com.paystack.security.views.response.UserInfoResponseView;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;

@Service
public class AuthService {

	private final @NonNull AuthenticationManager authenticationManager;

	private final @NonNull IUsersRepository usersRepository;

	private final @NonNull IRolesRepository rolesRepository;

	private final @NonNull PasswordEncoder encoder;
	
	private final @NonNull RefreshTokenService refreshTokenService;

	private final @NonNull JwtUtils jwtUtils;

	public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, IUsersRepository usersRepository,
			IRolesRepository rolesRepository, PasswordEncoder encoder, RefreshTokenService refreshTokenService) {
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;

		this.refreshTokenService = refreshTokenService;
		
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.encoder = encoder;
	}

	public ResponseEntity<?> signin(LoginRequestView loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsView userDetails = (UserDetailsView) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).toList();

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString()).body(new UserInfoResponseView(
						userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	public ResponseEntity<?> logoutUser() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!"anonymousUser".equals(principle.toString())) {
			Long userId = ((UserDetailsView) principle).getId();
			refreshTokenService.deleteByUserId(userId);
		}

		ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
		ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
				.body(new MessageResponseView("You've been signed out!"));
	}

	public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
		String refreshToken = jwtUtils.getJwtRefreshFromCookies(request);

		if ((refreshToken != null) && (refreshToken.length() > 0)) {
			return refreshTokenService.findByToken(refreshToken).map(refreshTokenService::verifyExpiration)
					.map(RefreshToken::getUser).map(user -> {
						ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

						return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
								.body(new MessageResponseView("Token is refreshed successfully!"));
					}).orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
		}

		return ResponseEntity.badRequest().body(new MessageResponseView("Refresh Token is empty!"));
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

		final String MsgError = "Error: Role is not found.";
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
