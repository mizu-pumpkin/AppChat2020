package modelo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

/**
 * Dentro de un grupo o para un contacto dado, un usuario podrá buscar mensajes con
 * diferentes filtros. Para grupos, podrá buscar combinando nombre de usuario, texto
 * a buscar y rango de fechas, cualquiera de ellos puede ser opcional. En el caso de
 * un contacto, la búsqueda es sólo por texto y rango de fechas.
 */
public abstract class Contacto {
	
	// Attributes
	private int id;
	private String name;
	protected List<Mensaje> messages;
	
	// Constructors
	public Contacto(String name) {
		this.name = name;
		this.messages = new LinkedList<>();
	}
	
	// Getters and Setters
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
		return Collections.unmodifiableList(messages);
	}
	
	public void addMessage(Mensaje message) {
		messages.add(message);
	}
	
	public void addMessage(Usuario sender, String text, Date timestamp) {
		this.addMessage(new Mensaje(sender, this, text, timestamp));
	}
	
	public void removeMessage(Mensaje message) {
		messages.remove(message);
	}
	
	public List<Mensaje> findMessagesByText(String text) {
		return messages.stream()
					   .filter(m -> m.getBody().contains(text))
					   .sorted()
					   .collect(Collectors.toList())
					   ;
	}
	
	public List<Mensaje> findMessagesByDate(Date d1, Date d2) {
		return messages.stream()
					   .filter(m -> !m.getTimestamp().before(d1))
					   .filter(m -> !m.getTimestamp().after(d2))
					   .sorted()
					   .collect(Collectors.toList())
					   ;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [id=" + id + ", name=" + name + ", messages=" + messages + "]";
	}
	
}
