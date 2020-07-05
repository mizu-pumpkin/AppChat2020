package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatGrupo extends Chat {
	
// ---------------------------------------------------------------------
//	                                                          Attributes
// ---------------------------------------------------------------------
	
	private Usuario admin;
	private List<ChatIndividual> members; //contactos
	
// ---------------------------------------------------------------------
//	                                                        Constructors
// ---------------------------------------------------------------------
		
	public ChatGrupo(String name, Usuario admin) {
		super(name);
		this.admin = admin;
		this.members = new LinkedList<>();
	}

	public ChatGrupo(String name) {
		this(name, null);
	}
	
// ---------------------------------------------------------------------
//                                                   Getters and Setters
// ---------------------------------------------------------------------

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario a) {
		admin = a;
	}

	public boolean isAdmin(Usuario u) {
		return u.equals(admin);
	}
	
	public List<ChatIndividual> getMembers() {
		return new LinkedList<>(members);
	}
	
	public void addMember(ChatIndividual m) {
		members.add(m);
	}
	
	public void editGroup(String name, List<ChatIndividual> newMembers) {
		this.name = name;
		clearGroup();
		for (ChatIndividual m : newMembers) {
			addMember(m);
			m.joinGroup(this);
		}
	}
	
	public void removeMember(Usuario user) {
		ChatIndividual found = null;
		for (ChatIndividual m : members)
			if (m.isUser(user)) {
				found = m;
				break;
			}
		if (found != null) removeMember(found);
	}
	
	public void removeMember(ChatIndividual m) {
		members.remove(m);
		m.leaveGroup(this);
	}
	
	public void clearGroup() {
		for (ChatIndividual m : members)
			m.leaveGroup(this);
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
			   "[admin=" + admin.getId() + ", members=" + members + "]";
	}

}
