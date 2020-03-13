package com.educandoweb.course.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.educandoweb.course.domain.enumeration.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Getter @Setter
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"enderecos", "telefones", "pedidos"})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id    
    private Long id;

    @Column(name = "nome")
    private String nome;
	
	@Email
    @Size(min = 5, max = 254)
    @Column(name = "email", length = 254, unique = true)
    private String email;

    @Column(name = "cpf_ou_cnpj")
    private String cpfOuCnpj;

    @Column(name = "tipo_cliente")
    private Integer tipoCliente;

    @OneToMany(mappedBy = "cliente", cascade=CascadeType.ALL)
    private Set<Endereco> enderecos = new HashSet<>();

    @ElementCollection
	@CollectionTable(name="TELEFONE")
    private Set<String> telefones = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")    
    private Set<Pedido> pedidos = new HashSet<>();
    
    @JsonIgnore
	@OneToOne
	@JoinColumn(name="user_id")
	@MapsId  
    private Usuario user;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public TipoCliente getTipoCliente() {
        return TipoCliente.toEnum(tipoCliente);
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente.getCod();
    }
    
    public Cliente tipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente.getCod();
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

    public Cliente addTelefones(String telefone) {
        this.telefones.add(telefone);
        return this;
    }

    public Cliente removeTelefones(String telefone) {
        this.telefones.remove(telefone);
        return this;
    }

    public Cliente addPedidos(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setCliente(this);
        return this;
    }

    public Cliente removePedidos(Pedido pedido) {
        this.pedidos.remove(pedido);
        pedido.setCliente(null);
        return this;
    }   
}
