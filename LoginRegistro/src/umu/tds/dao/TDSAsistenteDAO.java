package umu.tds.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Asistente;
import beans.Entidad;
import beans.Propiedad;

/**
 * 
 * Clase que implementa el Adaptador DAO concreto de Asistente para el tipo H2.
 * 
 */

public final class TDSAsistenteDAO implements AsistenteDAO {
	
	private ServicioPersistencia servPersistencia;
	
	public TDSAsistenteDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private Asistente entidadToAsistente(Entidad eAsistente) {
		
		String nombre = servPersistencia.recuperarPropiedadEntidad(eAsistente, "nombre");
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eAsistente, "apellidos");
		String dni = servPersistencia.recuperarPropiedadEntidad(eAsistente, "dni");
		String edadP = servPersistencia.recuperarPropiedadEntidad(eAsistente, "edad");
		String movil = servPersistencia.recuperarPropiedadEntidad(eAsistente, "movil");
		String email = servPersistencia.recuperarPropiedadEntidad(eAsistente, "email");
		String login = servPersistencia.recuperarPropiedadEntidad(eAsistente, "login");
		String password = servPersistencia.recuperarPropiedadEntidad(eAsistente, "password");
		
		int edad = Integer.parseInt(edadP);
		Asistente asistente = new Asistente(nombre,apellidos,dni,edad,movil,email,login,password);
		asistente.setId(eAsistente.getId());
		return asistente;
	}
	
	private Entidad asistenteToEntidad(Asistente asistente) {
		Entidad  eAsistente = new Entidad();
		eAsistente.setNombre("Asistente"); 
	
		eAsistente.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", asistente.getNombre()), 
						new Propiedad("apellidos", asistente.getApellidos()),
						new Propiedad("dni", asistente.getDni()),
						new Propiedad("edad", Integer.toString(asistente.getEdad())),
						new Propiedad("movil", asistente.getMovil()),
						new Propiedad("email", asistente.getEmail()),
						new Propiedad("login", asistente.getLogin()),
						new Propiedad("password", asistente.getPassword())
						))
				);
		return eAsistente;
	}
	
	public void create(Asistente asistente) {
		Entidad  eAsistente = this.asistenteToEntidad(asistente);
		eAsistente = servPersistencia.registrarEntidad(eAsistente);
		asistente.setId(eAsistente.getId());
	}
	
	public boolean delete(Asistente asistente) {
		Entidad eAsistente;
		eAsistente = servPersistencia.recuperarEntidad(asistente.getId());
		return servPersistencia.borrarEntidad(eAsistente);
	}
	
	/**
	 * Permite que un Asistente modifique su perfil: email, password y movil
	 */
	public void updatePerfil(Asistente asistente ) {
		Entidad eAsistente = servPersistencia.recuperarEntidad(asistente.getId());
		servPersistencia.eliminarPropiedadEntidad(eAsistente, "password");
		servPersistencia.anadirPropiedadEntidad(eAsistente, "password",asistente.getPassword());
		servPersistencia.eliminarPropiedadEntidad(eAsistente, "email");
		servPersistencia.anadirPropiedadEntidad(eAsistente, "email",asistente.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eAsistente, "movil");
		servPersistencia.anadirPropiedadEntidad(eAsistente, "movil",asistente.getPassword());
	}
	
	public Asistente get(int id) {
		Entidad eAsistente = servPersistencia.recuperarEntidad(id);
		return entidadToAsistente(eAsistente);
	}
	
	public List<Asistente> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Asistente");
		
		List<Asistente> asistentes  = new LinkedList<Asistente>();
		for (Entidad eAsistente : entidades) {
			asistentes.add(get(eAsistente.getId()));
		}
		return asistentes;
	}
	
	
}
