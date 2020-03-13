package com.educandoweb.course.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.educandoweb.course.domain.enumeration.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;

/**
 * A PagamentoComBoleto.
 */
@Entity
@Table(name = "pagamento_com_boleto")
@JsonTypeName("pagamentoComBoleto")
@Getter @Setter
public class PagamentoComBoleto extends Pagamento {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "data_vencimento")
    private Date dataVencimento;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "data_pagamento")
    private Date dataPagamento;

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
    
    public PagamentoComBoleto estadoPagamento(EstadoPagamento estadoPagamento) {
		this.setEstadoPagamento(estadoPagamento);
		return this;
	}
    
    public PagamentoComBoleto pedido(Pedido pedido) {
		this.setPedido(pedido);
		return this;
	}

    @Override
    public String toString() {
        return "PagamentoComBoleto{" +
            "id=" + getId() +
            ", dataVencimento='" + dataVencimento + "'" +
            ", dataPagamento='" + dataPagamento + "'" +
            "}";
    }
}
