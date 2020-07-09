package modelo;

import java.util.LinkedList;
import java.util.List;

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
	
	public List<ChatIndividual> getMembers() {
		return new LinkedList<>(members);
	}
	
	public void addMember(ChatIndividual m) {
		members.add(m);
	}

	@Override
	public String getAvatar() {
		return "";//TODO
	}
	
// ---------------------------------------------------------------------
//	                                                             Methods
// ---------------------------------------------------------------------

	/**
	 * Comprueba si el usuario es el admin del grupo.
	 * @param u el usuario del que se quiere comprobar.
	 * @return true si el usuario es el admin del grupo.
	 */
	public boolean isAdmin(Usuario u) {
		return u.equals(admin);
	}
	
	/**
	 * Comprueba si un contacto pertenece al grupo.
	 * @param c el contacto del que se quiere comprobar.
	 * @return true si el contacto pertenece al grupo.
	 */
	public boolean isMember(ChatIndividual c) {
		return isAdmin(c.getUser()) || members.contains(c);
	}

	/**
	 * Comprueba si un usuario pertenece al grupo.
	 * @param user el usuario del que se quiere comprobar.
	 * @return true si el usuario pertenece al grupo.
	 */
	public boolean isMember(Usuario user) {
		return isAdmin(user) || members.stream()
				.map(m -> m.getUser())
				.anyMatch(u -> u.equals(user))
				;
	}
	
	/**
	 * Añade el miembro al grupo, asegurandose de que el
	 * contacto también se una al grupo.
	 * @param m el miembro que se quiere añadir.
	 */
	public void join(ChatIndividual m) {
		addMember(m);
		m.joinGroup(this);
	}
	
	/**
	 * Modifica nombre y lista de contactos del grupo.
	 * @param name el nuevo nombre.
	 * @param newMembers la nueva lista de contactos.
	 */
	public void editGroup(String name, List<ChatIndividual> newMembers) {
		setName(name);
		clearGroup();
		for (ChatIndividual m : newMembers)
			join(m);
	}
	
	/**
	 * Elimina el usuario de la lista de contactos, asegurandose
	 * de que el contacto también deje el grupo.
	 * @param user el usuario que se quiere eliminar.
	 */
	public void removeMember(Usuario user) {
		for (ChatIndividual m : members)
			if (m.isUser(user)) {
				removeMember(m);
				return;
			}
	}

	/**
	 * Elimina el contacto de la lista de contactos, asegurandose
	 * de que el contacto también deje el grupo.
	 * @param m el contacto que se quiere eliminar.
	 */
	public void removeMember(ChatIndividual m) {
		members.remove(m);
		m.leaveGroup(this);
	}
	
	/**
	 * Vacía el grupo de todos sus contactos, asegurandose de que
	 * cada contacto también deje el grupo.
	 */
	public void clearGroup() {
		for (ChatIndividual m : members)
			m.leaveGroup(this);
		members.clear();//Supuestamente esto no se necesita
	}

}
