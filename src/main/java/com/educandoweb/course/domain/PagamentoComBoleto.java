package com.educandoweb.course.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * A PagamentoComBoleto.
 */
@Entity
@Table(name = "pagamento_com_boleto")
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "data_vencimento")
    private Date dataVencimento;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "data_pagamento")
    private Date dataPagamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Date getDataVencimento() {
        return dataVencimento;
    }

    public PagamentoComBoleto dataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public PagamentoComBoleto dataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "PagamentoComBoleto{" +
            "id=" + getId() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            "}";
    }
}
