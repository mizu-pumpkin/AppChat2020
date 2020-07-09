package modelo;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import whatsapp.modelo.MensajeWhatsApp;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public abstract class Chat {
	
// ---------------------------------------------------------------------
//		                                                      Attributes
// ---------------------------------------------------------------------
		
	private int id;
	private String name;
	protected List<Mensaje> messages; // mensajes
	
// ---------------------------------------------------------------------
//		                                                    Constructors
// ---------------------------------------------------------------------
		
	public Chat(String name) {
		this.name = name;
		this.messages = new LinkedList<>();
	}
	
// ---------------------------------------------------------------------
//		                                             Getters and Setters
// ---------------------------------------------------------------------
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Mensaje> getMessages() {
		return new LinkedList<>(messages);
	}
	
	public void addMessage(Mensaje message) {
		messages.add(message);
	}
	
	public void removeMessage(Mensaje message) {
		messages.remove(message);
	}
	
	public abstract String getAvatar();
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------

	/**
	 * Devuelve la fecha del último mensaje enviado al chat.
	 * @return la fecha del mensaje más reciente.
	 */
	public Date getTimeOfMostRecentMessage() {
		Mensaje msg = getMostRecentMessage();
		if (msg != null)
			return msg.getTimestamp();
		return new Date(0);
	}
	
	/**
	 * Devuelve el último mensaje enviado al chat.
	 * @return el mensaje más reciente.
	 */
	public Mensaje getMostRecentMessage() {
		if (messages.size() > 0)
			return messages
				.stream()
				.sorted((m1,m2) -> m2.compareTo(m1))
				.findFirst()
				.orElse(null);
		return null;
	}
	
	/**
	 * Devuelve el número de mensajes enviados por el usuario.
	 * @return el número de mensajes enviados por el usuario.
	 */
	public long getNumberOfMessagesSent(Usuario u) {
		return messages
				.stream()
				.filter(m -> m.isSender(u))
				.count();
	}
	
	/**
	 * Devuelve porcentaje de mensajes enviados por el usuario sobre
	 * el total de mensajes del chat.
	 * @return un valor entre 0 y 1 que indica el porcentaje de
	 * 			mensajes enviados por el usuario.
	 */
	public double getRatioOfMessagesSent(Usuario u) {
		return (double) getNumberOfMessagesSent(u) / (double) messages.size();
	}
	
	/**
	 * Crea y añade el mensaje a la lista de mensajes del chat.
	 * @param sender el usuario que envía el mensaje.
	 * @param text el cuerpo del mensaje.
	 * @return el mensaje enviado.
	 */
	public Mensaje sendMessage(Usuario sender, String text) {
		Mensaje msg = new Mensaje(sender, this, text);
		addMessage(msg);
		return msg;
	}

	/**
	 * Crea y añade el mensaje a la lista de mensajes del chat.
	 * @param sender el usuario que envía el mensaje.
	 * @param emoji el código del emoji del mensaje.
	 * @return el mensaje enviado.
	 */
	public Mensaje sendMessage(Usuario sender, int emoji) {
		Mensaje msg = new Mensaje(sender, this, emoji);
		addMessage(msg);
		return msg;
	}
	
	/**
	 * Método para registrar un mensaje de WhatsApp en el Chat.
	 * @param sender Usuario que envía el mensaje.
	 * @param mwa Mensaje de WhatsApp.
	 * @return Un objeto Mensaje que equivale a mwa.
	 */
	public Mensaje registerWhatsAppMessage(Usuario sender, MensajeWhatsApp mwa) {
		Mensaje msg = new Mensaje(sender, this, mwa);
		// Debe ser insertado en orden, en el momento que se escribió.
		int index = Collections.binarySearch(messages, msg, Comparator.comparing(Mensaje::getTimestamp));
		if (index < 0)
			index = -index - 1;
		messages.add(index, msg);
		return msg;
	}
	
	public List<Mensaje> findMessages(Predicate<Mensaje> criterio) {
		return messages
				.stream()
				.filter(criterio)
				.collect(Collectors.toList())
				;
	}
	
	/**
	 * Devuelve los mensajes enviados que incluyen el texto indicado
	 * en el cuerpo del mensaje.
	 * @param text el texto buscado.
	 * @return la lista de mensajes que incluyen el texto buscado.
	 */
	public List<Mensaje> findMessagesByText(String text) {
		return messages
				.stream()
				.filter(m -> m.getBody().contains(text))
				.collect(Collectors.toList())
				;
	}
	
	/**
	 * Devuelve los mensajes enviados al chat entre dos fechas.
	 * @param d1 la fecha de inicio.
	 * @param d2 la fecha de fin.
	 * @return la lista de mensajes enviados entre las fechas.
	 */
	public List<Mensaje> findMessagesByDate(Date d1, Date d2) {
		return messages
				.stream()
				.filter(m -> !m.getTimestamp().before(d1))
				.filter(m -> !m.getTimestamp().after(d2))
				.collect(Collectors.toList())
				;
	}

	/**
	 * Devuelve los mensajes enviados al chat por un usuario.
	 * @param user el usuario que ha enviado los mensajes.
	 * @return la lista de mensajes enviados por el usuario.
	 */
	public List<Mensaje> findMessagesByUser(Usuario user) {
		return messages
				.stream()
				.filter(m -> m.isSender(user))
				.collect(Collectors.toList())
				;
	}

	/**
	 * Devuelve los mensajes enviados al chat por un usuario.
	 * @param username el username del usuario que ha enviado los mensajes.
	 * @return la lista de mensajes enviados por el usuario.
	 */
	public List<Mensaje> findMessagesByUser(String username) {
		return messages
				.stream()
				.filter(m -> m.isSender(username))
				.collect(Collectors.toList())
				;
	}
	
}
