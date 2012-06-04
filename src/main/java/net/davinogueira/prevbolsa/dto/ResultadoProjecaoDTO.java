package net.davinogueira.prevbolsa.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ResultadoProjecaoDTO implements Serializable {

	private final Date dataPrevista;
	private final Double valorPrevisto;
	private final Double grauConfianca;
	private final Double coeficienteDeterminacao;
	private final String linhaRegressao;
	private final Date dtUltimoFechamento;
	private final Double vlUltimoFechamento;
	
	public ResultadoProjecaoDTO(Date dataPrevista, Double valorPrevisto,
			Double grauConfianca, Double coeficienteDeterminacao, String linhaRegressao,
			Date dtUltimoFechamento, Double vlUltimoFechamento) {
		super();
		this.dataPrevista = dataPrevista;
		this.valorPrevisto = valorPrevisto;
		this.grauConfianca = grauConfianca;
		this.coeficienteDeterminacao = coeficienteDeterminacao;
		this.linhaRegressao = linhaRegressao;
		this.dtUltimoFechamento = dtUltimoFechamento;
		this.vlUltimoFechamento = vlUltimoFechamento;
	}

	public Double getValorPrevisto() {
		return valorPrevisto;
	}
	
	public Double getGrauConfianca() {
		return grauConfianca;
	}
	
	public Double getCoeficienteDeterminacao() {
		return coeficienteDeterminacao * 100.0;
	}

	public Date getDataPrevista() {
		return dataPrevista;
	}
	
	public String getLinhaRegressao() {
		return linhaRegressao;
	}
	
	public Date getDtUltimoFechamento() {
		return dtUltimoFechamento;
	}
	
	public Double getVlUltimoFechamento() {
		return vlUltimoFechamento;
	}
}
