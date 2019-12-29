package modelo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Los usuarios pueden crear grupos de contactos para compartir mensajes sobre un
 * tema. Un grupo se crea añadiendo los contactos que lo forman y asociándole un
 * nombre al grupo. Sólo el usuario que creó el grupo (único administrador) puede
 * añadir a otros contactos y eliminar el grupo. Cualquier usuario del grupo puede
 * dejar de pertenecerle en cualquier momento. Un grupo se maneja igual que un
 * contacto en el intercambio de mensajes.
 */
public class Grupo extends Contacto {
	
	private Usuario admin;
	private List<ContactoIndividual> members;

	public Grupo(String name, Usuario admin) {
		super(name);
		this.admin = admin;
		this.members = new LinkedList<>();
	}

	public Grupo(String name) {
		this(name, null);
	}
	
	/* Getters and setters */
	
	public Usuario getAdmin() {
		return admin;
	}
	
	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public List<ContactoIndividual> getMembers() {
		return Collections.unmodifiableList(members);
	}
	
	public void addMember(ContactoIndividual contact) {
		members.add(contact);
	}
	
	public void removeMember(ContactoIndividual contact) {
		members.remove(contact);
	}
	
	/* Methods */
	
	public List<Mensaje> findMessagesByUsername(Usuario user) {
		return messages.stream()
					   .filter(m -> m.getSender().equals(user))
					   .sorted()
					   .collect(Collectors.toList())
					   ;
	}
	
	/**
	 * Dentro de un grupo un usuario podr� buscar mensajes combinando
	 * nombre de usuario, texto a buscar y rango de fechas, cualquiera
	 * de ellos puede ser opcional.
	 */
	public List<Mensaje> findMessages() {
		//TODO
		return messages.stream().collect(Collectors.toList());
	}

}
