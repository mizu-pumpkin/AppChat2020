package umu.tds.dominio;

public class Empresa {
	private int id;
	private String nombre;
	private String domicilio;
	private String cif;
	
	public Empresa( String nombre, String domicilio, String cif, String login, String password) {
		this.id = 0;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.cif = cif;
		this.login = login;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String login;
	private String password;
	

}
