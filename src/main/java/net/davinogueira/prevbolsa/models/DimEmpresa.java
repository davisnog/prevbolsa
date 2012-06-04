package net.davinogueira.prevbolsa.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name="DimEmpresa.listarTodos", query="from DimEmpresa"),
	@NamedQuery(
			name="DimEmpresa.portfolioDoUsuario", 
			query="select e from DimEmpresa e join e.portfolio p where p.id = :usuario"
	),
	@NamedQuery(name="DimEmpresa.listarPorSimbolos", 
			query="select e from DimEmpresa e where e.simbolo in (:simbolos)"
	),
	@NamedQuery(name="DimEmpresa.buscarForaPortfolio", 
			query="select e from DimEmpresa e left outer join fetch e.portfolio"
	)
})
public class DimEmpresa implements Serializable {
	
	private static final long serialVersionUID = -1803816584359516028L;

	@Id
	private String simbolo;
	
	private String nome;
	
	@Temporal(TemporalType.DATE)
	private Date cadastradoEm;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizadoEm;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "portfolio", targetEntity = Usuario.class)
	private Set<Usuario> portfolio;

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setCadastradoEm(Date cadastradoEm) {
		this.cadastradoEm = cadastradoEm;
	}

	public Date getCadastradoEm() {
		return cadastradoEm;
	}

	public void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public Date getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setPortfolio(Set<Usuario> portfolio) {
		this.portfolio = portfolio;
	}

	public Set<Usuario> getPortfolio() {
		if(this.portfolio == null){
			return new HashSet<Usuario>();
		}
		return portfolio;
	}
	
	@Override
	public String toString() {
		String result = this.simbolo + ".SA - " + this.nome;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((simbolo == null) ? 0 : simbolo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DimEmpresa other = (DimEmpresa) obj;
		
		if (simbolo == null) {
			if (other.simbolo != null)
				return false;
		} else if (!simbolo.equals(other.simbolo))
			return false;
		return true;
	}
}
