package net.davinogueira.prevbolsa.infra;

import java.io.Serializable;

import net.davinogueira.prevbolsa.models.Usuario;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class SessionUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	public void setUsuario(final Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
