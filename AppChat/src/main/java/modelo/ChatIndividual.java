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
	
	public boolean isUser(Usuario u) {
		return user.equals(u);
	}
	
	public String getPhone() { // movil
		return user.getPhone();
	}
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------
	
	@Override
	public String getAvatar() {
		return user.getAvatar();
	}
	
	public void joinGroup(ChatGrupo g) {
		user.joinGroup(g);
	}

	public void leaveGroup(ChatGrupo g) {
		user.leaveGroup(g);
	}
	
	public ChatIndividual getChatWith(Usuario u) {
		return user.getPrivateChat(u);
	}
	
	@Override
	public String toString() {
		return super.toString() + 
			   "[user=" + user.getId() + "]";
	}
}
