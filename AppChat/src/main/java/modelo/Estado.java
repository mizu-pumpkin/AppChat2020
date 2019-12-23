package modelo;

public class Estado {
	
	private String text;
	private String picture;
	
	public Estado(String text, String picture) {
		this.text = text;
		this.picture = picture;
	}

	public String getMensaje() {
		return text;
	}

	public String getImagen() {
		return picture;
	}

}
