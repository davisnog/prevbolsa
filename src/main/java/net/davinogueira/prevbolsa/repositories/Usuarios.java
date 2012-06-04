package net.davinogueira.prevbolsa.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.davinogueira.prevbolsa.models.DimEmpresa;
import net.davinogueira.prevbolsa.models.Usuario;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class Usuarios {
	
	private final EntityManager entityManager;
	
	Usuarios(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Usuario validaLogin(Usuario usuario) {
		Query query = entityManager.createNamedQuery("Usuario.validaLogin");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());
		query.setParameter("ativo", Boolean.TRUE);
		query.setMaxResults(1);
		
		Usuario u = null;
		
		try{
			u = (Usuario)query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		
		return u;
	}
	
	public void adicionarPortfolio(Usuario usuario, List<DimEmpresa> adicionar) {
		usuario.getPortfolio().addAll(adicionar);
		atualizar(usuario);
	}
	
	public void removerPortfolio(Usuario usuario, List<DimEmpresa> remover) {
		usuario.getPortfolio().removeAll(remover);
		atualizar(usuario);
	}

	public boolean novo(Usuario usuario) {
		Query query = entityManager.createNamedQuery("Usuario.existente");
		query.setParameter("login", usuario.getLogin());
		
		long retorno = (Long)query.getSingleResult();
		
		if(retorno > 0){
			return false;
		}
		
		entityManager.persist(usuario);
		return true;
	}

	public void atualizar(Usuario usuario) {
		entityManager.merge(usuario);
	}
}
