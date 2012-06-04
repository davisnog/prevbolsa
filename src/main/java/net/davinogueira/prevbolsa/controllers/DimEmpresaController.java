package net.davinogueira.prevbolsa.controllers;

import java.util.List;

import net.davinogueira.prevbolsa.infra.SessionUser;
import net.davinogueira.prevbolsa.models.DimEmpresa;
import net.davinogueira.prevbolsa.models.FatoQuota;
import net.davinogueira.prevbolsa.repositories.Empresas;
import net.davinogueira.prevbolsa.repositories.FatoQuotas;
import net.davinogueira.prevbolsa.repositories.Usuarios;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class DimEmpresaController {

	private final Result result;
	private final Empresas repository;
	private final FatoQuotas quotas;
	private final SessionUser user;
	private final Usuarios usuarios;
	
	DimEmpresaController(Result result, Empresas repository, FatoQuotas quotas, SessionUser user, Usuarios usuarios) {
		this.result = result;
		this.repository = repository;
		this.quotas = quotas;
		this.user = user;
		this.usuarios = usuarios;
	}
	
	@Post("/empresas")
	public void create(DimEmpresa dimempresa) {
		DimEmpresa empresa = repository.salvar(dimempresa);
		
		if(empresa == null){
			result.notFound();
		}else{
			user.getUsuario().getPortfolio().add(empresa);
			usuarios.atualizar(user.getUsuario());
			
			String simbolo = empresa.getSimbolo();
			
			quotas.novo(new FatoQuota(empresa));
			
			result.use(Results.json()).from(simbolo).serialize();
		}
	}
	
	@Get("/empresas/atualizar-quotas")
	public void atualizarQuotas(){
		quotas.atualizarQuotas();
		result.redirectTo(PortfolioController.class).index();
	}
	
	@Get("/empresas/autocomplete")
	public void buscarEmpresas(String term){
		List<DimEmpresa> empresas = repository.listarTodos();
		result.use(Results.json()).from(empresas).serialize();
	}
	
}
