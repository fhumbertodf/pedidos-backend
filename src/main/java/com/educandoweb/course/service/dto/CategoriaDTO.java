package com.educandoweb.course.service.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A DTO for the {@link com.educandoweb.course.domain.Categoria} entity.
 */
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString
public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    private String nome;
    
}