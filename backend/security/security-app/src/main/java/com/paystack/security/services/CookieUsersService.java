package com.paystack.security.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.paystack.security.entitys.CookieUsers;
import com.paystack.security.entitys.RefreshToken;
import com.paystack.security.entitys.Users;
import com.paystack.security.repositorys.ICookieUsersRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class CookieUsersService {

	@Value("${security.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	private final @NonNull ICookieUsersRepository cookieUsersRepository;
	private final @NonNull PasswordEncoder cryptEncoder;
	
	public CookieUsersService(ICookieUsersRepository cookieUsersRepository, PasswordEncoder cryptEncoder) {
		this.cookieUsersRepository = cookieUsersRepository;
		this.cryptEncoder = cryptEncoder;
	}
	
	public CookieUsers create(CookieUsers entity) {
		return cookieUsersRepository.save(entity);
	}
	
	public CookieUsers update(RefreshToken refreshToken, String oldToken, ResponseCookie cookie) {
		
		var cookieUser = cookieUsersRepository.findByToken(oldToken);
		if(cookieUser != null) {
			
			if(cookieUser.getExpireDateCookie().isBefore(LocalDateTime.now())) {
				cookieUser.setExpireDateCookie(LocalDateTime.now().plus(cookie.getMaxAge()));
				cookieUser.setCookieTokenGuid(cryptEncoder.encode(cookie.getValue()));
			} 
			
			cookieUser.setRefreshTokenGuid(refreshToken.getToken());
			return cookieUsersRepository.save(cookieUser);
		}
		
		return null;
	}
	


	@Transactional
	public void deleteById(Long id) {
		if(cookieUsersRepository.findById(id).isPresent()) cookieUsersRepository.deleteCookieById(id);
	}

	@Transactional
	public void deleteByUser(Users user) {
		cookieUsersRepository.deleteByUser(user.getId());
	}
}
