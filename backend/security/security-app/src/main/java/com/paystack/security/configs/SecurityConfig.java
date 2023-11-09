package com.paystack.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.paystack.security.configs.auths.AuthEntryPointJwt;
import com.paystack.security.configs.auths.AuthTokenFilters;
import com.paystack.security.services.UsersDetailsService;
import com.paystack.security.utils.JwtUtils;

import lombok.NonNull;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	private final @NonNull JwtUtils jwtUtils;
	private final @NonNull UsersDetailsService usersDetailsService;
	private final @NonNull AuthEntryPointJwt authEntryPointJwt;
	
	public SecurityConfig(JwtUtils jwtUtils, UsersDetailsService usersDetailsService, AuthEntryPointJwt authEntryPointJwt) {
		this.jwtUtils = jwtUtils;
		this.usersDetailsService = usersDetailsService;
		this.authEntryPointJwt = authEntryPointJwt;
	}

	
	@Bean
	public AuthTokenFilters authenticationJwtTokenFilter() {
		return new AuthTokenFilters(jwtUtils, usersDetailsService);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(usersDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/api/test/**").permitAll().anyRequest().authenticated());

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
