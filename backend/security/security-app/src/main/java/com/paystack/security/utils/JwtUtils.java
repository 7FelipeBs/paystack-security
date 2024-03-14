package com.paystack.security.utils;

import java.security.Key;
import java.util.Date;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.paystack.security.entitys.Users;
import com.paystack.security.views.UserDetailsView;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${security.app.jwtSecret}")
	private String jwtSecret;

	@Value("${security.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${security.app.jwtCookieName}")
	private String jwtCookie;

	@Value("${security.app.jwtRefreshCookieName}")
	private String jwtRefreshCookie;

	public ResponseCookie generateJwtCookie(UserDetailsView userPrincipal) {
		String jwt = generateTokenFromUsername(userPrincipal.getUsername());
		return generateCookie(jwtCookie, jwt, "/api", true);
	}

	public ResponseCookie generateJwtCookie(Users user) {
		String jwt = generateTokenFromUsername(user.getUsername());
		return generateCookie(jwtCookie, jwt, "/api", true);
	}

	public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
		return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken", false);
	}

	public String getJwtFromCookies(HttpServletRequest request) {
		return getCookieValueByName(request, jwtCookie);
	}

	public ResponseCookie getCleanJwtCookie() {
		return ResponseCookie.from(jwtCookie, null).path("/api").build();
	}

	public ResponseCookie getCleanJwtRefreshCookie() {
		return ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refreshtoken").build();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	private ResponseCookie generateCookie(String name, String value, String path, boolean secure) {
		return ResponseCookie.from(name, value).path(path).maxAge(60 * 10).secure(true).httpOnly(secure).build();
	}
	
	private String getCookieValueByName(HttpServletRequest request, String name) {
		Cookie cookie = WebUtils.getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}
}
