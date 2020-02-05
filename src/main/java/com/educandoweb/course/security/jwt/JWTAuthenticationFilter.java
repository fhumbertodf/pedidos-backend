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

import com.educandoweb.course.service.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		try {
			LoginDTO creds = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword());
			return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		try {
			LoginDTO creds = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
			boolean rememberMe = (creds.isRememberMe() == null) ? false : creds.isRememberMe();
			String jwt = tokenProvider.createToken(authResult, rememberMe);
			response.addHeader(JWTAuthorizationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			response.addHeader("access-control-expose-headers", "Authorization");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		 long date = new Date().getTime();
         String json = "{\"timestamp\": " + date + ", "
             + "\"status\": 401, "
             + "\"error\": \"Não autorizado\", "
             + "\"message\": \"Email ou senha inválidos\", "
             + "\"path\": \"/login\"}";
		
		response.setStatus(401);
        response.setContentType("application/json"); 
        response.getWriter().append(json);
	}
}

