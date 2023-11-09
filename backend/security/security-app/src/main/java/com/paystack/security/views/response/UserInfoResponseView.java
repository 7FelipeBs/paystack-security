package com.paystack.security.views.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponseView {

	private Long id;

	private String username;

	private String email;

	private List<String> roles;

}
