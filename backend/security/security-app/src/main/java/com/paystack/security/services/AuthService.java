package com.paystack.security.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paystack.security.entitys.CookieUsers;
import com.paystack.security.entitys.RefreshToken;
import com.paystack.security.entitys.Roles;
import com.paystack.security.entitys.Users;
import com.paystack.security.enums.ERole;
import com.paystack.security.repositorys.IRolesRepository;
import com.paystack.security.repositorys.IUsersRepository;
import com.paystack.security.utils.JwtUtils;
import com.paystack.security.views.UserDetailsView;
import com.paystack.security.views.request.LoginRequestView;
import com.paystack.security.views.request.SignupRequestView;
import com.paystack.security.views.response.MessageResponseView;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	private final @NonNull AuthenticationManager authenticationManager;

	private final @NonNull IUsersRepository usersRepository;

	private final @NonNull IRolesRepository rolesRepository;

	private final @NonNull PasswordEncoder encoder;

	private final @NonNull RefreshTokenService refreshTokenService;

	private final @NonNull CookieUsersService cookieUsersService;

	private final @NonNull JwtUtils jwtUtils;

	public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, IUsersRepository usersRepository,
			IRolesRepository rolesRepository, PasswordEncoder encoder, RefreshTokenService refreshTokenService,
			CookieUsersService cookieUsersService) {
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;

		this.refreshTokenService = refreshTokenService;
		this.cookieUsersService = cookieUsersService;

		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.encoder = encoder;
	}

	@Transactional
	public ResponseEntity<?> signup(SignupRequestView signUpRequest) {
		if (Boolean.TRUE.equals(usersRepository.existsByUsername(signUpRequest.getUsername()))) {
			return ResponseEntity.badRequest().body(new MessageResponseView("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(usersRepository.existsByEmail(signUpRequest.getEmail()))) {
			return ResponseEntity.badRequest().body(new MessageResponseView("Error: Email is already in use!"));
		}

		Users user = new Users(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

		Set<Roles> roles = new HashSet<>();
		Roles userRole = rolesRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		usersRepository.save(user);

		return ResponseEntity.ok(new MessageResponseView("User registered successfully!"));
	}

	@Transactional
	public ResponseEntity<?> signin(LoginRequestView loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsView userDetails = (UserDetailsView) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		RefreshToken refreshToken = refreshTokenService.create(userDetails.getId());

		var refreshTokenCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

		var cryptEncoder = new BCryptPasswordEncoder();
		cookieUsersService
				.create(new CookieUsers(refreshTokenCookie.getValue(), cryptEncoder.encode(jwtCookie.getValue()),
						LocalDateTime.now().plus(jwtCookie.getMaxAge()), userDetails.getId()));
		
		var tokenCookies = refreshToken.getToken().concat(";").concat(refreshToken.getExpiryDate().toString());
		return ResponseEntity.ok().body(tokenCookies);
	}

	@Transactional
	public ResponseEntity<?> logoutUser(String token) {
		String msg = "You've been signed out!";
		try {
			var entity = refreshTokenService.findByToken(token.substring(1, token.length() - 1));
			if (entity == null) {
				return ResponseEntity.ok().body(new MessageResponseView(msg));
			}

			refreshTokenService.deleteByUserId(entity.getUser().getId());
			cookieUsersService.deleteByUser(entity.getUser());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return ResponseEntity.ok().body(new MessageResponseView(msg));
	}

	@Transactional
	public ResponseEntity<?> refreshtoken(String token) {
		try {
			if (token == null) return null;

			RefreshToken refresh = refreshTokenService.findByToken(token.substring(1, token.length() - 1));

			if (refresh == null) return null;

			// valid date expiration token
			refreshTokenService.verifyExpiration(refresh);

			// if refresh is valid, generate new cookie token
			var cookiesJwt = jwtUtils.generateJwtCookie(refresh.getUser());

			// update refresh together with cookieUser
			var oldToken = refresh.getToken();
			refresh = refreshTokenService.update(refresh);
			cookieUsersService.update(refresh, oldToken, cookiesJwt);
			

			var tokenCookies = refresh.getToken().concat(";").concat(refresh.getExpiryDate().toString());
			return ResponseEntity.ok().body(tokenCookies);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return ResponseEntity.badRequest()
				.body(new MessageResponseView("Refresh Token is invalid! Please make a signin!"));
	}

}
