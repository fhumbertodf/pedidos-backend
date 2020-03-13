package com.educandoweb.course.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.educandoweb.course.domain.enumeration.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A Pagamento.
 */
@Entity
@Table(name = "pagamento")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@Getter @Setter
@EqualsAndHashCode(of = "id")
public abstract class Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "estado_pagamento")
	private Integer estadoPagamento;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId
	private Pedido pedido;

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
	
	public EstadoPagamento getEstadoPagamento() {
		return EstadoPagamento.toEnum(estadoPagamento);
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento.getCod();
	}

}
