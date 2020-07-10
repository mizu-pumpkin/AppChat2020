package modelo;

public class ChatIndividual extends Chat {
	
	private Usuario user;
	
// ---------------------------------------------------------------------
//	                                                        Constructors
// ---------------------------------------------------------------------

	public ChatIndividual(String name, Usuario user) {
		super(name);
		this.user = user;
	}

	public ChatIndividual(String name) {
		this(name, null);
	}
	
	public ChatIndividual(Usuario user) {
		this(user.getPhone(), user);
	}
	
// ---------------------------------------------------------------------
//	                                                 Getters and Setters
// ---------------------------------------------------------------------

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario u) {
		user = u;
	}
	
	public String getPhone() { // movil
		return user.getPhone();
	}
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------
	
	public String getUsername() {
		return user.getUsername();
	}
	
	/**
	 * Comprueba si el usuario es el usuario del contacto.
	 * @param u el usuario del que se quiere comprobar.
	 * @return true si el usuario coincide.
	 */
	public boolean isUser(Usuario u) {
		return user.equals(u);
	}
	
	/**
	 * Devuelve el avatar del usuario del contacto.
	 * return la ruta del avatar del usuario.
	 */
	@Override
	public String getAvatar() {
		return user.getAvatar();
	}
	
	/**
	 * Añade el grupo al usuario asociado.
	 * @param g el grupo al que se quiere unir.
	 */
	public void joinGroup(ChatGrupo g) {
		user.addChat(g);
	}

	/**
	 * El usuario asociado deja el grupo.
	 * @param g el grupo que se quiere dejar.
	 */
	public void leaveGroup(ChatGrupo g) {
		user.removeChat(g);
	}
	
	/**
	 * Devuelve el contacto que el usuario del contacto asocia al
	 * usuario pasado como parámetro.
	 * @param u el usuario del que se quiere obtener el contacto.
	 * @return el contacto asociado al usuario.
	 */
	public ChatIndividual getChatWith(Usuario u) {
		return user.getPrivateChat(u);
	}
}
