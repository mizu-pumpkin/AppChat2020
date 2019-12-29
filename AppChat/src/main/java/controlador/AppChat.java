package controlador;

import java.util.Date;

import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class AppChat {
	
	private static AppChat instance;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;

	private CatalogoUsuarios catalogoUsuarios;
	private Usuario usuarioActual;
	
	private AppChat() {
		FactoriaDAO factory = null;
		try {
			factory = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factory.getAdaptadorUsuarioDAO();
		adaptadorMensaje = factory.getAdaptadorMensajeDAO();
		adaptadorGrupo = factory.getAdaptadorGrupoDAO();
		adaptadorContactoIndividual = factory.getAdaptadorContactoIndividualDAO();
		
		catalogoUsuarios = CatalogoUsuarios.getInstance();
		usuarioActual = null;
	}
	
	public static AppChat getInstance() {
		if (instance == null)
			instance = new AppChat();
		
		return instance;
	}
	
	/* Métodos Usuario */
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public boolean isRegistered(String username) {
		return catalogoUsuarios.getByUsername(username) != null;
	}
	
	public boolean login(String username,String password) {
		Usuario usuario = catalogoUsuarios.getByUsername(username);
		
		if (usuario == null || !usuario.getPassword().equals(password)) return false;
		
		this.usuarioActual = usuario;
		
		System.out.println(usuario.getId());
		return true;
	}
	
	public boolean register(String username,
						    String password,
						    String name,
							Date birthday,
						    String email,
						    String phone,
						    String greeting) {
			if (isRegistered(username)) return false;
			
			Usuario usuario = new Usuario(username, password, name, birthday, email, phone, greeting);
			adaptadorUsuario.create(usuario);	// Almacenamos el nuevo Usuario en la BD
			catalogoUsuarios.add(usuario);		// Almacenamos el nuevo Usuario en el catalogo
			return true;
	}
	
	public boolean delete(Usuario usuario) {
		if (!isRegistered(usuario.getUsername())) return false;
		
		adaptadorUsuario.delete(usuario);	// Borramos el Usuario de la BD
		catalogoUsuarios.remove(usuario);	// Borramos el Usuario del catalogo
		return true;
	}

	/* Métodos Mensaje */
	
	public boolean sendMessage(Contacto receiver, String text, Date timestamp) {
		Mensaje msg = new Mensaje(usuarioActual, receiver, text, timestamp);
		adaptadorMensaje.create(msg);
		receiver.addMessage(msg);
		return true;
	}
	
	public boolean sendMessage(Contacto receiver, int emoticon, Date timestamp) {
		Mensaje msg = new Mensaje(usuarioActual, receiver, emoticon, timestamp);
		adaptadorMensaje.create(msg);
		receiver.addMessage(msg);
		return true;
	}
	
	public boolean deleteMessage(Mensaje msg) {
		adaptadorMensaje.delete(msg);
		msg.getReceiver().removeMessage(msg);
		return true;
	}

	/* Métodos Contacto */

	/**
	 * En cualquier momento, un usuario puede añadir contactos a su lista
	 * indicando un nombre para el contacto y su teléfono.
	 */
	public boolean registerContact(String contactName, String contactPhone) {
		ContactoIndividual contactoIndividual = new ContactoIndividual(contactName, contactPhone);
		usuarioActual.addContact(contactoIndividual);
		adaptadorContactoIndividual.create(contactoIndividual);
		return true;
	}
	
	public boolean registerContact(String groupName, Usuario groupAdmin) {
		Grupo group = new Grupo(groupName, groupAdmin);
		usuarioActual.addContact(group);
		adaptadorGrupo.create(group);
		return true;
	}
	
	public boolean deleteContact(Contacto contact) {
		if (contact instanceof ContactoIndividual)
			adaptadorContactoIndividual.delete( (ContactoIndividual) contact );
		else
			adaptadorGrupo.delete( (Grupo) contact );
		usuarioActual.removeContact(contact);
		return true;
	}
	
	public boolean registerGroup(String groupName) {
		Grupo group = new Grupo(groupName, usuarioActual);
		usuarioActual.addAdminGroup(group);
		adaptadorGrupo.create(group);
		return true;
	}
	
	public boolean deleteGroup(Grupo group) {
		adaptadorGrupo.delete(group);
		usuarioActual.removeAdminGroup(group);
		return true;
	}
}
