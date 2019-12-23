package umu.tds.controlador;

import umu.tds.dao.AsistenteDAO;
import umu.tds.dao.DAOException;
import umu.tds.dao.FactoriaDAO;
import umu.tds.dominio.Asistente;
import umu.tds.dominio.CatalogoAsistentes;

public final class ControladorAsistentes {
		
	private Asistente asistenteActual;
	private static ControladorAsistentes unicaInstancia;
	private FactoriaDAO factoria;
	
	private ControladorAsistentes() {
		asistenteActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}	
	}
	
	public static ControladorAsistentes getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new ControladorAsistentes();
		return unicaInstancia;
	}
	
	public Asistente getAsistenteActual() {
		return asistenteActual;
	}
	
	public boolean esAsistenteRegistrado(String login) {
		return CatalogoAsistentes.getUnicaInstancia().getAsistente(login)!=null;
	}
	
	public boolean loginAsistente(String nombre,String password) {
		Asistente asistente = CatalogoAsistentes.getUnicaInstancia().getAsistente(nombre);
		if (asistente != null && asistente.getPassword().equals(password)) {
				this.asistenteActual = asistente;
				return true;
		}
		return false;
	}
	
	public boolean registrarAsistente(String nombre,
									String apellidos, 
									String dni,
									int edad,
									String movil,
									String email,
									String login,
									String password) {

			if (esAsistenteRegistrado(login)) return false;
			Asistente asistente = new Asistente(nombre,apellidos,dni,edad,movil, email,login,password);
			
			AsistenteDAO asistenteDAO = factoria.getAsistenteDAO(); /*Adaptador DAO para almacenar el nuevo Asistente en la BD*/
			asistenteDAO.create(asistente);
			
			CatalogoAsistentes.getUnicaInstancia().addAsistente(asistente);
			return true;
	}
	
	public boolean borrarAsistente(Asistente asistente) {
		if (!esAsistenteRegistrado(asistente.getLogin())) return false;
		
		AsistenteDAO asistenteDAO = factoria.getAsistenteDAO();  /*Adaptador DAO para borrar el Asistente de la BD*/
		asistenteDAO.delete(asistente);
		
		CatalogoAsistentes.getUnicaInstancia().removeAsistente(asistente);
		return true;
	}
	
}
	