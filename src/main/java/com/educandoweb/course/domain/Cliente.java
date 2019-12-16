package com.educandoweb.course.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.educandoweb.course.domain.enumeration.TipoCliente;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "cpf_ou_cnpj")
    private String cpfOuCnpj;

    @Column(name = "tipo_cliente")
    private Integer tipoCliente;

    @OneToMany(mappedBy = "cliente")
    private Set<Endereco> enderecos = new HashSet<>();
    
    @ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();

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

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Cliente email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public Cliente cpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
        return this;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public TipoCliente getTipoCliente() {
    	return TipoCliente.toEnum(tipoCliente);
    }

    public void setTipoCliente(TipoCliente tipo) {
        this.tipoCliente = tipo.getCod();
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public Cliente enderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    public Cliente addEnderecos(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setCliente(this);
        return this;
    }

    public Cliente removeEnderecos(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setCliente(null);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", cpfOuCnpj='" + getCpfOuCnpj() + "'" +
            ", tipoCliente='" + getTipoCliente() + "'" +
            "}";
    }
}
