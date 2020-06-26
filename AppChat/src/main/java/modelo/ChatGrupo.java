package modelo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatGrupo extends Chat {
	
// ---------------------------------------------------------------------
//	                                                          Attributes
// ---------------------------------------------------------------------
	
	private Map<Usuario, ChatIndividual> members; // contactos
	
// ---------------------------------------------------------------------
//	                                                        Constructors
// ---------------------------------------------------------------------
		
	public ChatGrupo(String name, Usuario admin) {
		super(name, admin);
		this.members = new HashMap<>();
	}

	public ChatGrupo(String name) {
		this(name, null);
	}
	
// ---------------------------------------------------------------------
//                                                   Getters and Setters
// ---------------------------------------------------------------------

	public List<ChatIndividual> getMembers() {
		return new LinkedList<ChatIndividual>(members.values());
	}
	
	public void addMember(ChatIndividual contact) {
		members.put(contact.getOwner(), contact);
	}
	
	public void removeMember(ChatIndividual contact) {
		members.remove(contact.getOwner());
	}
	
	public void removeMember(Usuario user) {
		members.remove(user);
	}
	
	public void clearGroup() {
		for (Usuario u : members.keySet())
			u.removeChat(this);
		members.clear();
	}
	
// ---------------------------------------------------------------------
//	                                                             Methods
// ---------------------------------------------------------------------
	
	public List<Mensaje> findMessagesByUsername(Usuario user) {
		return messages.stream()
					   .filter(m -> m.getSender().equals(user))
					   .sorted()
					   .collect(Collectors.toList())
					   ;
	}
	
	public List<Mensaje> findMessages() {
		/* TODO
		 * Dentro de un grupo un usuario podrá buscar mensajes combinando
		 * nombre de usuario, texto a buscar y rango de fechas, cualquiera
		 * de ellos puede ser opcional.
		 */
		return messages.stream().collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return super.toString() +
			   "[admin=" + getOwner().getId() + ", members=" + members + "]";
	}

}
