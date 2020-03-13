package com.educandoweb.course.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A ItemPedido.
 */
@Entity
@Table(name = "item_pedido")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
	@EmbeddedId	
	private ItemPedidoPK id = new ItemPedidoPK();

    @Column(name = "desconto")
    private Double desconto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "preco")
    private Double preco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}
	
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public ItemPedido setPedido(Pedido pedido) {
		id.setPedido(pedido);
		return this;
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public ItemPedido setProduto(Produto produto) {
		id.setProduto(produto);
		return this;
	}

}
