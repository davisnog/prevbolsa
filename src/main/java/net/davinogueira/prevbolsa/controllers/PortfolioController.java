package net.davinogueira.prevbolsa.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import net.davinogueira.prevbolsa.dto.ChartDTO;
import net.davinogueira.prevbolsa.infra.SessionUser;
import net.davinogueira.prevbolsa.models.DimEmpresa;
import net.davinogueira.prevbolsa.models.Usuario;
import net.davinogueira.prevbolsa.repositories.Empresas;
import net.davinogueira.prevbolsa.repositories.FatoQuotas;
import net.davinogueira.prevbolsa.repositories.Usuarios;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class PortfolioController {

	private final Result result;
	private final Usuarios usuarios;
	private final Empresas empresas;
	private final SessionUser user;
	private final FatoQuotas repository;
	
	public PortfolioController(Result result, Usuarios usuarios, Empresas empresas, SessionUser user, FatoQuotas repository) {
		this.result = result;
		this.usuarios = usuarios;
		this.empresas = empresas;
		this.user = user;
		this.repository = repository;
	}
	
	@Get("/portfolio")
	public Usuario index(){
		return user.getUsuario();
	}
	
	@Get("/portfolio/listar-empresas/")
	public Set<DimEmpresa> listarEmpresas(){
		return empresas.buscarForaPortfolio();
	}
	
	@Get("/portfolio/listar-portfolio/")
	public Set<DimEmpresa> listarPortfolio(){
		return user.getUsuario().getPortfolio();
	}
	
	@Post("/portfolio/quotas/")
	public void quotas(Date inicio, Date fim){
		List<List<ChartDTO>>  dados = repository.dadosGraficos(user.getUsuario(), inicio, fim);
		result.use(Results.json()).from(dados).serialize();
	}
	
	@Post("/portfolio/adicionar-portfolio/{adicionar}")
	public void adicionarAoPortfolio(String adicionar){
		String[] addSimbolos = adicionar.split(",");
		List<DimEmpresa> add = empresas.listarPorSimbolos(addSimbolos);
				
		usuarios.adicionarPortfolio(user.getUsuario(), add);
		result.nothing();
	}
	
	@Post("/portfolio/remover-portfolio/{remover}")
	public void removerDoPortfolio(String remover){
		String[] remSimbolos = remover.split(",");
		List<DimEmpresa> rem = empresas.listarPorSimbolos(remSimbolos);
		
		usuarios.removerPortfolio(user.getUsuario(), rem);
		result.nothing();
	}
	
}
