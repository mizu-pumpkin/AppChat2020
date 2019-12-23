package modelo;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Dentro de un grupo o para un contacto dado, un usuario podr� buscar mensajes con
 * diferentes filtros. Para grupos, podr� buscar combinando nombre de usuario, texto
 * a buscar y rango de fechas, cualquiera de ellos puede ser opcional. En el caso de
 * un contacto, la b�squeda es s�lo por texto y rango de fechas.
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
	
	public void removeMessage(Mensaje message) {
		messages.remove(message);
	}
	
	// Methods
	public void sendMessage(Usuario sender, String text) {
		Mensaje message = new Mensaje(sender, this, text);
		messages.add(message);
	}
	
	public List<Mensaje> findMessagesByText(String text) {
		return messages.stream()
					   .filter(m -> m.getBody().contains(text))
					   .sorted()
					   .collect(Collectors.toList())
					   ;
	}
	
	public List<Mensaje> findMessagesByDate(LocalDate d1, LocalDate d2) {
		return messages.stream()
					   .filter(m -> !m.getTimestamp().isBefore(d1))
					   .filter(m -> !m.getTimestamp().isAfter(d2))
					   .sorted()
					   .collect(Collectors.toList())
					   ;
	}
	
}
