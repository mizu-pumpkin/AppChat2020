package controlador;

import java.util.Collection;
import java.util.Date;

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
	}

// ---------------------------------------------------------------------
//                                                    Gestión de Mensaje
// ---------------------------------------------------------------------
	
	public boolean sendMessage(Chat chat, String text) {
		Mensaje msg_sent = chat.sendMessage(usuarioActual, text);
		
		if (chat instanceof ChatGrupo)
			return registerMessage(chat, msg_sent);
		
		Chat chatR = getRecipient(chat);
		Mensaje msg_rcvd = chatR.sendMessage(usuarioActual, text);
		
		return registerMessage(chat, msg_sent) && registerMessage(chatR, msg_rcvd);
	}

	public boolean sendMessage(Chat chat, int emoticon) {
		Mensaje msg_sent = chat.sendMessage(usuarioActual, emoticon);
		
		if (chat instanceof ChatGrupo)
			return registerMessage(chat, msg_sent);
		
		Chat chatR = getRecipient(chat);
		Mensaje msg_rcvd = chatR.sendMessage(usuarioActual, emoticon);
		
		return registerMessage(chat, msg_sent) && registerMessage(chatR, msg_rcvd);
	}
	
	private Chat getRecipient(Chat chat) {
		Usuario user = chat.getOwner();
		Chat recipient = user.getPrivateChat(usuarioActual);
		if (recipient.getId() == 0) registerChat(chat, user); //FIXME
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

// ---------------------------------------------------------------------
//                                         Gestión de ContactoIndividual
// ---------------------------------------------------------------------
	
	private boolean registerChat(Chat chat) {
		return registerChat(chat, usuarioActual);
	}
	
	private boolean registerChat(Chat chat, Usuario user) {
		if (chat == null) return false;
		adaptadorChat.create(chat);
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
		return registerChat(usuarioActual.addContact(user));
	}

	public boolean createGroup(String groupName) {
		return registerChat(usuarioActual.makeGroup(groupName));
	}

	public boolean createGroup(String groupName, Collection<ChatIndividual> contacts) {
		ChatGrupo grupo = usuarioActual.makeGroup(groupName);
		for (ChatIndividual c : contacts) {
			grupo.addMember(c);
			c.getOwner().joinGroup(grupo);
			registerChat(grupo, c.getOwner());
		}
		return registerChat(grupo);
	}

	public boolean joinGroup(ChatGrupo group) {
		usuarioActual.joinGroup(group);
		return registerChat(group);
	}
	
	public boolean deleteChat(Chat chat) {
		if (chat instanceof ChatGrupo)
			((ChatGrupo) chat).clearGroup();
		else
			usuarioActual.removeChat(chat);
			
		adaptadorUsuario.update(usuarioActual);
		adaptadorChat.delete(chat);
		return true;
	}
	
	public boolean leaveGroup(ChatGrupo group) {
		usuarioActual.removeChat(group);
		group.removeMember(usuarioActual);
		
		adaptadorUsuario.update(usuarioActual);
		adaptadorChat.update(group);
		return true;
	}

}
