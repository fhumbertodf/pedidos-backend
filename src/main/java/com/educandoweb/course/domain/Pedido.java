package com.educandoweb.course.domain;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"pagamento", "cliente", "enderecoDeEntrega", "itens"})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    @Column(name = "instante")
    private Date instante;

    @OneToOne(mappedBy = "pedido", cascade=CascadeType.ALL)
    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="endereco_de_entrega_id")
    private Endereco enderecoDeEntrega;

    @OneToMany(mappedBy="id.pedido")
    private final Set<ItemPedido> itens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove.
    
    public Pedido addItens(ItemPedido itemPedido) {
        this.itens.add(itemPedido);
        return this;
    }

    public Pedido removeItens(ItemPedido itemPedido) {
        this.itens.remove(itemPedido);
        return this;
    }
}