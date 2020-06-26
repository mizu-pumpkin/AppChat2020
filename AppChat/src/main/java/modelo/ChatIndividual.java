package modelo;

public class ContactoIndividual extends Contacto {

	private Usuario user;

	public ContactoIndividual(String name, String phone) {
		super(name);
		this.user = CatalogoUsuarios.getInstance().getByPhone(phone);
	}

	public ContactoIndividual(Usuario user) {
		super("");
		this.user = user;
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public String getPhone() {
		return user.getPhone();
	}
	
	@Override
	public void addMessage(Mensaje message) {
		super.addMessage(message);
		user.receivePrivateMessage(message);
	}
	
	@Override
	public String toString() {
		return super.toString() + 
				"[user=" + user.getId() + "]";
	}
	
}
