package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Chat;
import modelo.Estado;
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
	private static final String PROPERTY_CHATS = "chats";

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
	public void create(Usuario user) {
		Entidad eUser;
		
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eUser = servPersistencia.recuperarEntidad(user.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		registrarObjetos(user);
		
		// Crear entidad
		eUser = new Entidad();
		eUser.setNombre(ENTITY_NAME);
		eUser.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
			new Propiedad(PROPERTY_USERNAME, user.getUsername()),
			new Propiedad(PROPERTY_PASSWORD, user.getPassword()),
			new Propiedad(PROPERTY_NAME, user.getName()),
			new Propiedad(PROPERTY_BIRTHDAY, dateFormat.format(user.getBirthday())),
			new Propiedad(PROPERTY_EMAIL, user.getEmail()),
			new Propiedad(PROPERTY_PHONE, user.getPhone()),
			new Propiedad(PROPERTY_GREETING, user.getGreeting()),
			new Propiedad(PROPERTY_PREMIUM, String.valueOf(user.isPremium())),
			new Propiedad(PROPERTY_AVATAR, user.getAvatar()),
			new Propiedad(PROPERTY_STORY_TEXT, user.getStory().getText()),
			new Propiedad(PROPERTY_STORY_PICTURE, user.getStory().getPicture()),
			new Propiedad(PROPERTY_CHATS, getChatsIDs(user.getChats()))
		)));
		
		// Registrar entidad
		eUser = servPersistencia.registrarEntidad(eUser);
		
		// Asignar el identificador único que genera el servicio de persistencia
		user.setId(eUser.getId());
	}

	@Override
	public void delete(Usuario user) {
		// No se tienen en cuenta restricciones de integridad
		servPersistencia.borrarEntidad(servPersistencia.recuperarEntidad(user.getId()));
	}
	
	@Override
	public void update(Usuario user) {
		Entidad eUser = servPersistencia.recuperarEntidad(user.getId());
		
		//servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_USERNAME);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_PASSWORD);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_NAME);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_BIRTHDAY);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_EMAIL);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_PHONE);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_GREETING);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_PREMIUM);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_AVATAR);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_STORY_TEXT);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_STORY_PICTURE);
		servPersistencia.eliminarPropiedadEntidad(eUser, PROPERTY_CHATS);
		
		// ???:FIXME: debería volver a registrar las propiedades que son objetos?
		registrarObjetos(user);
		
		//servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_USERNAME, user.getUsername());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_PASSWORD, user.getPassword());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_NAME, user.getName());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_BIRTHDAY, dateFormat.format(user.getBirthday().getTime()));
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_EMAIL, user.getEmail());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_PHONE, user.getPhone());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_GREETING, user.getGreeting());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_PREMIUM, String.valueOf(user.isPremium()));
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_AVATAR, user.getAvatar());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_STORY_TEXT, user.getStory().getText());
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_STORY_PICTURE, user.getStory().getPicture());		
		servPersistencia.anadirPropiedadEntidad(eUser, PROPERTY_CHATS, getChatsIDs(user.getChats()));
		
		PoolDAO.getInstance().addObject(user.getId(), user);
	}
	
	@Override
	public Usuario read(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (Usuario) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eUser = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		String username = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_USERNAME);
		String password = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_PASSWORD);
		String name = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_NAME);
		Date birthday = null;
		try {
			birthday = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_BIRTHDAY));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String email = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_EMAIL);
		String phone = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_PHONE);
		String greeting = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_GREETING);
		boolean premium;
		if (servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_PREMIUM).equals("true"))
			premium = true;
		else premium = false;
		String avatar = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_AVATAR);
		Estado story = new Estado(servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_STORY_TEXT),
								  servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_STORY_PICTURE));
		String idsChats = servPersistencia.recuperarPropiedadEntidad(eUser, PROPERTY_CHATS);

		// Crear el objeto
		Usuario usuario = new Usuario(username, password, name, birthday, email, phone, greeting);
		usuario.setPremium(premium);
		usuario.setAvatar(avatar);
		usuario.setStory(story);
		usuario.setId(id);
		
		// Añadir el cliente al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, usuario);

		// Recuperar propiedades que son objetos llamando a adaptadores
		for (Chat c : getChatsFromIDs(idsChats))
			usuario.addChat(c);

		return usuario;
	}

	@Override
	public List<Usuario> readAll() {
		List<Usuario> usuarios = new LinkedList<>();
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			usuarios.add(read(e.getId()));
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	
	private void registrarObjetos(Usuario user) {
		for (Chat c : user.getChats())
			AdaptadorChatTDS.getInstance().create(c);
	}
	
	private String getChatsIDs(Collection<Chat> chats) {
		String ids = "";
		for (Chat c : chats)
			ids += c.getId() + " ";
		return ids.trim();
	}

	private List<Chat> getChatsFromIDs(String ids) {
		List<Chat> chats = new LinkedList<>();
		if (ids.equals(""))
			return chats;

		StringTokenizer strTok = new StringTokenizer(ids, " ");
		while (strTok.hasMoreTokens())
			chats.add(AdaptadorChatTDS.getInstance().read(Integer.valueOf((String) strTok.nextElement())));
		
		return chats;
	}
}
