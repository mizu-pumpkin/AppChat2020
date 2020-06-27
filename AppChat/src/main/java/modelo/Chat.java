package modelo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.LinkedList;

public abstract class Chat {
	
// ---------------------------------------------------------------------
//		                                                      Attributes
// ---------------------------------------------------------------------
		
	private int id;
	private String name;
	private Usuario owner; // usuario o admin
	protected List<Mensaje> messages; // mensajes
	
// ---------------------------------------------------------------------
//		                                                    Constructors
// ---------------------------------------------------------------------
		
	public Chat(String name, Usuario owner) {
		this.name = name;
		this.owner = owner;
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
	
	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public List<Mensaje> getMessages() {
		return new LinkedList<Mensaje>(messages);
	}
	
	public void addMessage(Mensaje message) {
		messages.add(message);
	}
	
	public void removeMessage(Mensaje message) {
		messages.remove(message);
	}
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------
	
	public Mensaje sendMessage(Usuario sender, String text) {
		Mensaje msg = new Mensaje(sender, this, text);
		addMessage(msg);
		return msg;
	}
	
	public Mensaje sendMessage(Usuario sender, int emoticon) {
		Mensaje msg = new Mensaje(sender, this, emoticon);
		addMessage(msg);
		return msg;
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
		return this.getClass().getSimpleName() +
			   " [id=" + id + ", name=" + name + ", messages=" + messages + "]";
	}
	
}