package com.educandoweb.course.service.dto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.educandoweb.course.domain.Autorizacao;
import com.educandoweb.course.domain.Usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO representing a user, with his authorities.
 */
@Getter @Setter
@ToString
public class UserDTO {
	
	public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    private Long id;

    @NotBlank
    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    //@Email
    //@Size(min = 5, max = 254)
    //private String email;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private Instant createdDate;

    private Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(Usuario user) {
        this.id = user.getId();
        this.login = user.getLogin();
        //this.email = user.getEmail();
        this.activated = user.getActivated();
        this.langKey = user.getLangKey();
        this.createdDate = user.getCreatedDate();        
        this.authorities = user.getAuthorities().stream()
            .map(Autorizacao::getName)
            .collect(Collectors.toSet());
    }
    
}
