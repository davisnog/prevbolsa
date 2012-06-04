package net.davinogueira.prevbolsa.dto;

import net.davinogueira.prevbolsa.infra.Utils;

public class ChartDTO {

	private final Double abertura;
	private final Double fechamento;
	private final Double baixa;
	private final Double alta;
	private final Long volume;
	private final String lancadoEm;	
	private final String simbolo;

	public ChartDTO(Double abertura, Double fechamento, Double baixa,
			Double alta, Long volume, Long lancadoEm, String simbolo) {
		super();
		this.abertura = abertura;
		this.fechamento = fechamento;
		this.baixa = baixa;
		this.alta = alta;
		this.volume = volume;
		this.lancadoEm = Utils.formatDate("MM/dd/yyyy", Utils.formatDate("yyyyMMdd", lancadoEm.toString()));
		this.simbolo = simbolo;
	}

	public final Double getAbertura() {
		if(abertura != null){
			return Utils.roundingDouble(abertura);
		}
		return abertura;
	}

	public final Double getFechamento() {
		if(fechamento != null){
			return Utils.roundingDouble(fechamento);
		}
		return fechamento;
	}

	public final Double getBaixa() {
		if(baixa != null){
			return Utils.roundingDouble(baixa);
		}
		return baixa;
	}

	public final Double getAlta() {
		if(alta != null){
			return Utils.roundingDouble(alta);
		}
		return alta;
	}

	public final Long getVolume() {
		return volume;
	}

	public String getLancadoEm() {
		return lancadoEm;
	}

	public final String getSimbolo() {
		return simbolo;
	}
	
}
