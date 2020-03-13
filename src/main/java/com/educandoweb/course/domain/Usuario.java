package com.educandoweb.course.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"login", "activated", "langKey", "activationKey"})
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @NotNull
    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)    
    private Boolean activated = Boolean.FALSE;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")    
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @BatchSize(size = 20)    
    private Set<Autorizacao> authorities = new HashSet<>();
    
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore    
    private Instant createdDate = Instant.now();
    
    @OneToOne(mappedBy = "user", cascade=CascadeType.ALL) 
    private Cliente cliente;

    // Lowercase the login before saving it in database
    
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }
    	
}
