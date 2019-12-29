package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Estado;
import modelo.Grupo;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	
	private static final String ENTITY_NAME = "usuario";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PASSWORD = "password";
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_BIRTHDAY = "birthday";
	private static final String PROPERTY_EMAIL = "email";
	private static final String PROPERTY_PHONE = "phone";
	private static final String PROPERTY_GREETING = "greeting";
	private static final String PROPERTY_PREMIUM = "premium";
	private static final String PROPERTY_AVATAR = "avatar";
	private static final String PROPERTY_STORY_TEXT = "storytext";
	private static final String PROPERTY_STORY_PICTURE = "storypicture";
	private static final String PROPERTY_PRIVATE_CHATS = "privatechats";
	private static final String PROPERTY_GROUP_CHATS = "groupchats";
	private static final String PROPERTY_ADMINGROUPS = "adminGroups";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS instance = null;
	private SimpleDateFormat dateFormat;
	
	public static AdaptadorUsuarioTDS getInstance() {
		if (instance == null)
			instance = new AdaptadorUsuarioTDS();
		
		return instance;
	}
	
	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public void create(Usuario usuario) {
		Entidad eUsuario;
		
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstance();
		for (Contacto c : usuario.getContactosIndividuales())
			adaptadorContactoIndividual.create((ContactoIndividual) c);
		AdaptadorGrupoTDS adaptadorGrupo= AdaptadorGrupoTDS.getInstance();
		for (Contacto g : usuario.getGrupos())
			adaptadorGrupo.create((Grupo) g);
		
		// Crear entidad
		eUsuario = new Entidad();
		eUsuario.setNombre(ENTITY_NAME);
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
			new Propiedad(PROPERTY_USERNAME, usuario.getUsername()),
			new Propiedad(PROPERTY_PASSWORD, usuario.getPassword()),
			new Propiedad(PROPERTY_NAME, usuario.getName()),
			new Propiedad(PROPERTY_BIRTHDAY, dateFormat.format(usuario.getBirthday())),
			new Propiedad(PROPERTY_EMAIL, usuario.getEmail()),
			new Propiedad(PROPERTY_PHONE, usuario.getPhone()),
			new Propiedad(PROPERTY_GREETING, usuario.getGreeting()),
			new Propiedad(PROPERTY_PREMIUM, String.valueOf(usuario.isPremium())),
			new Propiedad(PROPERTY_AVATAR, usuario.getAvatar()),
			new Propiedad(PROPERTY_STORY_TEXT, usuario.getStory().getText()),
			new Propiedad(PROPERTY_STORY_PICTURE, usuario.getStory().getPicture()),
			new Propiedad(PROPERTY_PRIVATE_CHATS, getContactsIDs(usuario.getContactosIndividuales())),
			new Propiedad(PROPERTY_GROUP_CHATS, getContactsIDs(usuario.getGrupos())),
			new Propiedad(PROPERTY_ADMINGROUPS, getContactsIDs(usuario.getAdminGroups()))
		)));
		
		// Registrar entidad
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		
		// Asignar el identificador único que genera el servicio de persistencia
		usuario.setId(eUsuario.getId());
	}

	@Override
	public void delete(Usuario usuario) {
		// Restricciones de integridad
		AdaptadorGrupoTDS adaptadorGrupo = AdaptadorGrupoTDS.getInstance();
		for (Grupo g: adaptadorGrupo.getAll())
			if (g.getAdmin().equals(usuario)) adaptadorGrupo.delete(g);

		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstance();
		for (ContactoIndividual c: adaptadorContactoIndividual.getAll())
			if (c.getUser().equals(usuario)) adaptadorContactoIndividual.delete(c);
		// Borrar entidad
		servPersistencia.borrarEntidad(servPersistencia.recuperarEntidad(usuario.getId()));
	}
	
	@Override
	public void update(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_USERNAME);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_PASSWORD);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_NAME);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_BIRTHDAY);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_EMAIL);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_PHONE);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_GREETING);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_PREMIUM);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_AVATAR);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_STORY_TEXT);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_STORY_PICTURE);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_PRIVATE_CHATS);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_GROUP_CHATS);
		servPersistencia.eliminarPropiedadEntidad(eUsuario, PROPERTY_ADMINGROUPS);
		
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_USERNAME, usuario.getUsername());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_PASSWORD, usuario.getPassword());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_NAME, usuario.getName());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_BIRTHDAY, dateFormat.format(usuario.getBirthday().getTime()));
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_EMAIL, usuario.getEmail());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_PHONE, usuario.getPhone());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_GREETING, usuario.getGreeting());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_PREMIUM, String.valueOf(usuario.isPremium()));
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_AVATAR, usuario.getAvatar());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_STORY_TEXT, usuario.getStory().getText());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_STORY_PICTURE, usuario.getStory().getPicture());
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_PRIVATE_CHATS, getContactsIDs(usuario.getContactosIndividuales()));
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_GROUP_CHATS, getContactsIDs(usuario.getGrupos()));
		servPersistencia.anadirPropiedadEntidad(eUsuario, PROPERTY_ADMINGROUPS, getContactsIDs(usuario.getAdminGroups()));
		
		PoolDAO.getInstance().addObject(usuario.getId(), usuario);
	}
	
	@Override
	public Usuario get(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (Usuario) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		String username = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_USERNAME);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_PASSWORD);
		String name = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_NAME);
		Date birthday = null;
		try {
			birthday = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_BIRTHDAY));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_EMAIL);
		String phone = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_PHONE);
		String greeting = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_GREETING);
		boolean premium = Boolean.getBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_PREMIUM));
		String avatar = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_AVATAR);
		Estado story = new Estado(servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_STORY_TEXT),
								  servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_STORY_PICTURE));
		String idsPrivateChats = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_PRIVATE_CHATS);
		String idsGroupChats = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_GROUP_CHATS);
		String idsAdminGroups = servPersistencia.recuperarPropiedadEntidad(eUsuario, PROPERTY_ADMINGROUPS);

		// Crear el objeto
		Usuario usuario = new Usuario(username, password, name, birthday, email, phone, greeting);
		usuario.setPremium(premium);
		usuario.setAvatar(avatar);
		usuario.setStory(story);
		usuario.setId(id);
		
		// Añadir el cliente al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, usuario);

		// Recuperar propiedades que son objetos llamando a adaptadores
		for (Contacto c : getPrivateChatsFromIDs(idsPrivateChats))
			usuario.addContact(c);
		for (Contacto g : getGroupsFromIDs(idsGroupChats))
			usuario.addContact(g);
		for (Grupo ag : getGroupsFromIDs(idsAdminGroups))
			usuario.addAdminGroup(ag);

		return usuario;
	}

	@Override
	public List<Usuario> getAll() {
		List<Usuario> usuarios = new LinkedList<>();
		
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			usuarios.add(get(e.getId()));
		
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String getContactsIDs(List<Contacto> contacts) {
		String contactsIds = "";
		
		for (Contacto c : contacts)
			contactsIds += c.getId() + " ";
		
		return contactsIds.trim();
	}

	private List<Contacto> getPrivateChatsFromIDs(String privateChatIds) {
		List<Contacto> contacts = new LinkedList<>();
		if (privateChatIds.equals(""))
			return contacts;

		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstance();
		StringTokenizer strTok = new StringTokenizer(privateChatIds, " ");
		while (strTok.hasMoreTokens())
			contacts.add(adaptadorContactoIndividual.get(Integer.valueOf((String) strTok.nextElement())));
		
		return contacts;
	}

	private List<Grupo> getGroupsFromIDs(String groupChatIds) {
		List<Grupo> groups = new LinkedList<>();
		if (groupChatIds.equals(""))
			return groups;
		
		AdaptadorGrupoTDS adaptadorGrupo= AdaptadorGrupoTDS.getInstance();
		StringTokenizer strTok = new StringTokenizer(groupChatIds, " ");
		while (strTok.hasMoreTokens())
			groups.add(adaptadorGrupo.get(Integer.valueOf((String) strTok.nextElement())));
		
		return groups;
	}
	
}
