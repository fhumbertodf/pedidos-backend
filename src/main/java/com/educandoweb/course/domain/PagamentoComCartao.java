package com.educandoweb.course.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.educandoweb.course.domain.enumeration.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Getter;
import lombok.Setter;

/**
 * A PagamentoComCartao.
 */
@Entity
@Table(name = "pagamento_com_cartao")
@JsonTypeName("pagamentoComCartao")
@Getter @Setter
public class PagamentoComCartao extends Pagamento {

	private static final long serialVersionUID = 1L;

	@Column(name = "numero_de_parcelas")
	private Integer numeroDeParcelas;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
	
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public PagamentoComCartao numeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
		return this;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
	
	public PagamentoComCartao estadoPagamento(EstadoPagamento estadoPagamento) {
		this.setEstadoPagamento(estadoPagamento);
		return this;
	}
	
	public PagamentoComCartao pedido(Pedido pedido) {
		this.setPedido(pedido);
		return this;
	}

	@Override
	public String toString() {
		return "PagamentoComCartao{" + "id=" + getId() + ", numeroDeParcelas=" + numeroDeParcelas + "}";
	}
}
