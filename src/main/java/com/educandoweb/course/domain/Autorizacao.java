package com.educandoweb.course.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority")
@Getter @Setter
@EqualsAndHashCode
@ToString
public class Autorizacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;
    
}
