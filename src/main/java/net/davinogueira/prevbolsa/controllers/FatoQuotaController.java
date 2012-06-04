package net.davinogueira.prevbolsa.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.davinogueira.prevbolsa.dto.ResultadoProjecaoDTO;
import net.davinogueira.prevbolsa.infra.SessionUser;
import net.davinogueira.prevbolsa.repositories.FatoQuotas;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class FatoQuotaController {

	private final Result result;
	private final FatoQuotas repository;
	private final SessionUser usuario;
	
	FatoQuotaController(Result result, FatoQuotas repository, SessionUser usuario) {
		this.result = result;
		this.repository = repository;
		this.usuario = usuario;
	}
		
	@Post("/fato-quota/projetar-previsao/")
	public void projetarPrevisao(Date dtInicial, Date dtFinal, Integer dias){
		Map<String, List<ResultadoProjecaoDTO>> dtos = repository.calcularProjecao(usuario.getUsuario(), dtInicial, dtFinal, dias); 
		result.include("projecoes", dtos);
	}
	
}