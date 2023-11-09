package com.paystack.security.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.paystack.security.entitys.RefreshToken;
import com.paystack.security.entitys.Users;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	@Modifying
	int deleteByUser(Users user);
}