package modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {
	
// ---------------------------------------------------------------------
//		                                                      Attributes
// ---------------------------------------------------------------------
			
	private static CatalogoUsuarios instance;
	private FactoriaDAO factory;
	private IAdaptadorUsuarioDAO adaptadorUsuarios;

	private HashMap<String, Usuario> usersByUsername;
	private HashMap<String, Usuario> usersByPhone;
	
// ---------------------------------------------------------------------
//		                                                    Constructors
// ---------------------------------------------------------------------
		
	public static CatalogoUsuarios getInstance() {
		if (instance == null)
			instance = new CatalogoUsuarios();
		return instance;
	}

	private CatalogoUsuarios (){
		usersByUsername = new HashMap<String, Usuario>();
		usersByPhone = new HashMap<String, Usuario>();
		try {
			factory = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
			adaptadorUsuarios = factory.getAdaptadorUsuarioDAO();
			cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	// Recupera todos los usuarios para trabajar con ellos en memoria
	private void cargarCatalogo() throws DAOException {
		List<Usuario> listaUsuarios = adaptadorUsuarios.readAll();
		for (Usuario usuario : listaUsuarios) {
			usersByUsername.put(usuario.getUsername(), usuario);
			usersByPhone.put(usuario.getPhone(), usuario);
		}
	}
	
// ---------------------------------------------------------------------
//	                                                 Getters and Setters
// ---------------------------------------------------------------------
			
	public List<Usuario> getAll() throws DAOException {
		return new LinkedList<Usuario>(usersByUsername.values());
	}
	
	public Usuario getByUsername(String username) {
		return usersByUsername.get(username);
	}
	
	public Usuario getByPhone(String phone) {
		return usersByPhone.get(phone);
	}
	
	public void add(Usuario usuario) {
		usersByUsername.put(usuario.getUsername(), usuario);
		usersByPhone.put(usuario.getPhone(), usuario);
	}
	
	public void remove(Usuario usuario) {
		usersByUsername.remove(usuario.getUsername());
		usersByPhone.remove(usuario.getPhone());
	}
}
