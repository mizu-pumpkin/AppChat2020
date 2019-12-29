package persistencia;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	
	private static final String ENTITY_NAME = "mensaje";
	private static final String PROPERTY_SENDER = "sender";
	private static final String PROPERTY_RECEIVER = "receiver";
	private static final String PROPERTY_BODY = "body";
	private static final String PROPERTY_TIMESTAMP = "timestamp";
	private static final String PROPERTY_CHAT_TYPE = "chatType";
	private static final String PROPERTY_BODY_TYPE = "bodyType";
	private static final String TYPE_INDIVIDUAL_CHAT = "individual";
	private static final String TYPE_GROUP_CHAT = "group";
	private static final String TYPE_TEXT_BODY = "text";
	private static final String TYPE_EMOTICON_BODY = "emoticon";

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensajeTDS instance = null;
	private SimpleDateFormat dateFormat;
	
	public static AdaptadorMensajeTDS getInstance() {
		if (instance == null)
			instance = new AdaptadorMensajeTDS();
		
		return instance;
	}
	
	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public void create(Mensaje mensaje) {
		Entidad eMensaje;
		
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		AdaptadorUsuarioTDS.getInstance().create(mensaje.getSender());
		Contacto receiver = mensaje.getReceiver();
		String chatType;
		if (receiver instanceof ContactoIndividual) {
			AdaptadorContactoIndividualTDS.getInstance().create((ContactoIndividual) receiver);
			chatType = TYPE_INDIVIDUAL_CHAT;
		} else {
			AdaptadorGrupoTDS.getInstance().create((Grupo) receiver);
			chatType = TYPE_GROUP_CHAT;
		}
		
		// Crear entidad
		String bodyType = (mensaje.getBodyType() == Mensaje.TEXT_BODY) ? TYPE_TEXT_BODY : TYPE_EMOTICON_BODY;
		eMensaje = new Entidad();
		eMensaje.setNombre(ENTITY_NAME);
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
			new Propiedad(PROPERTY_SENDER, String.valueOf(mensaje.getSender().getId())),
			new Propiedad(PROPERTY_RECEIVER, String.valueOf(mensaje.getReceiver().getId())),
			new Propiedad(PROPERTY_BODY, mensaje.getBody()),
			new Propiedad(PROPERTY_TIMESTAMP, dateFormat.format(mensaje.getTimestamp())),
			new Propiedad(PROPERTY_CHAT_TYPE, chatType),
			new Propiedad(PROPERTY_BODY_TYPE, bodyType)
		)));
		
		// Registrar entidad
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		
		// Asignar el identificador único que genera el servicio de persistencia
		mensaje.setId(eMensaje.getId());
	}

	@Override
	public void delete(Mensaje mensaje) {
		//TODO Restricciones de integridad
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());
		
		servPersistencia.borrarEntidad(eMensaje);
	}

	@Override
	public void update(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getId());

		servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_SENDER);
		servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_RECEIVER);
		servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_BODY);
		servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_TIMESTAMP);
		servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_CHAT_TYPE);
		servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_BODY_TYPE);
		
		String chatType = (mensaje.getReceiver() instanceof ContactoIndividual) ? TYPE_INDIVIDUAL_CHAT : TYPE_GROUP_CHAT;
		String bodyType = (mensaje.getBodyType() == Mensaje.TEXT_BODY) ? TYPE_TEXT_BODY : TYPE_EMOTICON_BODY;
		
		servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_SENDER, String.valueOf(mensaje.getSender().getId()));
		servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_RECEIVER, String.valueOf(mensaje.getReceiver().getId()));
		servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_BODY, mensaje.getBody());
		servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_TIMESTAMP, dateFormat.format(mensaje.getTimestamp()));
		servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_CHAT_TYPE, chatType);
		servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_BODY_TYPE, bodyType);
		
		PoolDAO.getInstance().addObject(mensaje.getId(), mensaje);
	}

	@Override
	public Mensaje get(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (Mensaje) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eMensaje = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		int idSender = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPERTY_SENDER));
		int idReceiver = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPERTY_RECEIVER));
		String body = servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPERTY_BODY);
		Date timestamp = null;
		try {
			timestamp = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPERTY_TIMESTAMP));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String chatType = servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPERTY_CHAT_TYPE);
		String bodyType = servPersistencia.recuperarPropiedadEntidad(eMensaje, PROPERTY_BODY_TYPE);

		// Crear el objeto
		Mensaje mensaje = (bodyType.equals(TYPE_TEXT_BODY)) ?
				new Mensaje(body, timestamp) : new Mensaje(Integer.parseInt(body), timestamp);
		mensaje.setId(id);
		
		// Añadir el objeto al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, mensaje);

		// Recuperar propiedades que son objetos llamando a adaptadores
		Usuario sender = AdaptadorUsuarioTDS.getInstance().get(idSender);
		Contacto receiver = (chatType.equals(TYPE_INDIVIDUAL_CHAT)) ?
							AdaptadorContactoIndividualTDS.getInstance().get(idReceiver) :
							AdaptadorGrupoTDS.getInstance().get(idReceiver);
		
		mensaje.setSender(sender);
		mensaje.setReceiver(receiver);

		return mensaje;
	}

	@Override
	public List<Mensaje> getAll() {
		List<Mensaje> mensajes = new LinkedList<>();
		
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			mensajes.add(get(e.getId()));
		
		return mensajes;
	}
	
}
