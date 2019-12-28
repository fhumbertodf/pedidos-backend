package com.educandoweb.course.domain;

import java.time.LocalDate;

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
    private LocalDate dataVencimento;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public PagamentoComBoleto dataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public PagamentoComBoleto dataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
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
