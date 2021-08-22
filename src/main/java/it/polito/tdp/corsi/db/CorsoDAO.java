package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;

public class CorsoDAO {
	
	//l'idea generale è di avere una classe DAO per ogni tabella da cui ci interessa estrarre i dati
	
	
	//ho bisogno di un elenco di corsi, mi aspetto che mi venga ritornato una lista di oggetti Corso
	public List<Corso> getCorsiByPeriodo(Integer periodo) {
		
		//il pattern di un metodo DAO è sempre lo stesso
		
		//query
			String sql=" SELECT * "+
				"FROM corso "+
				"WHERE pd = ?";
			
		//struttura dati in cui salvero la lista e che ritornerò al chiamante
			List<Corso> result = new ArrayList<Corso>();
			
			try {
				//inserisco il codice per accedere al database
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				//imposto il parametro
				st.setInt(1, periodo);  //il parametro 1 della query, se ce ne fossero di piu 
				                        //scriverei anche st.setDipende(2, dipende);
				ResultSet rs = st.executeQuery();
				
				//scorro il resultSet per riempire la lista di corsi e ritornarla al chiamante
				while(rs.next()) {
					Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
					result.add(c);
				}
				st.close();
				rs.close();
				conn.close();
			}catch (SQLException e) {
				throw new RuntimeException (e);
			} 
			return result;
			
	}
	//il programma e il database si scambieranno una mappa con chiave Corso e con valore il numero di iscritti
	public Map<Corso,Integer> getIscrittiByPeriodo(Integer periodo) {
		
		//il pattern di un metodo DAO è sempre lo stesso
		
				//query
					String sql="SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot "+
							"FROM corso c, iscrizione i "+
							"WHERE c.codins=i.codins AND c.pd =? "+
							"GROUP BY c.codins, c.nome, c.crediti, c.pd "+
							"ORDER BY tot DESC";
					
				//struttura dati in cui salvero la lista e che ritornerò al chiamante
					Map<Corso,Integer> result = new HashMap<Corso,Integer>();
					
					try {
						//inserisco il codice per accedere al database
						Connection conn = DBConnect.getConnection();
						PreparedStatement st = conn.prepareStatement(sql);
						//imposto il parametro
						st.setInt(1, periodo);  //il parametro 1 della query, se ce ne fossero di piu 
						                        //scriverei anche st.setDipende(2, dipende);
						ResultSet rs = st.executeQuery();
						
						//scorro il resultSet per riempire la lista di corsi e ritornarla al chiamante
						while(rs.next()) {
							Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
							Integer n = rs.getInt("tot");
							result.put(c,n);
						}
						st.close();
						rs.close();
						conn.close();
					}catch (SQLException e) {
						throw new RuntimeException (e);
					} 
					return result;
		
	}


}
