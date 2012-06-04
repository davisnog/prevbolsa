package net.davinogueira.prevbolsa.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name="Usuario.validaLogin", 
			query="from Usuario u where u.login = :login and u.senha = :senha and u.ativo = :ativo"
	),
	@NamedQuery(name="Usuario.existente", 
			query="select count(*) from Usuario u where u.login = :login"
	)
})
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private Long id;
	private String nome;
	private String login;
	private String senha;
	private boolean ativo = true;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizacao = new Date();
	
	@ManyToMany(cascade = CascadeType.ALL, targetEntity = DimEmpresa.class, fetch = FetchType.EAGER)
	@JoinTable(name = "portfolio", 
			joinColumns=@JoinColumn(name="usuario_id"),
	        inverseJoinColumns=@JoinColumn(name="empresa_id"))
	private Set<DimEmpresa> portfolio;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtualizacao(Date atualizacao) {
		this.atualizacao = atualizacao;
	}

	public Date getAtualizacao() {
		return atualizacao;
	}

	public void setPortfolio(Set<DimEmpresa> portfolio) {
		this.portfolio = portfolio;
	}

	public Set<DimEmpresa> getPortfolio() {
		if(this.portfolio == null){
			return new HashSet<DimEmpresa>();
		}
		return portfolio;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Usuario other = (Usuario) obj;
		if (id != other.id && (id == null || !id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login + "]";
	}
	
}
