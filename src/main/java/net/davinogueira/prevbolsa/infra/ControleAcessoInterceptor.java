package net.davinogueira.prevbolsa.infra;

import net.davinogueira.prevbolsa.controllers.UsuarioController;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;

@RequestScoped
@Intercepts
public class ControleAcessoInterceptor implements Interceptor {

	private final Result result;
	private final SessionUser usuario;
	
	public ControleAcessoInterceptor(Result result, SessionUser usuario){
		this.result = result;
		this.usuario = usuario;
	}
	
	public boolean accepts(ResourceMethod method) {
		return !method.containsAnnotation(Public.class);
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object instance) throws InterceptionException {
		if(usuario.getUsuario() != null){
			stack.next(method, instance);
		}else{
			result.redirectTo(UsuarioController.class).index();
		}
	}

}
