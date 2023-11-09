package com.paystack.security.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paystack.security.entitys.Users;

public interface IUsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}