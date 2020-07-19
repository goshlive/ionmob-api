package com.ionmob.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ionmob.model.User;

/**
 * This class implements the UserDetails as defined by the Spring Security
 * 
 * @author I Made Putrama
 *
 */
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = -1730483527378159339L;
	
	private String userName;
	private String password;
	private boolean isActive;
	private List<GrantedAuthority> authorities;
	
	/**
	 * Mapping User roles and other attributes
	 * 
	 * @param user
	 */
	public UserDetailsImpl(User user) {
		this.userName = user.getUsername();
		this.password = user.getPassword();
		this.isActive = user.getIsActive() == 1 ? true : false;
		this.authorities = Arrays.stream(user.getRoles().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	public UserDetailsImpl() {}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
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
		return isActive;
	}
}
