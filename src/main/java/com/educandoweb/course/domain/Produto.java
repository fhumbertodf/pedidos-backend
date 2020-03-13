package com.educandoweb.course.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"categorias", "itens"})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private Double preco;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "produto_categorias",
               joinColumns = @JoinColumn(name = "produto_id"),
               inverseJoinColumns = @JoinColumn(name = "categorias_id"))
    private final Set<Categoria> categorias = new HashSet<>();

    @JsonIgnore
	@OneToMany(mappedBy="id.produto")
    private final Set<ItemPedido> itens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public Produto addCategorias(Categoria categoria) {
        this.categorias.add(categoria);
        categoria.getProdutos().add(this);
        return this;
    }

    public Produto removeCategorias(Categoria categoria) {
        this.categorias.remove(categoria);
        categoria.getProdutos().remove(this);
        return this;
    }

    public Produto addItens(ItemPedido itemPedido) {
        this.itens.add(itemPedido);
        return this;
    }

    public Produto removeItens(ItemPedido itemPedido) {
        this.itens.remove(itemPedido);
        return this;
    }
}
