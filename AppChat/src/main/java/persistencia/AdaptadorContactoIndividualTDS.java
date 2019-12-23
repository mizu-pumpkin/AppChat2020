package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoIndividualTDS implements IAdaptadorContactoIndividualDAO {
	
	private static final String ENTITY_NAME = "contactoindividual";
	private static final String PROPERTY_NAME = "name";
	private static final String PROPERTY_MESSAGES = "messages";
	private static final String PROPERTY_USER = "user";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividualTDS instance = null;

	public static AdaptadorContactoIndividualTDS getInstance() {
		if (instance == null)
			instance = new AdaptadorContactoIndividualTDS();
		
		return instance;
	}
	
	private AdaptadorContactoIndividualTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void create(ContactoIndividual contactoIndividual) {
		Entidad eContactoIndividual;
		
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstance();
		for (Mensaje m : contactoIndividual.getMessages())
			adaptadorMensaje.create(m);
		AdaptadorUsuarioTDS.getInstance().create(contactoIndividual.getUser());
		
		// Crear entidad
		eContactoIndividual = new Entidad();
		eContactoIndividual.setNombre(ENTITY_NAME);
		eContactoIndividual.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad(PROPERTY_NAME, contactoIndividual.getName()),
				new Propiedad(PROPERTY_MESSAGES, getMessagesIDs(contactoIndividual.getMessages())),
				new Propiedad(PROPERTY_USER, String.valueOf(contactoIndividual.getUser().getId()))
				)));
		
		// Registrar entidad
		eContactoIndividual = servPersistencia.registrarEntidad(eContactoIndividual);
		
		// Asignar el identificador  único que genera el servicio de persistencia
		contactoIndividual.setId(eContactoIndividual.getId());
		
		//FIXME Añadir al pool
		PoolDAO.getInstance().addObject(contactoIndividual.getId(), contactoIndividual);
	}

	@Override
	public void delete(ContactoIndividual contactoIndividual) {
		//TODO No se comprueban restricciones de integridad
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getId());
		
		servPersistencia.borrarEntidad(eContactoIndividual);
	}

	@Override
	public void update(ContactoIndividual contactoIndividual) {
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoIndividual.getId());

		// Eliminar propiedades
		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, PROPERTY_NAME);
		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, PROPERTY_MESSAGES);
		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, PROPERTY_USER);
		
		// Añadir las nuevas propiedades
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, PROPERTY_NAME,
												contactoIndividual.getName());
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, PROPERTY_MESSAGES,
												getMessagesIDs(contactoIndividual.getMessages()));
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, PROPERTY_USER,
												String.valueOf(contactoIndividual.getUser().getId()));
	}

	@Override
	public ContactoIndividual get(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (ContactoIndividual) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		String name = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPERTY_NAME);
		String idsMessages = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPERTY_MESSAGES);
		int idUser = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, PROPERTY_USER));
		
		// Crear el objeto
		ContactoIndividual contactoIndividual = new ContactoIndividual(name);
		contactoIndividual.setId(id);
		
		// Añadir el cliente al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, contactoIndividual);
		
		// Recuperar propiedades que son objetos llamando a adaptadores
		for (Mensaje m : getMessagesFromIDs(idsMessages))
			contactoIndividual.addMessage(m);
		contactoIndividual.setUser(AdaptadorUsuarioTDS.getInstance().get(idUser));

		return contactoIndividual;
	}

	@Override
	public List<ContactoIndividual> getAll() {
		List<ContactoIndividual> contactosIndividuales = new LinkedList<>();
		
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			contactosIndividuales.add(get(e.getId()));
		
		return contactosIndividuales;
	}

	// -------------------Funciones auxiliares-----------------------------
	private String getMessagesIDs(List<Mensaje> messages) {
		String messagesIds = "";
		
		for (Mensaje m : messages)
			messagesIds += m.getId() + " ";
		
		return messagesIds.trim();
	}

	private List<Mensaje> getMessagesFromIDs(String messagesIds) {
		List<Mensaje> messages = new LinkedList<>();
		StringTokenizer strTok = new StringTokenizer(messagesIds, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getInstance();
		
		while (strTok.hasMoreTokens())
			messages.add(adaptadorMensaje.get(Integer.valueOf((String) strTok.nextElement())));
		
		return messages;
	}
	
}
