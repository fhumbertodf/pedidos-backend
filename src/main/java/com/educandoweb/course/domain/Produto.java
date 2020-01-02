package com.educandoweb.course.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
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
    private Set<Categoria> categorias = new HashSet<>();

    @JsonIgnore
	@OneToMany(mappedBy="id.produto")
    private Set<ItemPedido> itens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Produto preco(Double preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

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

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public Produto addItens(ItemPedido itemPedido) {
        this.itens.add(itemPedido);
        return this;
    }

    public Produto removeItens(ItemPedido itemPedido) {
        this.itens.remove(itemPedido);
        return this;
    }

    public void setItens(Set<ItemPedido> itemPedidos) {
        this.itens = itemPedidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            "}";
    }
}
