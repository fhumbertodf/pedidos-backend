package com.educandoweb.course.security;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.repository.ClienteRepository;
import com.educandoweb.course.repository.UsuarioRepository;
import com.educandoweb.course.web.errors.UserNotActivatedException;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);
    
    private final ClienteRepository clienteRepository;
    
    private final UsuarioRepository userRepository;

    public DomainUserDetailsService(ClienteRepository clienteRepository, UsuarioRepository userRepository) {
    	this.clienteRepository = clienteRepository;
    	this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        if (new EmailValidator().isValid(login, null)) {
        	return clienteRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(cliente -> createSpringSecurityUser(login, cliente.getUser()))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private User createSpringSecurityUser(String lowercaseLogin, Usuario user) {
        if (!user.getActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }
}
