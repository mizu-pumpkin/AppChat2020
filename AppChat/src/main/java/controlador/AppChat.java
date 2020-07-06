package controlador;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

public class AppChat {
	
// ---------------------------------------------------------------------
//		                                                      Attributes
// ---------------------------------------------------------------------
	
	private static AppChat instance;

	private IAdaptadorChatDAO adaptadorChat;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	private CatalogoUsuarios catalogoUsuarios;
	private Usuario usuarioActual;
	
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
	
	private boolean registerMessage(Chat chat, Mensaje msg) {
		adaptadorMensaje.create(msg);
		adaptadorChat.update(chat);
		return true;
	}
	
	public boolean deleteMessage(Mensaje msg) {
		Chat chat = msg.getChat();
		chat.removeMessage(msg);
		adaptadorChat.update(chat);
		adaptadorMensaje.delete(msg);
		return true;
	}
	
	public List<Mensaje> findMessages(Chat chat, String text, String user, Date d1, Date d2) {
		Set<Mensaje> mensajes = new HashSet<>();
		List<List<Mensaje>> resultados = new LinkedList<>();
		List<Mensaje> aux;
		
		if (!text.isBlank()) {
			aux = chat.findMessagesByText(text.trim());
			resultados.add(aux);
			mensajes.addAll(aux);
		}
		if (!user.isBlank()) {
			aux = chat.findMessagesByUser(user.trim());
			resultados.add(aux);
			mensajes.addAll(aux);
		}
		// TODO: igual alguna de las dos puede ser nulo para indicar "buscar de d1 en adelante"
		// o algo así.
		if (d1 != null && d2 != null) {
			aux = chat.findMessagesByDate(d1, d2);
			resultados.add(aux);
			mensajes.addAll(aux);
		}
		
		// La idea es devolver los mensajes que CUMPLAN TODOS LOS CRITERIOS.
		// Por ello hago una intersección de las listas usadas con en el conjunto
		// "mensajes" para luego devolverlo como una lista.
		for (List<Mensaje> l : resultados)
			mensajes.containsAll(l);
		
		return mensajes.stream()
					   .sorted((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()))
					   .collect(Collectors.toList())
					   ;
	}

// ---------------------------------------------------------------------
//                                         Gestión de ContactoIndividual
// ---------------------------------------------------------------------
	
	private boolean registerChat(Chat chat) {
		if (chat == null) return false;
		return registerChat(chat, usuarioActual);
	}
	
	private boolean registerChat(Chat chat, Usuario user) {
		if (chat == null) return false;
		if (chat.getId() == 0)
			adaptadorChat.create(chat);
		else
			adaptadorChat.update(chat);
		adaptadorUsuario.update(user);
		
		return true;
	}
	
	public boolean registerContact(String contactName, String contactPhone) {
		Usuario user = CatalogoUsuarios.getInstance().getByPhone(contactPhone);
		if (user == null || user.equals(usuarioActual)) return false;
		return registerChat(usuarioActual.addContact(contactName, user));
	}
	
	public boolean registerContact(Usuario user) {
		if (user == null || user.equals(usuarioActual)) return false;
		return registerChat(usuarioActual.getPrivateChat(user));
	}

	public boolean createGroup(String groupName) {
		return registerChat(usuarioActual.makeGroup(groupName));
	}

	public boolean createGroup(String groupName, Collection<ChatIndividual> contacts) {
		ChatGrupo g = usuarioActual.makeGroup(groupName);
		for (ChatIndividual c : contacts) {
			g.addMember(c);
			c.joinGroup(g);
			registerChat(g, c.getUser());
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
	
	public boolean deleteChat(Chat chat) {
		if (chat instanceof ChatGrupo) {
			ChatGrupo g = (ChatGrupo) chat;
			List<ChatIndividual> old = g.getMembers();
			
			g.clearGroup();
			
			for (ChatIndividual m : old) 
				adaptadorUsuario.update(m.getUser());
		}
		usuarioActual.removeChat(chat);
			
		adaptadorChat.delete(chat);
		adaptadorUsuario.update(usuarioActual);
		return true;
	}
	
	public boolean leaveGroup(ChatGrupo g) {
		usuarioActual.leaveGroup(g);
		g.removeMember(usuarioActual);
		
		adaptadorUsuario.update(usuarioActual);
		adaptadorChat.update(g);
		return true;
	}

}
