package com.paystack.security.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paystack.security.entitys.RefreshToken;
import com.paystack.security.exceptions.TokenRefreshException;
import com.paystack.security.repositorys.IRefreshTokenRepository;
import com.paystack.security.repositorys.IUsersRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class RefreshTokenService {

	@Value("${security.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	private final @NonNull IRefreshTokenRepository refreshTokenRepository;
	private final @NonNull IUsersRepository usersRepository;
	private final @NonNull CookieUsersService cookieUsersService;
	
	public RefreshTokenService(IUsersRepository usersRepository, IRefreshTokenRepository refreshTokenRepository, CookieUsersService cookieUsersService) {
		this.usersRepository = usersRepository;
		this.refreshTokenRepository = refreshTokenRepository;
		this.cookieUsersService = cookieUsersService;
	}

	public RefreshToken findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken create(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		var users = usersRepository.findById(userId).orElse(null);

		if (users == null)
			return null;

		refreshToken.setUser(users);
		refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		return refreshTokenRepository.save(refreshToken);
	}
	
	public RefreshToken update(RefreshToken refreshToken) {
		if(refreshToken.getId() == null)  {
			throw new TokenRefreshException(refreshToken.getToken(),
					"Error Refresh token. Please make a new signin request!");
		}
		
		refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		return refreshTokenRepository.save(refreshToken);
	}

	public void verifyExpiration(RefreshToken entity) {
		if (entity.getExpiryDate().isBefore(LocalDateTime.now())) {
			
			cookieUsersService.deleteByUser(entity.getUser());
			refreshTokenRepository.deleteById(entity.getId());
			
			throw new TokenRefreshException(entity.getToken(),
					"Refresh token was expired. Please make a new signin request!");
		}
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		usersRepository.findById(userId).ifPresent(refreshTokenRepository::deleteByUser);
	}
}
