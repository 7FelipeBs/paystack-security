package com.paystack.security.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paystack.security.entitys.Roles;
import com.paystack.security.enums.ERole;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {
	  Optional<Roles> findByName(ERole name);
}