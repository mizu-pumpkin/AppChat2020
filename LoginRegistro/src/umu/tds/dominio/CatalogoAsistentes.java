package umu.tds.dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import umu.tds.dao.DAOException;
import umu.tds.dao.FactoriaDAO;

public class CatalogoAsistentes {
	private static CatalogoAsistentes unicaInstancia;
	private FactoriaDAO factoria;

	private HashMap<Integer, Asistente> asistentesPorID;
	private HashMap<String, Asistente> asistentesPorLogin;

	public static CatalogoAsistentes getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new CatalogoAsistentes();
		return unicaInstancia;
	}

	private CatalogoAsistentes (){
		asistentesPorID = new HashMap<Integer, Asistente>();
		asistentesPorLogin = new HashMap<String, Asistente>();
		
		try {
			factoria = FactoriaDAO.getInstancia();
			
			List<Asistente> listaAsistentes = factoria.getAsistenteDAO().getAll();
			for (Asistente asistente : listaAsistentes) {
				asistentesPorID.put(asistente.getId(), asistente);
				asistentesPorLogin.put(asistente.getLogin(), asistente);
			}
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	public List<Asistente> getAsistentes() throws DAOException {
		return new LinkedList<Asistente>(asistentesPorLogin.values());
	}
	
	public Asistente getAsistente(String login) {
		return asistentesPorLogin.get(login);
	}

	public Asistente getAsistente(int id) {
		return asistentesPorID.get(id);
	}
	
	public void addAsistente(Asistente asistente) {
		asistentesPorID.put(asistente.getId(), asistente);
		asistentesPorLogin.put(asistente.getLogin(), asistente);
	}
	
	public void removeAsistente(Asistente asistente) {
		asistentesPorID.remove(asistente.getId());
		asistentesPorLogin.remove(asistente.getLogin());
	}

}
