package br.com.eckelp.ldap.authentication.config.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ApiEncoder {

	
	public static PasswordEncoder build() {
		return new BCryptPasswordEncoder();
	}
	
	public static String encode(String toEncode) {
		PasswordEncoder encoder = ApiEncoder.build();
				
		String encoded = encoder.encode(toEncode);
		
		return encoded;
	}
	
}
