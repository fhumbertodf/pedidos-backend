package com.educandoweb.course.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "produtos")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "categorias")
    private final Set<Produto> produtos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public Categoria addProdutos(Produto produto) {
        this.produtos.add(produto);
        produto.getCategorias().add(this);
        return this;
    }

    public Categoria removeProdutos(Produto produto) {
        this.produtos.remove(produto);
        produto.getCategorias().remove(this);
        return this;
    }    
}
