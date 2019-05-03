package br.com.eckelp.ldap.authentication.models;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SystemUser implements UserDetails{
	
	
	private static final long serialVersionUID = -4285684734947485016L;

	private BigInteger id;
	
	private Long numberFromAd;
	
	private String username;
	private String password;
	
	private SystemUser() {
		super();
	}
	
	public static SystemUser build() {
		return new SystemUser();
	}
	
	
	public BigInteger getId() {
		return this.id;
	}

	public SystemUser withId(BigInteger id) {
		this.id = id;
		
		return this;
	}

	public Long getNumberFromAd() {
		return this.numberFromAd;
	}

	public SystemUser withNumberFromAd(Long numberFromAd) {
		this.numberFromAd = numberFromAd;
		
		return this;
	}

	public String getUsername() {
		return this.username;
	}

	public SystemUser withUsername(String username) {
		this.username = username;
		
		return this;
	}

	public SystemUser withPassword(String password) {
		this.password = password;
		
		return this;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO get all authorities to user from yout system
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Check if account are expired or not
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Check if credentials are locked or not
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Check if credentials are expired or not
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Check if user is enable in your system
		return true;
	}
	
	

}
