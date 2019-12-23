package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupoTDS implements IAdaptadorGrupoDAO {
	
	private static final String ENTITY_NAME = "grupo";
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_MESSAGES = "messages";
	private static final String PROPERTY_ADMIN = "admin";
	private static final String PROPERTY_MEMBERS = "members";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupoTDS instance = null;
	
	public static AdaptadorGrupoTDS getInstance() {
		if (instance == null)
			instance = new AdaptadorGrupoTDS();

		return instance;
	}
	
	private AdaptadorGrupoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void create(Grupo grupo) {
		Entidad eGrupo;
		
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstance();
		for (Mensaje m : grupo.getMessages())
			adaptadorMensaje.create(m);
		AdaptadorUsuarioTDS.getInstance().create(grupo.getAdmin());
		AdaptadorContactoIndividualTDS adaptadorContactoIndividual = AdaptadorContactoIndividualTDS.getInstance();
		for (ContactoIndividual c : grupo.getMembers())
			adaptadorContactoIndividual.create(c);
		
		// Crear entidad
		eGrupo = new Entidad();
		eGrupo.setNombre(ENTITY_NAME);
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(PROPERTY_NAME, grupo.getName()),
				new Propiedad(PROPERTY_MESSAGES, getMessagesIDs(grupo.getMessages())),
				new Propiedad(PROPERTY_ADMIN, String.valueOf(grupo.getAdmin().getId())),
				new Propiedad(PROPERTY_MEMBERS, getMembersIDs(grupo.getMembers()))
				)));
		
		// Registrar entidad
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		
		// Asignar el identificador único que genera el servicio de persistencia
		grupo.setId(eGrupo.getId());
		
		//FIXME Añadir al pool?
		PoolDAO.getInstance().addObject(grupo.getId(), grupo);
	}

	@Override
	public void delete(Grupo grupo) {
		//TODO No se comprueban restricciones de integridad
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());
		
		servPersistencia.borrarEntidad(eGrupo);
	}

	@Override
	public void update(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getId());

		servPersistencia.eliminarPropiedadEntidad(eGrupo, PROPERTY_NAME);
		servPersistencia.eliminarPropiedadEntidad(eGrupo, PROPERTY_MESSAGES);
		servPersistencia.eliminarPropiedadEntidad(eGrupo, PROPERTY_ADMIN);
		servPersistencia.eliminarPropiedadEntidad(eGrupo, PROPERTY_MEMBERS);
		
		servPersistencia.anadirPropiedadEntidad(eGrupo, PROPERTY_NAME, grupo.getName());
		servPersistencia.anadirPropiedadEntidad(eGrupo, PROPERTY_MESSAGES, getMessagesIDs(grupo.getMessages()));
		servPersistencia.anadirPropiedadEntidad(eGrupo, PROPERTY_ADMIN, String.valueOf(grupo.getAdmin().getId()));
		servPersistencia.anadirPropiedadEntidad(eGrupo, PROPERTY_MEMBERS, getMembersIDs(grupo.getMembers()));
	}

	@Override
	public Grupo get(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (Grupo) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eGrupo = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		String name = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPERTY_NAME);
		String idsMessages = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPERTY_MESSAGES);
		int idAdmin = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPERTY_ADMIN));
		String idsMembers = servPersistencia.recuperarPropiedadEntidad(eGrupo, PROPERTY_MEMBERS);
		
		// Crear el objeto
		Grupo grupo = new Grupo(name);
		grupo.setId(id);
		
		// Añadir el objeto al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, grupo);
		
		// Recuperar propiedades que son objetos llamando a adaptadores
		for (Mensaje m : getMessagesFromIDs(idsMessages))
			grupo.addMessage(m);
		grupo.setAdmin(AdaptadorUsuarioTDS.getInstance().get(idAdmin));
		for (ContactoIndividual c : getMembersFromIDs(idsMembers))
			grupo.addMember(c);

		return grupo;
	}

	@Override
	public List<Grupo> getAll() {
		List<Grupo> grupos = new LinkedList<>();
		
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			grupos.add(get(e.getId()));
		
		return grupos;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String getMessagesIDs(List<Mensaje> messages) {
		String messagesIds = "";
		
		for (Mensaje m : messages)
			messagesIds += m.getId() + " ";
		
		return messagesIds.trim();
	}

	private List<Mensaje> getMessagesFromIDs(String messagesIds) {
		StringTokenizer strTok = new StringTokenizer(messagesIds, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstance();
		List<Mensaje> messages = new LinkedList<>();
		
		while (strTok.hasMoreTokens())
			messages.add(adaptadorMensaje.get(Integer.valueOf((String) strTok.nextElement())));
		
		return messages;
	}

	private String getMembersIDs(List<ContactoIndividual> members) {
		String membersIds = "";
		
		for (ContactoIndividual c : members)
			membersIds += c.getId() + " ";
		
		return membersIds.trim();
	}

	private List<ContactoIndividual> getMembersFromIDs(String membersIds) {
		StringTokenizer strTok = new StringTokenizer(membersIds, " ");
		AdaptadorContactoIndividualTDS adaptadorC = AdaptadorContactoIndividualTDS.getInstance();
		List<ContactoIndividual> members = new LinkedList<>();
		
		while (strTok.hasMoreTokens())
			members.add(adaptadorC.get(Integer.valueOf((String) strTok.nextElement())));
		
		return members;
	}

}
