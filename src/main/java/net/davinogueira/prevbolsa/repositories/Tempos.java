package net.davinogueira.prevbolsa.repositories;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import net.davinogueira.prevbolsa.models.DimTempo;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class Tempos {

	private final EntityManager entityManager;
	
	Tempos(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public DimTempo salvar(Date data){
		final String[] nomeMeses = { "Janeiro", "Fevereiro", "Março", "Abril",
				"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
				"Novembro", "Dezembro" };

		DateFormat formataId = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);

		Long id = new Long(formataId.format(cal.getTime()));
		
		DimTempo tempo = entityManager.find(DimTempo.class, id);
		
		if(tempo == null){
			tempo = new DimTempo();
			tempo.setId(id);
			
			tempo.setDataCompleta(cal.getTime());
			tempo.setNomeMes(nomeMeses[cal.get(Calendar.MONTH)]);
			tempo.setNroAno(cal.get(Calendar.YEAR));
			tempo.setDiaMes(cal.get(Calendar.DAY_OF_MONTH));
			tempo.setNroMes(cal.get(Calendar.MONTH) + 1);
			
			entityManager.persist(tempo);
		}
		
		return tempo;
	}
}
