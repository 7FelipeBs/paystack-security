package com.paystack.security.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paystack.security.entitys.RefreshToken;
import com.paystack.security.entitys.Users;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	
	@Query("FROM RefreshToken WHERE token = :token")
	RefreshToken findByToken(String token);

	@Modifying
	int deleteByUser(Users user);

	@Modifying
	@Query("DELETE FROM RefreshToken WHERE token = :token")
	void deleteByToken(String token);
}