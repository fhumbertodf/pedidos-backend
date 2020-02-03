package com.educandoweb.course.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.educandoweb.course.domain.Autorizacao;
import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.service.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// User

	public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

	private Long id;

	@NotBlank
	@Pattern(regexp = LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	private boolean activated = false;

	@Size(min = 2, max = 10)
	private String langKey;

	private Instant createdDate;

	private Set<String> authorities;

	// Cliente

	@NotEmpty
	@Length(min = 5, max = 120)
	private String nome;

	@NotEmpty
	@Email
	private String email;
	
	public ClienteDTO(Usuario user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.activated = user.getActivated();
		this.langKey = user.getLangKey();
		this.createdDate = user.getCreatedDate();
		this.authorities = user.getAuthorities().stream().map(Autorizacao::getName).collect(Collectors.toSet());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
}
