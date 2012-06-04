package net.davinogueira.prevbolsa.repositories;

import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.davinogueira.prevbolsa.infra.SessionUser;
import net.davinogueira.prevbolsa.models.DimEmpresa;
import net.davinogueira.prevbolsa.models.Usuario;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class Empresas {

	private final EntityManager entityManager;
	private final SessionUser user;

	Empresas(EntityManager entityManager, SessionUser user) {
		this.entityManager = entityManager;
		this.user = user;
	}

	public DimEmpresa salvar(DimEmpresa entity) {
		String simbolo = entity.getSimbolo().toUpperCase();

		if (simbolo.endsWith(".SA")) {
			simbolo = simbolo.replace(".SA", "");
		}
		
		if(empresaExiste(simbolo)){		
			DimEmpresa dim = entityManager.find(DimEmpresa.class, simbolo);
		
			if (dim == null) {
				dim = new DimEmpresa();
				dim.setSimbolo(simbolo);
				dim.setCadastradoEm(new Date());
				dim.setNome(buscarNomeEmpresa(simbolo));
	
				entityManager.persist(dim);
			} else {
				dim.setAtualizadoEm(new Date());
				entityManager.merge(dim);
			}
			
			return dim;
		}
		return null;
	}
	
	private boolean empresaExiste(String simbolo){
		try {
			
			String url = String.format("http://ichart.finance.yahoo.com/table.csv?s=%s.SA", simbolo);
/*			URL url = new URL(String.format("http://ichart.finance.yahoo.com/table.csv?s=%s.SA", simbolo));
			URLConnection yc = url.openConnection();
*/
			System.setProperty("http.proxyHost","proxybnu.sociesc.com.br") ;
		    System.setProperty("http.proxyPort", "3128") ;
		 
		    Authenticator.setDefault(new Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new
		           PasswordAuthentication("sociesc\\bnutec","ibes@2011".toCharArray());
		    }});
		 
		    URL u = new URL(url);
		    URLConnection yc = u.openConnection();
		
			new Scanner(yc.getInputStream());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private String buscarNomeEmpresa(String simbolo) {
		String nome = null;
		
		try {
			String urlStr = "http://finance.yahoo.com/d/quotes.csv?s=" + simbolo + ".SA&f=n";
			/*URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + simbolo + ".SA&f=n");
			URLConnection yc = url.openConnection();
			*/
			System.setProperty("http.proxyHost","proxybnu.sociesc.com.br") ;
		    System.setProperty("http.proxyPort", "3128") ;
		 
		    Authenticator.setDefault(new Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new
		           PasswordAuthentication("sociesc\\bnutec","ibes@2011".toCharArray());
		    }});
		 
		    URL u = new URL(urlStr);
		    URLConnection yc = u.openConnection();
			
			Scanner scanner = new Scanner(yc.getInputStream());

			while (scanner.hasNextLine()) {
				nome = scanner.nextLine();
				nome = nome.replaceAll("\\s", "").replace("\"", "").replace("*", "");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nome;
	}

	@SuppressWarnings("unchecked")
	public Set<DimEmpresa> buscarForaPortfolio() {
		List<DimEmpresa> empresas = entityManager.createNamedQuery("DimEmpresa.buscarForaPortfolio").getResultList();

		Set<DimEmpresa> retorno = new LinkedHashSet<DimEmpresa>();

		for (DimEmpresa empresa : empresas) {
			Set<Usuario> usuarios = empresa.getPortfolio();

			if (usuarios == null || usuarios.size() == 0) {
				retorno.add(empresa);
			}

			for (Usuario usuario : usuarios) {
				if (!(user.getUsuario().getId().equals(usuario.getId()))) {
					retorno.add(empresa);
				}
			}
		}

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<DimEmpresa> listarPorSimbolos(String[] simbolos) {
		Query query = entityManager.createNamedQuery("DimEmpresa.listarPorSimbolos");

		query.setParameter("simbolos", Arrays.asList(simbolos));
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<DimEmpresa> portfolioDoUsuario(Usuario usuario) {
		Query query = entityManager.createNamedQuery("DimEmpresa.portfolioDoUsuario");
		query.setParameter("usuario", usuario.getId());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<DimEmpresa> listarTodos() {
		return entityManager.createNamedQuery("DimEmpresa.listarTodos").getResultList();
	}
}
