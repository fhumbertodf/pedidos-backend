package com.educandoweb.course.security.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public JWTAuthenticationFilter(TokenProvider tokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				getUsernameParameter(), getPasswordParameter());
		return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		boolean rememberMe = (request.getParameter("rememberMe") == null) ? false
				: Boolean.valueOf(request.getParameter("rememberMe"));
		String jwt = tokenProvider.createToken(authResult, rememberMe);
		response.addHeader(JWTAuthorizationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		response.addHeader("access-control-expose-headers", "Authorization");
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		long date = new Date().getTime();
		String json = "{\"timestamp\": " + date + ", " + 
				"\"status\": 401, " + 
				"\"error\": \"Não autorizado\", " + 
				"\"message\": \"Email ou senha inválidos\", " + 
				"\"path\": \"/login\"}";

		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json);
	}
}
