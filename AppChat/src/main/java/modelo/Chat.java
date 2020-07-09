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
	protected String name;
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
	
	// Para simplificar
	
	public void removeMessage(Mensaje message) {
		messages.remove(message);
	}
	
	public abstract String getAvatar();
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------

	public Date getTimeOfMostRecentMessage() {
		Mensaje msg = getMostRecentMessage();
		if (msg != null)
			return msg.getTimestamp();
		return new Date(0);
	}
	
	public Mensaje getMostRecentMessage() {
		if (messages.size() > 0)
			return messages
				.stream()
				.sorted((m1,m2) -> m2.compareTo(m1))
				.findFirst()
				.orElse(null);
		return null;
	}
	
	public long getNumberOfMessagesSent(Usuario u) {
		return messages
				.stream()
				.filter(m -> m.isSender(u))
				.count();
	}
	
	public float getRatioOfMessagesSent(Usuario u) {
		return (float) getNumberOfMessagesSent(u) / (float) messages.size();
	}
	
	public Mensaje sendMessage(Usuario sender, String text) {
		Mensaje msg = new Mensaje(sender, this, text);
		addMessage(msg);
		return msg;
	}
	
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName() +
			   " [id=" + id + ", name=" + name + ", messages=" + messages + "]";
	}
	
}
