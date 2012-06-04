package net.davinogueira.prevbolsa.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name="FatoQuota.porPeriodo",
			query="select q from FatoQuota q " +
					"where q.empresa.simbolo = :simbolo " +
					"and q.lancadoEm.id >= :inicio " +
					"and q.lancadoEm.id <= :fim"
	)
})
public class FatoQuota implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue
	private Long id;
	private Double abertura;
	private Double fechamento;
	private Double baixa;
	private Double alta;
	private Long volume;

	@ManyToOne(fetch= FetchType.LAZY)
	private DimEmpresa empresa;

	@ManyToOne(fetch= FetchType.LAZY)
	private DimTempo lancadoEm;
	
	public FatoQuota(DimTempo lancadoEm, Double abertura, Double baixa,
			Double alta, Double fechamento, Long volume, DimEmpresa empresa) {
		super();
		this.setLancadoEm(lancadoEm);
		this.abertura = abertura;
		this.fechamento = fechamento;
		this.baixa = baixa;
		this.alta = alta;
		this.volume = volume;
		this.setEmpresa(empresa);
	}

	public FatoQuota(DimEmpresa empresa) {
		this.empresa = empresa;
	}
	

	public FatoQuota() {
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAbertura(Double abertura) {
		this.abertura = abertura;
	}

	public Double getAbertura() {
		return abertura;
	}

	public void setFechamento(Double fechamento) {
		this.fechamento = fechamento;
	}

	public Double getFechamento() {
		return fechamento;
	}

	public void setBaixa(Double baixa) {
		this.baixa = baixa;
	}

	public Double getBaixa() {
		return baixa;
	}

	public void setAlta(Double alta) {
		this.alta = alta;
	}

	public Double getAlta() {
		return alta;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Long getVolume() {
		return volume;
	}

	public void setEmpresa(DimEmpresa empresa) {
		this.empresa = empresa;
	}

	public DimEmpresa getEmpresa() {
		return empresa;
	}

	public void setLancadoEm(DimTempo lancadoEm) {
		this.lancadoEm = lancadoEm;
	}

	public DimTempo getLancadoEm() {
		return lancadoEm;
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
		final FatoQuota other = (FatoQuota) obj;
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
		return "FatoQuota [id=" + id + ", abertura=" + abertura + ", fechamento=" + fechamento + ", volume=" + volume + "]";
	}

}
