package com.paystack.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paystack.security.entitys.Users;
import com.paystack.security.repositorys.IUsersRepository;
import com.paystack.security.views.UserDetailsView;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class UsersDetailsService implements UserDetailsService {

	private final @NonNull IUsersRepository usersRepository;

	public UsersDetailsService(IUsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsView.build(user);
	}
}
