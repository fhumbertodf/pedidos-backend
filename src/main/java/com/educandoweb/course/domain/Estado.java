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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A Estado.
 */
@Entity
@Table(name = "estado")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = "cidades")
public class Estado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "estado")    
    private Set<Cidade> cidades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
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
}
