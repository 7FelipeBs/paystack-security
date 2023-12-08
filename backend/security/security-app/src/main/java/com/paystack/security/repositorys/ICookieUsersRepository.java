package com.paystack.security.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paystack.security.entitys.CookieUsers;

@Repository
public interface ICookieUsersRepository extends JpaRepository<CookieUsers, Long> {

	@Query("FROM CookieUsers WHERE refreshTokenGuid = :refreshTokenGuid")
	CookieUsers findByToken(String refreshTokenGuid);

	@Modifying
	@Query("DELETE FROM CookieUsers WHERE id = :id")
	void deleteCookieById(Long id);

	@Modifying
	@Query("DELETE FROM CookieUsers WHERE userId = :userId")
	void deleteByUser(Long userId);

}