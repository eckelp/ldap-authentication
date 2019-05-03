package br.com.eckelp.ldap.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	private static final int TOKEN_VALIDITY = 30;
	private static final int REFRESH_VALIDITY = 3600 * 24; 
	
	private static final String STATIC_CLIENT_USER = "client_application_user";
	private static final String STATIC_CLIENT_PASS = "client_application_pass";
	
	private static final String APPLICATION_SECURITY_KEY = "$2y$18$Exx9kibwY2JOM6Gt.geGkew/rJfOE5rw116kjOQ7C2gH/3onQ5IxG";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		
		clients.inMemory()
			.withClient(STATIC_CLIENT_USER)
			.secret(encoder.encode(STATIC_CLIENT_PASS))
			.scopes("read", "write", "foo", "something_else")
			.authorizedGrantTypes("password", "refresh_token")			
			.accessTokenValiditySeconds(TOKEN_VALIDITY)
			.refreshTokenValiditySeconds(REFRESH_VALIDITY);
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.authenticationManager(authenticationManager)
			.reuseRefreshTokens(false);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		
		converter.setSigningKey(APPLICATION_SECURITY_KEY);
		
		return converter;
	}


	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	
	
	
}
