package com.educandoweb.course.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Estado.
 */
@Entity
@Table(name = "estado")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "estado")
    private Set<Cidade> cidades = new HashSet<>();

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

    public Estado nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Cidade> getCidades() {
        return cidades;
    }

    public Estado cidades(Set<Cidade> cidades) {
        this.cidades = cidades;
        return this;
    }

    public Estado addCidades(Cidade cidade) {
        this.cidades.add(cidade);
        cidade.setEstado(this);
        return this;
    }

    public Estado removeCidades(Cidade cidade) {
        this.cidades.remove(cidade);
        cidade.setEstado(null);
        return this;
    }

    public void setCidades(Set<Cidade> cidades) {
        this.cidades = cidades;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estado)) {
            return false;
        }
        return id != null && id.equals(((Estado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Estado{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
