package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
	
	public boolean isPhoneRegistered(String phone) {
		return catalogoUsuarios.getByPhone(phone) != null;
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
			if (isRegistered(username) || isPhoneRegistered(phone)) return false;
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
	
	/**
	 * Método para registrar un mensaje importado de WhatsApp a AppChat.
	 * @param chat Chat del que envía el mensaje.
	 * @param chatR Chat del que recibe el mensaje.
	 * @param sender Usuario que envía el mensaje.
	 * @param mwa Objeto que contiene el mensaje de WhatsApp.
	 * @return true en caso de haber registrado mwa en la base de datos con éxito.
	 */
	private boolean registerWhatsAppMessage(Chat chat, Chat chatR, Usuario sender, MensajeWhatsApp mwa) {
		Mensaje msg_sent = chat.registerWhatsAppMessage(sender, mwa);
		Mensaje msg_rcvd = chatR.registerWhatsAppMessage(sender, mwa);
		return registerMessage(chat, msg_sent) && registerMessage(chatR, msg_rcvd);
	}
	
	private Chat getRecipient(ChatIndividual chat) {
		Chat recipient = chat.getChatWith(usuarioActual);
		if (recipient.getId() == 0) registerChat(chat, chat.getUser());
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
	
	public List<Mensaje> findMessages(Chat chat, String text, String username, Date d1, Date d2) {
		List<Mensaje> aux;
		List<List<Mensaje>> listados = new LinkedList<>();
		
		if (chat == null)
			return new LinkedList<>(); 
		
		if (text != null && !text.trim().isEmpty())
			listados.add(chat.findMessages(m -> m.getBody().contains(text.trim())));
		if (username != null && !username.trim().isEmpty())
			listados.add(chat.findMessages(m -> m.isSender(username.trim())));
		if (d1 != null && d2 != null)
			listados.add(chat.findMessages(m -> !m.getTimestamp().before(d1) && !m.getTimestamp().after(d2)));
		
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
	
	/**
	 * Manda al cargador de mensajes a leer un fichero indicándole el formato del mismo.
	 * @param fichero Objeto que contiene el fichero de texto con el chat.
	 * @param formatoFecha Es un entero que indica el formato de los mensajes
	 * almacenados en el objeto fichero. Es un valor de 0 a 2 siendo:
	 * 	0 = IOS
	 * 	1 = ANDROID 1
	 * 	2 = ANDROID 2
	 */
	public void readFileChat(File fichero, int formatoFecha) {
		cargador.setFichero(fichero.getAbsolutePath(), formatoFecha);
	}
	
	@Override
	public void nuevosMensajes(MensajesEvent ev) {
		List<MensajeWhatsApp> mensajesWhatsapp = ev.getNuevoMensajes();

		// 1. Corrección del fichero.
		//	1.1. Debe haber solo dos usuarios (es para chat privados).
		//	1.2. El usuario actual debe ser uno de esos usuarios.
		//	1.3. El otro usuario debe existir en la BBDD.
		// 2. Proceder a insertar los mensajes al chat correspondiente.
		//	2.1. Encontrar los chat entre el usuario actual y el otro usuario.
		//	2.2. Delegar la transformación de objetos MensajeWhatsApp a Mensaje
		//		 en manos del chat.
		//	2.3. Registrar en la BBDD los cambios realizados.
		
		List<String> nombresUsuarios = mensajesWhatsapp.stream()
													   .map(m -> m.getAutor())
													   .collect(Collectors.toList());
		Set<String> nombresUsuariosNoRepetidos = new HashSet<>(nombresUsuarios);
		// 1.1. y 1.2.
		if (nombresUsuariosNoRepetidos.contains(usuarioActual.getUsername())
				&& nombresUsuariosNoRepetidos.size() == 2) {
			nombresUsuarios.remove(usuarioActual.getUsername());
			Usuario otroUsuario = catalogoUsuarios.getByUsername(nombresUsuarios.get(0));
			// 1.3.
			if (otroUsuario != null) {
				// 2.1.
				registerContact(otroUsuario);
				Chat chat = usuarioActual.getPrivateChat(otroUsuario);
				Chat chatR = otroUsuario.getPrivateChat(usuarioActual);
				// 2.2. y 2.3.
				for (MensajeWhatsApp mwa : mensajesWhatsapp) {
					if (mwa.getAutor().equals(usuarioActual.getUsername()))
						registerWhatsAppMessage(chat, chatR, usuarioActual, mwa);
					else
						registerWhatsAppMessage(chatR, chat, otroUsuario, mwa);
				}
				System.out.println("Donde writting in DB.");
			}
		}
	}
	
// ---------------------------------------------------------------------
//	                                                  Gestión de Usuario
// ---------------------------------------------------------------------
	
	public void togglePremium() {
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
	
	public boolean isYoung(Usuario user) {
		try {//TODO cambiar para que sea menores de 20 dinámico
			Date limiteJoven = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2000");
			return user.getBirthday().after(limiteJoven);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isSummer() {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("M/yyyy");
		try {
			String year = new SimpleDateFormat("yyyy").format(today);
			if (today.after(format.parse("7/"+year)) &&	//desde 1 de julio
				today.before(format.parse("9/"+year)))	//hasta 31 de agosto
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

}
