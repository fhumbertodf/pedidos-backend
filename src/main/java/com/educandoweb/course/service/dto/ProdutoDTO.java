package com.educandoweb.course.service.dto;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}