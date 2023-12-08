package com.paystack.security.entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cookie_users")
public class CookieUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cookie_user_id")
	private long id;

	@Column(name = "refresh_token_guid", nullable = false, unique = true)
	private String refreshTokenGuid;

	@Column(name = "cookie_token_guid", nullable = false, unique = true)
	private String cookieTokenGuid;

	@Column(name = "user_id", nullable = false, unique = true)
	private long userId;

	@Column(nullable = false, name = "expire_date_cookie")
	private LocalDateTime expireDateCookie;

	public CookieUsers(String refreshTokenGuid, String cookieTokenGuid, LocalDateTime expireDateCookie, long userId) {
		this.refreshTokenGuid = refreshTokenGuid;
		this.cookieTokenGuid = cookieTokenGuid;
		this.expireDateCookie = expireDateCookie;
		this.userId = userId;
	}

}
