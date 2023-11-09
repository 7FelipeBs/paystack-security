package com.paystack.security.views;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paystack.security.entitys.Users;

public class UserDetailsView implements UserDetails {

	  private static final long serialVersionUID = 1L;

	  private Long id;

	  private String username;

	  private String email;

	  @JsonIgnore
	  private String password;

	  private Collection<? extends GrantedAuthority> authorities;

	  public UserDetailsView(Long id, String username, String email, String password,
	      Collection<? extends GrantedAuthority> authorities) {
	    this.id = id;
	    this.username = username;
	    this.email = email;
	    this.password = password;
	    this.authorities = authorities;
	  }

	  public static UserDetailsView build(Users user) {
	    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
	                               .map(role -> new SimpleGrantedAuthority(role.getName().name()))
	                               .toList();

	    return new UserDetailsView(user.getId(), 
	                               user.getUsername(), 
	                               user.getEmail(),
	                               user.getPassword(), 
	                               authorities);
	  }

	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return authorities;
	  }

	  public Long getId() {
	    return id;
	  }

	  public String getEmail() {
	    return email;
	  }

	  @Override
	  public String getPassword() {
	    return password;
	  }

	  @Override
	  public String getUsername() {
	    return username;
	  }

	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserDetailsView)) {
			return false;
		}
		UserDetailsView other = (UserDetailsView) obj;
		return Objects.equals(id, other.id);
	}
}
