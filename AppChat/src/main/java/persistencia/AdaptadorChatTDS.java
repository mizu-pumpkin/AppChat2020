package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Chat;
import modelo.ChatIndividual;
import modelo.ChatGrupo;
import modelo.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorChatTDS implements IAdaptadorChatDAO {
	
	private static final String ENTITY_NAME = "chat";
	private static final String PROPERTY_CHAT_TYPE = "type";
	private static final String TYPE_INDIVIDUAL = "individual";
	private static final String TYPE_GROUP = "group";
	private static final String PROPERTY_CHAT_NAME = "name";
	private static final String PROPERTY_CHAT_OWNER = "owner";
	private static final String PROPERTY_MESSAGES = "messages";
	private static final String PROPERTY_GROUP_MEMBERS = "members";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorChatTDS instance = null;
	
	public static AdaptadorChatTDS getInstance() {
		if (instance == null)
			instance = new AdaptadorChatTDS();

		return instance;
	}
	
	private AdaptadorChatTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void create(Chat chat) {
		Entidad eChat;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eChat = servPersistencia.recuperarEntidad(chat.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		registrarObjetos(chat);
		
		// Crear entidad
		String type;
		String memberIDs;
		if (chat instanceof ChatGrupo) {
			type = TYPE_GROUP;
			memberIDs = getMembersIDs(((ChatGrupo) chat).getMembers());
		} else {
			type = TYPE_INDIVIDUAL;
			memberIDs = "";
		}
		eChat = new Entidad();
		eChat.setNombre(ENTITY_NAME);
		eChat.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
			new Propiedad(PROPERTY_CHAT_TYPE, type),
			new Propiedad(PROPERTY_CHAT_NAME, chat.getName()),
			new Propiedad(PROPERTY_CHAT_OWNER, String.valueOf(chat.getOwner().getId())),
			new Propiedad(PROPERTY_MESSAGES, getMessagesIDs(chat.getMessages())),
			new Propiedad(PROPERTY_GROUP_MEMBERS, memberIDs)
		)));
		
		// Registrar entidad
		eChat = servPersistencia.registrarEntidad(eChat);
		
		// Asignar el identificador único que genera el servicio de persistencia
		chat.setId(eChat.getId());
	}

	@Override
	public void update(Chat chat) {
		if (chat.getId() == 0) return;
		Entidad eChat = servPersistencia.recuperarEntidad(chat.getId());

		servPersistencia.eliminarPropiedadEntidad(eChat, PROPERTY_CHAT_NAME);
		servPersistencia.eliminarPropiedadEntidad(eChat, PROPERTY_MESSAGES);
		//servPersistencia.eliminarPropiedadEntidad(eChat, PROPERTY_CHAT_OWNER);
		
		// ???:FIXME: debería volver a registrar las propiedades que son objetos?
		registrarObjetos(chat);
		
		servPersistencia.anadirPropiedadEntidad(eChat, PROPERTY_CHAT_NAME, chat.getName());
		//servPersistencia.anadirPropiedadEntidad(eChat, PROPERTY_CHAT_OWNER, String.valueOf(chat.getOwner().getId()));
		servPersistencia.anadirPropiedadEntidad(eChat, PROPERTY_MESSAGES, getMessagesIDs(chat.getMessages()));
		if (chat instanceof ChatGrupo) {
			servPersistencia.eliminarPropiedadEntidad(eChat, PROPERTY_GROUP_MEMBERS);
			servPersistencia.anadirPropiedadEntidad(eChat, PROPERTY_GROUP_MEMBERS, getMembersIDs(((ChatGrupo) chat).getMembers()));
		}
		
		PoolDAO.getInstance().addObject(chat.getId(), chat);
	}

	@Override
	public void delete(Chat chat) {
		// No se tienen en cuenta restricciones de integridad
		servPersistencia.borrarEntidad(servPersistencia.recuperarEntidad(chat.getId()));
	}

	@Override
	public Chat read(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (Chat) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eChat = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		String type = servPersistencia.recuperarPropiedadEntidad(eChat, PROPERTY_CHAT_TYPE);
		String name = servPersistencia.recuperarPropiedadEntidad(eChat, PROPERTY_CHAT_NAME);
		int idOwner = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eChat, PROPERTY_CHAT_OWNER));
		String idsMessages = servPersistencia.recuperarPropiedadEntidad(eChat, PROPERTY_MESSAGES);
		String idsMembers = servPersistencia.recuperarPropiedadEntidad(eChat, PROPERTY_GROUP_MEMBERS);
		
		// Crear el objeto
		Chat chat;
		if (type.equals(TYPE_GROUP)) chat = new ChatGrupo(name);
		else chat = new ChatIndividual(name);
		chat.setId(id);
		
		// Añadir el objeto al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, chat);
		
		// Recuperar propiedades que son objetos llamando a adaptadores
		chat.setOwner(AdaptadorUsuarioTDS.getInstance().read(idOwner));
		for (Mensaje m : getMessagesFromIDs(idsMessages))
			chat.addMessage(m);
		if (chat instanceof ChatGrupo)
			for (ChatIndividual c : getMembersFromIDs(idsMembers))
				((ChatGrupo) chat).addMember(c);

		return chat;
	}

	@Override
	public List<Chat> readAll() {
		List<Chat> chats = new LinkedList<>();
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			chats.add(read(e.getId()));
		return chats;
	}

	// -------------------Funciones auxiliares-----------------------------
	
	private void registrarObjetos(Chat chat) {
		AdaptadorUsuarioTDS.getInstance().create(chat.getOwner());
		
		for (Mensaje m : chat.getMessages())
			AdaptadorMensajeTDS.getInstance().create(m);
		
		if (chat instanceof ChatGrupo)
			for (ChatIndividual c : ((ChatGrupo) chat).getMembers())
				/*adaptadorChat.*/create(c);
	}
	
	private String getMessagesIDs(List<Mensaje> messages) {
		String ids = "";
		for (Mensaje m : messages)
			ids += m.getId() + " ";
		return ids.trim();
	}

	private String getMembersIDs(List<ChatIndividual> members) {
		String ids = "";
		for (ChatIndividual c : members)
			ids += c.getId() + " ";
		return ids.trim();
	}

	private List<Mensaje> getMessagesFromIDs(String ids) {
		List<Mensaje> messages = new LinkedList<>();
		if (ids.equals(""))
			return messages;

		StringTokenizer strTok = new StringTokenizer(ids, " ");
		while (strTok.hasMoreTokens())
			messages.add(AdaptadorMensajeTDS.getInstance().read(Integer.valueOf((String) strTok.nextElement())));
		
		return messages;
	}

	private List<ChatIndividual> getMembersFromIDs(String ids) {
		List<ChatIndividual> members = new LinkedList<>();
		if (ids.equals(""))
			return members;

		StringTokenizer strTok = new StringTokenizer(ids, " ");
		while (strTok.hasMoreTokens())
			members.add((ChatIndividual) /*adaptadorChat.*/read(Integer.valueOf((String) strTok.nextElement())));
		
		return members;
	}

}
