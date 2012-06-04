package net.davinogueira.prevbolsa.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.davinogueira.prevbolsa.dto.ChartDTO;
import net.davinogueira.prevbolsa.dto.ResultadoProjecaoDTO;
import net.davinogueira.prevbolsa.infra.Utils;
import net.davinogueira.prevbolsa.models.DimEmpresa;
import net.davinogueira.prevbolsa.models.FatoQuota;
import net.davinogueira.prevbolsa.models.Usuario;

import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.regression.SimpleRegression;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class FatoQuotas {

	private final EntityManager entityManager;
	private final Tempos tempos;
	private final Empresas empresas;
	
	FatoQuotas(EntityManager entityManager, Tempos tempos, Empresas empresas) {
		this.entityManager = entityManager;
		this.tempos = tempos;
		this.empresas = empresas;
	}
	
	public void novo(FatoQuota entity) {
		List<FatoQuota> quotas = buscarPorPeriodo(entity.getEmpresa());
		int count = 0;
		
		for(FatoQuota quota : quotas){
			entityManager.persist(quota);
			
			if (count % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
			
			++count;
		}
		
		entityManager.flush();
		entityManager.clear();
		
		empresas.salvar(entity.getEmpresa());
	}
	
	private List<FatoQuota> buscarPorPeriodo(DimEmpresa empresa){
		StringBuilder url = new StringBuilder("http://ichart.finance.yahoo.com/table.csv?s=");
		url.append(empresa.getSimbolo());
		url.append(".SA");
		
		if(empresa.getAtualizadoEm() != null){
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(empresa.getAtualizadoEm());
			
			Calendar calFim = Calendar.getInstance();
			
			url.append("&a=");
			url.append(calInicio.get(Calendar.MONTH));
			url.append("&b=");
			url.append(calInicio.get(Calendar.DAY_OF_MONTH));
			url.append("&c=");
			url.append(calInicio.get(Calendar.YEAR));
			url.append("&d=");
			url.append(calFim.get(Calendar.MONTH));
			url.append("&e=");
			url.append(calFim.get(Calendar.DAY_OF_MONTH));
			url.append("&f=");
			url.append(calFim.get(Calendar.YEAR));
			url.append("&g=d&ignore=.csv");
		}
		
		return buscarQuotas(url.toString(), empresa);
	}
	
	private List<FatoQuota> buscarQuotas(String urlStr, DimEmpresa empresa) {
		List<FatoQuota> quotas = new ArrayList<FatoQuota>();
		try {
			
		    URL u = new URL(urlStr);
		    URLConnection yc = u.openConnection();
		
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String inputLine = in.readLine();
			
			while ((inputLine = in.readLine()) != null) {
				String[] line = inputLine.split(",");
				quotas.add(criarQuota(df, line, empresa));
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return quotas;
	}
	
	private FatoQuota criarQuota(DateFormat df, String[] line, DimEmpresa empresa){
		Date lancamento = null;
		try {
			lancamento = df.parse(line[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Double abertura = new Double(line[1]);
		Double alta = new Double(line[2]);
		Double baixa = new Double(line[3]);
		Double fechamento = new Double(line[4]);
		Long volume = new Long(line[5]);

		FatoQuota quota = new FatoQuota();
		quota.setAbertura(abertura);
		quota.setAlta(alta);
		quota.setBaixa(baixa);
		quota.setFechamento(fechamento);
		quota.setVolume(volume);
		quota.setLancadoEm(tempos.salvar(lancamento));
		quota.setEmpresa(empresa);
		
		return quota;
	}

	@SuppressWarnings("unchecked")
	public List<FatoQuota> porPeriodo(DimEmpresa empresa, Date inicio, Date fim) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");

		Query query = entityManager.createNamedQuery("FatoQuota.porPeriodo");
		query.setParameter("simbolo", empresa.getSimbolo());
		query.setParameter("inicio", new Long(df.format(inicio)));
		query.setParameter("fim", new Long(df.format(fim)));

		List<FatoQuota> quotas = query.getResultList();
		
		return quotas;
	}


	public void atualizarQuotas() {
		List<DimEmpresa> result = empresas.listarTodos();
		
		for(DimEmpresa empresa : result){
			novo(new FatoQuota(empresa));
		}
	}

	public List<List<ChartDTO>> dadosGraficos(Usuario usuario, Date inicio, Date fim) {
		
		List<DimEmpresa> portfolio = empresas.portfolioDoUsuario(usuario);
		
		List<List<ChartDTO>> dados = new ArrayList<List<ChartDTO>>();
		
		for(DimEmpresa empresa : portfolio){
			List<FatoQuota> quotas = porPeriodo(empresa, inicio, fim);
			List<ChartDTO> serie = new ArrayList<ChartDTO>();
			
			for(FatoQuota q : quotas){
				final ChartDTO dto = new ChartDTO(q.getAbertura(), q.getFechamento(), q.getBaixa(), q.getAlta(), q.getVolume(), q.getLancadoEm().getId(), q.getEmpresa().getSimbolo());
				serie.add(dto);
			}
					
			if(!quotas.isEmpty()){
				dados.add(serie);
			}
		}
		
		return dados;
	}

	public Map<String, List<ResultadoProjecaoDTO>> calcularProjecao(Usuario usuario, Date inicio, Date fim, Integer dias) {
		Map<String, List<ResultadoProjecaoDTO>> projecoes = new LinkedHashMap<String, List<ResultadoProjecaoDTO>>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fim);
		
		Set<DimEmpresa> portfolio = usuario.getPortfolio();
		
		for (DimEmpresa dimEmpresa : portfolio) {
			List<FatoQuota> quotas = porPeriodo(dimEmpresa, inicio, fim);
			SimpleRegression regression = new SimpleRegression();
			double count = 0;
			
			for(FatoQuota quota : quotas){
				++count;
				regression.addData(count, quota.getFechamento());
			}
			
			FatoQuota ultQuota = quotas.get(quotas.size()-1);
			
			List<ResultadoProjecaoDTO> dtos = resultadoProjecao(dias, calendar, regression, Utils.formatDate("yyyyMMdd", ultQuota.getLancadoEm().getId()), ultQuota.getFechamento());
			projecoes.put(dimEmpresa.getSimbolo(), dtos);
			calendar.setTime(fim);
		}
		
		return projecoes;
	}

	private List<ResultadoProjecaoDTO> resultadoProjecao(Integer dias, Calendar calendar, SimpleRegression regression, Date dtUltFechamento, Double vlUltFechamento) {
		List<ResultadoProjecaoDTO> dtos = new ArrayList<ResultadoProjecaoDTO>();
		
		for (int i = 1; i <= dias; i++) {
			calendar.add(Calendar.DAY_OF_WEEK, 1);
			
			proximoDiaDeSemana(calendar);
			
			Double variancia = null;
			try {
				variancia = regression.getSlopeConfidenceInterval();
			} catch (MathException e) {
				e.printStackTrace();
			}
			
			//y = %.2f %s %.2f * %s
			String linhaRegressao = String.format("y = %s %s %s * %s", Utils.roundingDouble(regression.getIntercept()), Utils.roundingDouble(regression.getSlope()) >= 0 ? " + " : "",  Utils.roundingDouble(regression.getSlope()), regression.getN());
			
			double valorPrevisto = Utils.roundingDouble(regression.predict(regression.getN()+i));
			
			ResultadoProjecaoDTO dto = new ResultadoProjecaoDTO(calendar.getTime(),  
					valorPrevisto, variancia, regression.getRSquare(), linhaRegressao, dtUltFechamento, vlUltFechamento);
			
			dtos.add(dto);
		}
		return dtos;
	}
	
	private void proximoDiaDeSemana(Calendar calendar){
		boolean finalDeSemana = false;
		
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		
		if(dia == Calendar.SATURDAY || dia == Calendar.SUNDAY){
			calendar.add(Calendar.DAY_OF_WEEK, 1);
			finalDeSemana = true;
		}
		
		while(finalDeSemana){
			dia = calendar.get(Calendar.DAY_OF_WEEK);
			
			if(dia == Calendar.SATURDAY || dia == Calendar.SUNDAY){
				calendar.add(Calendar.DAY_OF_WEEK, 1);
				finalDeSemana = true;
			}else{
				finalDeSemana = false;
			}
		}
	}
	
}
