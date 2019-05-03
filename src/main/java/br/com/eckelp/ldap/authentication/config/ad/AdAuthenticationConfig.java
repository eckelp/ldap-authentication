package br.com.eckelp.ldap.authentication.config.ad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Component;

import br.com.eckelp.ldap.authentication.config.mapper.CustomUserMapper;

@Component
public class AdAuthenticationConfig {
	
	private static final String AD_URL = "ldap://ip";
	private static final String AD_DOMAIN = "domain";
	private static final String USER_PATTERN = "sAMAccountName={1}";
	
	@Autowired
	private CustomUserMapper customUserMapper;
	
	@Bean
	public ActiveDirectoryLdapAuthenticationProvider getLdapProvider(){
		
		ActiveDirectoryLdapAuthenticationProvider adProvider = 
				new ActiveDirectoryLdapAuthenticationProvider(AD_DOMAIN, AD_URL);
		
	    adProvider.setConvertSubErrorCodesToExceptions(true);
	    adProvider.setUseAuthenticationRequestCredentials(true);
		    
        adProvider.setSearchFilter(USER_PATTERN);
        
        adProvider.setUserDetailsContextMapper(customUserMapper);
        
        return adProvider;
        
	}
	
}