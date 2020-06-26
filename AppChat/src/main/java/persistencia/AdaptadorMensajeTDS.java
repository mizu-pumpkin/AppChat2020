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
import modelo.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	
	private static final String ENTITY_NAME = "mensaje";
	private static final String PROPERTY_SENDER = "sender";
	private static final String PROPERTY_CHAT = "chat";
	private static final String PROPERTY_BODY = "body";
	private static final String PROPERTY_BODY_TYPE = "type";
	private static final String PROPERTY_TIMESTAMP = "timestamp";

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
	public void create(Mensaje msg) {
		Entidad eMsg;
		
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eMsg = servPersistencia.recuperarEntidad(msg.getId());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// Registrar primero los atributos que son objetos
		registrarObjetos(msg);

		// Crear entidad
		eMsg = new Entidad();
		eMsg.setNombre(ENTITY_NAME);
		eMsg.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
			new Propiedad(PROPERTY_SENDER, String.valueOf(msg.getSender().getId())),
			new Propiedad(PROPERTY_CHAT, String.valueOf(msg.getChat().getId())),
			new Propiedad(PROPERTY_BODY, msg.getBody()),
			new Propiedad(PROPERTY_BODY_TYPE, String.valueOf(msg.getBodyType())),
			new Propiedad(PROPERTY_TIMESTAMP, dateFormat.format(msg.getTimestamp()))
		)));
		
		// Registrar entidad
		eMsg = servPersistencia.registrarEntidad(eMsg);
		
		// Asignar el identificador único que genera el servicio de persistencia
		msg.setId(eMsg.getId());
	}

	@Override
	public void delete(Mensaje msg) {
		// No se tienen en cuenta restricciones de integridad
		servPersistencia.borrarEntidad(servPersistencia.recuperarEntidad(msg.getId()));
	}

	@Override
	public void update(Mensaje msg) {
		Entidad eMsg = servPersistencia.recuperarEntidad(msg.getId());

		//servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_SENDER);
		//servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_RECEIVER);
		servPersistencia.eliminarPropiedadEntidad(eMsg, PROPERTY_BODY);
		servPersistencia.eliminarPropiedadEntidad(eMsg, PROPERTY_BODY_TYPE);
		//servPersistencia.eliminarPropiedadEntidad(eMensaje, PROPERTY_TIMESTAMP);
		
		// ???:FIXME: debería volver a registrar las propiedades que son objetos?
		registrarObjetos(msg);
		
		//servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_SENDER, String.valueOf(mensaje.getSender().getId()));
		//servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_RECEIVER, String.valueOf(mensaje.getChat().getId()));
		servPersistencia.anadirPropiedadEntidad(eMsg, PROPERTY_BODY, msg.getBody());
		servPersistencia.anadirPropiedadEntidad(eMsg, PROPERTY_BODY_TYPE, String.valueOf(msg.getBodyType()));
		//servPersistencia.anadirPropiedadEntidad(eMensaje, PROPERTY_TIMESTAMP, dateFormat.format(mensaje.getTimestamp()));
		
		PoolDAO.getInstance().addObject(msg.getId(), msg);
	}

	@Override
	public Mensaje read(int id) {
		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getInstance().contains(id))
			return (Mensaje) PoolDAO.getInstance().getObject(id);
		
		// Si no, la recupera de la base de datos
		Entidad eMsg = servPersistencia.recuperarEntidad(id);
		
		// Recuperar propiedades que no son objetos
		int idSender = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMsg, PROPERTY_SENDER));
		int idChat = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMsg, PROPERTY_CHAT));
		String body = servPersistencia.recuperarPropiedadEntidad(eMsg, PROPERTY_BODY);
		int bodyType = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMsg, PROPERTY_BODY_TYPE));
		Date timestamp = null;
		try {
			timestamp = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eMsg, PROPERTY_TIMESTAMP));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Crear el objeto
		Mensaje mensaje = (bodyType == Mensaje.TEXT_BODY) ?
				new Mensaje(body, timestamp) : new Mensaje(Integer.parseInt(body), timestamp);
		mensaje.setId(id);
		
		// Añadir el objeto al pool antes de llamar a otros adaptadores
		PoolDAO.getInstance().addObject(id, mensaje);

		// Recuperar propiedades que son objetos llamando a adaptadores
		mensaje.setSender(AdaptadorUsuarioTDS.getInstance().read(idSender));
		mensaje.setChat(AdaptadorChatTDS.getInstance().read(idChat));

		return mensaje;
	}

	@Override
	public List<Mensaje> readAll() {
		List<Mensaje> mensajes = new LinkedList<>();
		for (Entidad e : servPersistencia.recuperarEntidades(ENTITY_NAME))
			mensajes.add(read(e.getId()));
		return mensajes;
	}
	
	// -------------------Funciones auxiliares-----------------------------
	
	private void registrarObjetos(Mensaje msg) {
		AdaptadorUsuarioTDS.getInstance().create(msg.getSender());
		//AdaptadorChatTDS.getInstance().create(msg.getChat());
	}
	
}
