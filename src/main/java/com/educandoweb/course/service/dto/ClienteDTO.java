package com.educandoweb.course.service.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.educandoweb.course.domain.Usuario;
import com.educandoweb.course.service.validation.ClienteUpdate;

import lombok.Getter;
import lombok.Setter;

@ClienteUpdate
@Getter @Setter
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// User

	public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

	private Long id;

	@NotBlank
	@Pattern(regexp = LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	@Size(min = 2, max = 10)
	private String langKey;

	// Cliente

	@NotEmpty
	@Length(min = 5, max = 120)
	private String nome;
	
	@Email
    @Size(min = 5, max = 254)
	private String email;
	
	@NotEmpty
	private String cpfOuCnpj;

	private Integer tipoCliente;
	
	@NotEmpty
	private String logradouro;

	@NotEmpty
	private String numero;

	private String complemento;

	private String bairro;

	@NotEmpty
	private String cep;
	
	@NotEmpty
	private String telefone1;

	private String telefone2;
	
	private String telefone3;

	private Long cidadeId;
	
	public ClienteDTO(Usuario user) {
		this.id = user.getId();
		this.login = user.getLogin();		
		this.langKey = user.getLangKey();		
	}	
}
