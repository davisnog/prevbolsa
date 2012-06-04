package net.davinogueira.prevbolsa.controllers;

import net.davinogueira.prevbolsa.infra.Public;
import net.davinogueira.prevbolsa.infra.SessionUser;
import net.davinogueira.prevbolsa.models.Usuario;
import net.davinogueira.prevbolsa.repositories.Usuarios;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class UsuarioController {

	private final Result result;
	private final Usuarios usuarios;
	private final SessionUser usuarioLogado;
	
	UsuarioController(Result result, Usuarios usuarios, SessionUser usuarioLogado) {
		this.result = result;
		this.usuarios = usuarios;
		this.usuarioLogado = usuarioLogado;
	}
	
	@Public @Post("/usuarios/valida-login/")
	public void validaLogin(Usuario usuario){
		Usuario loginValido = usuarios.validaLogin(usuario);
		
		if(loginValido != null){
			usuarioLogado.setUsuario(loginValido);
			result.redirectTo(PortfolioController.class).index();
		}else{
			result.include("msgUsuarioSenha", "Usuário ou senha invalidos!");
			result.redirectTo(this).index();
		}
	}
	
	@Public @Get("/")
	public void index() {}
	
	@Public @Post("/usuarios")
	public void criar(Usuario usuario) {
		boolean criou = usuarios.novo(usuario);
		if(criou){
			result.redirectTo(this).index();
		}else{
			result.include("msgUsuarioExistente", String.format("Usuário com login %s já foi cadastrado", usuario.getLogin()));
			result.redirectTo(this).novo();
		}
	}
	
	@Public @Get("/usuarios/novo")
	public void novo() {}
	
	@Get("/usuarios/sair/")
	public void sair(){
		this.usuarioLogado.setUsuario(null);
		this.result.redirectTo(this).index();
	}
}