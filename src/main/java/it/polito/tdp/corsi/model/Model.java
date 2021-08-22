package it.polito.tdp.corsi.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	//il modello verrà chiamato dal controllore per usare il metodo del DAO per ritornare i dati al controllore
	
	private CorsoDAO corsoDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
	}
	
	
	public List<Corso> getCorsiByPeriodo(Integer pd) {
		return corsoDao.getCorsiByPeriodo(pd);
	}
	
	public Map<Corso,Integer> getIscrittiByPeriodo(Integer pd) {
		return corsoDao.getIscrittiByPeriodo(pd);
	}
}
