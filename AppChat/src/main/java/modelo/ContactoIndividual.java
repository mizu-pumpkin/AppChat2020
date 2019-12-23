package modelo;

public class ContactoIndividual extends Contacto {

	private Usuario user;
	
	public ContactoIndividual(String name, Usuario user) {
		super(name);
		this.user = user;
	}

	public ContactoIndividual(String name, String phone) {
		this(name, CatalogoUsuarios.getInstance().getByPhone(phone));
	}

	public ContactoIndividual(String name) {
		this(name, (Usuario) null);
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

}
