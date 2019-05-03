package br.com.eckelp.ldap.authentication.config.mapper;

import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Component;

import br.com.eckelp.ldap.authentication.models.SystemUser;

@Component
public class CustomUserMapper extends LdapUserDetailsMapper{
	
    @Override
    public SystemUser mapUserFromContext(DirContextOperations ctx, 
    		String username, Collection<? extends GrantedAuthority> authorities){

    	UserDetails userDetails = super.mapUserFromContext(ctx, username, authorities);
    	
    	
    	Long numberFromAd = Long.valueOf(ctx.getStringAttribute("employeeNumber"));

        SystemUser user = 
    		SystemUser.build()
				.withUsername(userDetails.getUsername())
				.withPassword(userDetails.getPassword())
				.withNumberFromAd(numberFromAd);
		
        return user;
        
    }
    

}