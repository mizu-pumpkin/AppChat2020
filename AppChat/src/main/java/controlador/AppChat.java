package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.Collection;
import java.util.Date;
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
		adaptadorUsuario.create(usuario);
		catalogoUsuarios.add(usuario);
		return true;
	}
	
	public boolean delete(Usuario usuario) {
		if (!isRegistered(usuario.getUsername())) return false;
		catalogoUsuarios.remove(usuario);
		adaptadorUsuario.delete(usuario);
		return true;
	}
	
	public Usuario findUser(String data) {
		Usuario user = catalogoUsuarios.getByPhone(data);
		if (user == null) user = catalogoUsuarios.getByUsername(data);
		return user;
	}
	
	public void changeAvatar(String avatar) {
		usuarioActual.setAvatar(avatar);
		adaptadorUsuario.update(usuarioActual);
	}

	public void changeGreeting(String text) {
		usuarioActual.setGreeting(text);
		adaptadorUsuario.update(usuarioActual);
	}
	
	public void togglePremium() {
		if (usuarioActual.isPremium())
			usuarioActual.setPremiumOff();
		else
			usuarioActual.setPremiumOn();
		adaptadorUsuario.update(usuarioActual);
	}

// ---------------------------------------------------------------------
//                                                    Gestión de Mensaje
// ---------------------------------------------------------------------
	
	/*
	 * Crea el mensaje en la BD y actualiza el chat que lo contiene.
	 */
	private void registerMessage(Chat chat, Mensaje msg) {
		adaptadorMensaje.create(msg);
		adaptadorChat.update(chat);
	}
	
	/*
	 * Borra el mensaje en la BD y actualiza el chat que lo contiene.
	 */
	public void deleteMessage(Mensaje msg) {
		//NOTE aparentemente viola el patrón experto, pero voy a
		//actualizar la BD tanto para el mensaje como para el chat,
		//por lo que me interesa obtener el chat del mensaje.
		Chat chat = msg.getChat();
		chat.removeMessage(msg);
		adaptadorChat.update(chat);
		adaptadorMensaje.delete(msg);
	}
	
	public void sendMessage(Chat chat, String text) {
		Mensaje msg_sent = chat.sendMessage(usuarioActual, text);
		registerMessage(chat, msg_sent);
		
		if (chat instanceof ChatIndividual) {
			Chat chatR = getRecipient((ChatIndividual) chat);
			Mensaje msg_rcvd = chatR.sendMessage(usuarioActual, text);
			registerMessage(chatR, msg_rcvd);
		}
	}

	public void sendMessage(Chat chat, int emoticon) {
		Mensaje msg_sent = chat.sendMessage(usuarioActual, emoticon);
		registerMessage(chat, msg_sent);
		
		if (chat instanceof ChatIndividual) {
			Chat chatR = getRecipient((ChatIndividual) chat);
			Mensaje msg_rcvd = chatR.sendMessage(usuarioActual, emoticon);
			registerMessage(chatR, msg_rcvd);
		}
	}
	
	/*
	 * Devuelve el contacto que corresponde al chat que recibirá un mensaje.
	 * Cuando se envía un mensaje a un ChatIndividual, hay que asegurarse de que
	 * el ChatIndividual correspondiente que pertenece al Usuario del contacto
	 * al que se está enviando un mensaje, también recibe el mensaje.
	 */
	private Chat getRecipient(ChatIndividual chat) {
		Chat recipient = chat.getChatWith(usuarioActual);
		if (recipient.getId() == 0) registerChat(chat, chat.getUser());
		return recipient;
	}
	
	public List<Mensaje> findMessages(Chat chat, String text, String username, Date d1, Date d2) {
		List<Mensaje> aux;
		List<List<Mensaje>> listados = new LinkedList<>();
		
		if (chat == null)
			return new LinkedList<>(); 
		
		//Se está violando el patrón experto al acceder al Mensaje en el Predicate?
		//Sería más correcto usar las funciones específicas definidas en Chat?
		if (text != null && !text.trim().isEmpty())
			listados.add(chat.findMessages(m -> m.containsText(text.trim())));
		if (username != null && !username.trim().isEmpty())
			listados.add(chat.findMessages(m -> m.isSender(username.trim())));
		if (d1 != null && d2 != null)
			listados.add(chat.findMessages(m -> !m.sentBefore(d1) && !m.sentAfter(d2)));
		
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
	
	/*
	 * Crea el chat en la BD y actualiza el usuario que lo contiene.
	 */
	private void registerChat(Chat chat, Usuario user) {
		if (chat == null) return;
		if (chat.getId() == 0)
			adaptadorChat.create(chat);
		else
			adaptadorChat.update(chat);
		adaptadorUsuario.update(user);
	}
	/*
	 * Crea el chat en la BD y actualiza el usuario actual.
	 */
	private void registerChat(Chat chat) {
		if (chat != null)
			registerChat(chat, usuarioActual);
	}
	
	/*
	 * Guarda un nuevo contacto en los contactos del usuario actual,
	 * asociandole un nombre elegido por el usuario actual.
	 */
	public void saveContact(String contactName, String contactPhone) {
		Usuario user = catalogoUsuarios.getByPhone(contactPhone);
		if (user != null && !user.equals(usuarioActual))
			registerChat(usuarioActual.addContact(contactName, user));
	}
	
	/*
	 * Guarda un contacto desconocido en los contactos del usuario actual.
	 */
	public void saveContact(Usuario user) {
		saveContact(usuarioActual, user);
	}
	
	/*
	 * Guarda el contacto us2 en los contactos del us1.
	 */
	private void saveContact(Usuario us1, Usuario us2) {
		if (us2 != null && !us2.equals(us1))
			registerChat(us1.getPrivateChat(us2));
	}

	/*
	 * Edita el nombre del contacto.
	 */
	public void editContact(ChatIndividual contact, String text) {
		if (contact != null) {
			contact.setName(text);
			registerChat(contact);
		}
	}

	/*
	 * Crea un nuevo grupo, asociandole un nombre elegido por el usuario actual.
	 */
	public void createGroup(String groupName, Collection<ChatIndividual> members) {
		ChatGrupo g = usuarioActual.makeGroup(groupName);
		for (ChatIndividual m : members) {
			g.join(m);
			registerChat(g, m.getUser());
		}
		registerChat(g);
	}

	/*
	 * Edita un grupo previamente creado, cambiandole nombre y contactos miembros.
	 */
	public void editGroup(ChatGrupo g, String name, List<ChatIndividual> members) {
		List<ChatIndividual> old = g.getMembers();
		
		g.editGroup(name, members);
		
		adaptadorChat.update(g);
		for (ChatIndividual m : old)
			adaptadorUsuario.update(m.getUser());
		for (ChatIndividual m : g.getMembers())
			adaptadorUsuario.update(m.getUser());
	}
	
	/*
	 * Borra un contacto o grupo de la lista de chats del usuario actual.
	 * Si se borra un grupo del que se es admin, habrá que borrar el
	 * grupo de la lista de chats de todos los miembros. Si se borra un
	 * grupo del que se es miembro, solo se borra el proprio chat.
	 */
	public void deleteChat(Chat chat) {
		if (chat instanceof ChatGrupo) {
			ChatGrupo g = (ChatGrupo) chat;
			
			if (g.isAdmin(usuarioActual)) {
				List<ChatIndividual> old = g.getMembers();
				g.clearGroup();
				for (ChatIndividual m : old) 
					adaptadorUsuario.update(m.getUser());
			} else {
				g.removeMember(usuarioActual);
				adaptadorUsuario.update(usuarioActual);
				adaptadorChat.update(g);
				return;
			}
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
	}
	
// ---------------------------------------------------------------------
//  								Importación de mensajes de WhatsApp
// ---------------------------------------------------------------------
	
	/**
	 * Método para registrar un mensaje importado de WhatsApp a AppChat.
	 * @param chat Chat del que envía el mensaje.
	 * @param chatR Chat del que recibe el mensaje.
	 * @param sender Usuario que envía el mensaje.
	 * @param mwa Objeto que contiene el mensaje de WhatsApp.
	 * @return true en caso de haber registrado mwa en la base de datos con éxito.
	 */
	private void registerWhatsAppMessage(Chat chat, Chat chatR, Usuario sender, MensajeWhatsApp mwa) {
		Mensaje msg_sent = chat.sendWhatsAppMessage(sender, mwa);
		Mensaje msg_rcvd = chatR.sendWhatsAppMessage(sender, mwa);
		registerMessage(chat, msg_sent);
		registerMessage(chatR, msg_rcvd);
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
		
		Set<String> nombresUsuarios = mensajesWhatsapp.stream()
													  .map(m -> m.getAutor())
													  .collect(Collectors.toSet());
		// 1.1. y 1.2.
		if (nombresUsuarios.contains(usuarioActual.getUsername())
				&& nombresUsuarios.size() == 2) {
			nombresUsuarios.remove(usuarioActual.getUsername());
			String otroNombre = nombresUsuarios.iterator().next();
			Usuario otroUsuario = catalogoUsuarios.getByUsername(otroNombre);
			// 1.3.
			if (otroUsuario != null) {
				// 2.1.
				saveContact(usuarioActual, otroUsuario);
				saveContact(otroUsuario, usuarioActual);
				Chat chat = usuarioActual.getPrivateChat(otroUsuario);
				Chat chatR = otroUsuario.getPrivateChat(usuarioActual);
				// 2.2. y 2.3.
				for (MensajeWhatsApp mwa : mensajesWhatsapp) {
					if (mwa.getAutor().equals(usuarioActual.getUsername()))
						registerWhatsAppMessage(chat, chatR, usuarioActual, mwa);
					else
						registerWhatsAppMessage(chatR, chat, otroUsuario, mwa);
				}
				System.out.println("Done writting in DB.");
			}
		}
	}

// ---------------------------------------------------------------------
//		                                         				   Otras
// ---------------------------------------------------------------------
	
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

	public boolean itsMe(Usuario user) {
		return usuarioActual.equals(user);
	}

	public boolean alreadyKnown(Usuario user) {
		return usuarioActual.knowsUser(user);
	}

}
