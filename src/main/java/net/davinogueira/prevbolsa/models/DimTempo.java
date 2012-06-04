package net.davinogueira.prevbolsa.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@javax.persistence.Entity
public class DimTempo implements Serializable {
	
	private static final long serialVersionUID = 8120853225667367423L;

	@Id
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataCompleta;
	private Integer diaMes;
	private String nomeMes;
	private Integer nroAno;
	private Integer nroMes;
	
	public DimTempo(Long lancamento) {
		this.id = lancamento;
	}

	public DimTempo(){}
	
	public Date getDataCompleta() {
		return dataCompleta;
	}

	public void setDataCompleta(Date dataCompleta) {
		this.dataCompleta = dataCompleta;
	}

	public Integer getDiaMes() {
		return diaMes;
	}

	public void setDiaMes(Integer diaMes) {
		this.diaMes = diaMes;
	}

	public String getNomeMes() {
		return nomeMes;
	}

	public void setNomeMes(String nomeMes) {
		this.nomeMes = nomeMes;
	}

	public Integer getNroAno() {
		return nroAno;
	}

	public void setNroAno(Integer nroAno) {
		this.nroAno = nroAno;
	}

	public Integer getNroMes() {
		return nroMes;
	}

	public void setNroMes(Integer nroMes) {
		this.nroMes = nroMes;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
}
