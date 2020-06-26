package modelo;

public class ChatIndividual extends Chat {
	
// ---------------------------------------------------------------------
//	                                                        Constructors
// ---------------------------------------------------------------------
	
	public ChatIndividual(String name) {
		super(name, null);
	}

	public ChatIndividual(String name, Usuario user) {
		super(name, user);
	}

	public ChatIndividual(Usuario user) {
		super(user.getUsername(), user);
	}
	
// ---------------------------------------------------------------------
//	                                                 Getters and Setters
// ---------------------------------------------------------------------
	
	public String getPhone() { // movil
		return getOwner().getPhone();
	}
	
// ---------------------------------------------------------------------
//		                                                         Methods
// ---------------------------------------------------------------------
	
	@Override
	public String toString() {
		return super.toString() + 
			   "[user=" + getOwner().getId() + "]";
	}
}
