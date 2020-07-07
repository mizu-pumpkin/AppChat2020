package controlador;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import modelo.CatalogoUsuarios;
import modelo.Chat;
import modelo.ChatGrupo;
import modelo.ChatIndividual;
import modelo.Mensaje;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorChatDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;
import whatsapp.modelo.CargadorMensajes;
import whatsapp.modelo.MensajeWhatsApp;
import whatsapp.modelo.MensajesEvent;
import whatsapp.modelo.MensajesListener;

public class AppChat implements MensajesListener {
	
// ---------------------------------------------------------------------
//		                                                      Attributes
// ---------------------------------------------------------------------
	
	private static AppChat instance;

	private IAdaptadorChatDAO adaptadorChat;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	private CatalogoUsuarios catalogoUsuarios;
	private Usuario usuarioActual;
	private CargadorMensajes cargador;
	
// ---------------------------------------------------------------------
//		                                                    Constructors
// ---------------------------------------------------------------------
	
	public static AppChat getInstance() {
		if (instance == null)
			instance = new AppChat();
		
		return instance;
	}
	
	private AppChat() {
		FactoriaDAO factory = null;
		try {
			factory = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorChat = factory.getAdaptadorChatDAO();
		adaptadorMensaje = factory.getAdaptadorMensajeDAO();
		adaptadorUsuario = factory.getAdaptadorUsuarioDAO();
		
		catalogoUsuarios = CatalogoUsuarios.getInstance();
		usuarioActual = null;
		
		cargador = new CargadorMensajes();
		cargador.addCambioMensajesListener(this);
	}
	
// ---------------------------------------------------------------------
//                                                    Gestión de Usuario
// ---------------------------------------------------------------------
	
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
		
		catalogoUsuarios.remove(usuario);	// Borramos el Usuario del catalogo
		adaptadorUsuario.delete(usuario);	// Borramos el Usuario de la BD
		return true;
	}
	
	public Usuario findUser(String data) {
		Usuario user = catalogoUsuarios.getByPhone(data);
		if (user == null) user = catalogoUsuarios.getByUsername(data);
		return user;
	}
	
	public void changeAvatar(Usuario usuario, String avatar) {
		usuario.setAvatar(avatar);
		adaptadorUsuario.update(usuario);
	}

// ---------------------------------------------------------------------
//                                                    Gestión de Mensaje
// ---------------------------------------------------------------------
	
	public boolean sendMessage(Chat chat, String text) {
		Mensaje msg_sent = chat.sendMessage(usuarioActual, text);
		
		if (chat instanceof ChatGrupo)
			return registerMessage(chat, msg_sent);
		
		Chat chatR = getRecipient((ChatIndividual) chat);
		Mensaje msg_rcvd = chatR.sendMessage(usuarioActual, text);
		
		return registerMessage(chat, msg_sent) && registerMessage(chatR, msg_rcvd);
	}

	public boolean sendMessage(Chat chat, int emoticon) {
		Mensaje msg_sent = chat.sendMessage(usuarioActual, emoticon);
		
		if (chat instanceof ChatGrupo)
			return registerMessage(chat, msg_sent);
		
		Chat chatR = getRecipient((ChatIndividual) chat);
		Mensaje msg_rcvd = chatR.sendMessage(usuarioActual, emoticon);
		
		return registerMessage(chat, msg_sent) && registerMessage(chatR, msg_rcvd);
	}
	
	private Chat getRecipient(ChatIndividual chat) {
		Chat recipient = chat.getChatWith(usuarioActual);
		if (recipient.getId() == 0) registerChat(chat, chat.getUser()); //FIXME?
		return recipient;
	}
	/**
	 * Crea el mensaje en la BD y actualiza el chat que lo contiene.
	 */
	private boolean registerMessage(Chat chat, Mensaje msg) {
		adaptadorMensaje.create(msg);
		adaptadorChat.update(chat);
		return true;
	}
	/**
	 * Borra el mensaje en la BD y actualiza el chat que lo contiene.
	 */
	public boolean deleteMessage(Mensaje msg) {
		Chat chat = msg.getChat();
		chat.removeMessage(msg);
		adaptadorChat.update(chat);
		adaptadorMensaje.delete(msg);
		return true;
	}
	
	public List<Mensaje> findMessages(Chat chat, String text, String user, Date d1, Date d2) {
		List<Mensaje> aux;
		List<List<Mensaje>> listados = new LinkedList<>();
		
		if (!text.trim().isEmpty())
			listados.add(chat.findMessagesByText(text.trim()));
		if (!user.trim().isEmpty())
			listados.add(chat.findMessagesByUser(user.trim()));
		if (d1 != null && d2 != null)
			listados.add(chat.findMessagesByDate(d1, d2));
		
		// A fin de evitar errores por una llamada sin ningún parámetro válido.
		if (listados.isEmpty())
			return new LinkedList<>();
		
		aux = new LinkedList<>(listados.get(0));
		
		for (List<Mensaje> l : listados)
			aux.retainAll(l);
		
		return aux;
	}

// ---------------------------------------------------------------------
//                                                       Gestión de Chat
// ---------------------------------------------------------------------
	
	public boolean registerContact(String contactName, String contactPhone) {
		Usuario user = CatalogoUsuarios.getInstance().getByPhone(contactPhone);
		if (user == null || user.equals(usuarioActual)) return false;
		
		return registerChat(usuarioActual.addContact(contactName, user));
	}
	
	public boolean registerContact(Usuario user) {
		if (user == null || user.equals(usuarioActual)) return false;
		
		return registerChat(usuarioActual.getPrivateChat(user));
	}

	public boolean editContact(ChatIndividual contact, String text) {
		if (contact == null) return false;
		
		contact.setName(text);
		return registerChat(contact);
	}
	
	private boolean registerChat(Chat chat) {
		if (chat == null) return false;
		
		return registerChat(chat, usuarioActual);
	}
	/**
	 * Crea el chat en la BD y actualiza el usuario que lo contiene.
	 */
	private boolean registerChat(Chat chat, Usuario user) {
		if (chat == null) return false;
		if (chat.getId() == 0)
			adaptadorChat.create(chat);
		else
			adaptadorChat.update(chat);
		adaptadorUsuario.update(user);
		
		return true;
	}

	public boolean createGroup(String groupName, Collection<ChatIndividual> members) {
		ChatGrupo g = usuarioActual.makeGroup(groupName);
		for (ChatIndividual m : members) {
			g.join(m);
			registerChat(g, m.getUser());
		}
		return registerChat(g);
	}

	public boolean editGroup(ChatGrupo g, String name, List<ChatIndividual> members) {
		List<ChatIndividual> old = g.getMembers();
		
		g.editGroup(name, members);
		
		adaptadorChat.update(g);
		for (ChatIndividual m : old)
			adaptadorUsuario.update(m.getUser());
		for (ChatIndividual m : g.getMembers())
			adaptadorUsuario.update(m.getUser());
		
		return false;
	}
	
	public boolean leaveGroup(ChatGrupo g) {
		g.removeMember(usuarioActual);
		
		adaptadorUsuario.update(usuarioActual);
		adaptadorChat.update(g);
		return true;
	}
	
	public boolean deleteChat(Chat chat) {
		if (chat instanceof ChatGrupo) {
			ChatGrupo g = (ChatGrupo) chat;
			
			if (g.isAdmin(usuarioActual)) {
				List<ChatIndividual> old = g.getMembers();
				g.clearGroup();
				for (ChatIndividual m : old) 
					adaptadorUsuario.update(m.getUser());
			} else
				return leaveGroup(g);
		}
		usuarioActual.removeChat(chat);
		
		//Si se borra un contacto, también se ha borrado de los grupos
		//en los que aparece, por lo que hay que actualizar la BD
		if (chat instanceof ChatIndividual) {
			for (ChatGrupo g : usuarioActual.getAdminGroups())
				adaptadorChat.update(g);
			adaptadorUsuario.update(((ChatIndividual) chat).getUser());
		}
			
		adaptadorChat.delete(chat);
		adaptadorUsuario.update(usuarioActual);
		return true;
	}
	
	public void readFileChat(File fichero, int formatoFecha) {
		cargador.setFichero(fichero.getAbsolutePath(), formatoFecha);
	}
	
	@Override
	public void nuevosMensajes(MensajesEvent ev) {
		List<MensajeWhatsApp> mensajesWhatsapp = ev.getNuevoMensajes();
		for (MensajeWhatsApp mwa : mensajesWhatsapp) {
			System.out.println(mwa.getAutor() + " "+ mwa.getTexto() + " " + mwa.getFecha());
		}
	}
	
// ---------------------------------------------------------------------
//	                                                  Gestión de Usuario
// ---------------------------------------------------------------------
	
	public void togglePremium() {//FIXME
		if (usuarioActual.isPremium())
			usuarioActual.setPremiumOff();
		else
			usuarioActual.setPremiumOn();
		adaptadorUsuario.update(usuarioActual);
	}

	public void changeGreeting(String text) {
		usuarioActual.setGreeting(text);
		adaptadorUsuario.update(usuarioActual);
	}
}
