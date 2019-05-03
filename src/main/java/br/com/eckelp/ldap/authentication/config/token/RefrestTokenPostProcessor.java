package br.com.eckelp.ldap.authentication.config.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefrestTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken>{

	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	/**
	 * This methods remove refresh token from body and add into a Cookie
	 * That's good if you're using Https
	 */
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
	
		String refreshToken = body.getRefreshToken().getValue();
		
		
		addCookieRefreshToken(request, response, refreshToken);
		
		removeBodyRequestToken(token);
		
		return body;
		
	}

	private void removeBodyRequestToken(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
		
	}

	private void addCookieRefreshToken(ServerHttpRequest request, ServerHttpResponse response, String refreshToken) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse res = ((ServletServerHttpResponse) response).getServletResponse();
		
		Cookie refreshTokenCookie = new Cookie(TokenConfig.COOKIE_NAME, refreshToken);
		
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(3600* 24 * 15);
		
		res.addCookie(refreshTokenCookie);
	}

}
