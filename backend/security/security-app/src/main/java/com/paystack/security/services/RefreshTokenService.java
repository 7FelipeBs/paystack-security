package com.paystack.security.services;

import java.time.Instant;
import java.util.Optional;
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

	public RefreshTokenService(IUsersRepository usersRepository, IRefreshTokenRepository refreshTokenRepository) {
		this.usersRepository = usersRepository;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();
		
		var users = usersRepository.findById(userId).orElse(null);
		
		if(users == null) return null;
		
		refreshToken.setUser(users);
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		usersRepository.findById(userId).ifPresent(refreshTokenRepository::deleteByUser);
	}
}
