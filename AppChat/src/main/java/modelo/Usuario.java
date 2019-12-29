package modelo;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Para utilizar los servicios del sistema, los usuarios deben estar registrados
 * y realizar un login con su nombre y contrase�a.
 * Para registrarse un usuario debe indicar su nombre, fecha de nacimiento, email,
 * n�mero de tel�fono m�vil, usuario y contrase�a, y un mensaje de saludo opcional.
 * 
 * Un usuario puede enviar y recibir mensajes a/de otros usuarios. Cuando un usuario
 * recibe un mensaje de otro usuario que no est� en su lista de contactos lo puede
 * a�adir asociando un nombre a su tel�fono. En cualquier momento, un usuario puede
 * a�adir contactos a su lista indicando un nombre para el contacto y su tel�fono.
 * Un usuario puede en cualquier momento a�adir una imagen a sus datos personales.
 * 
 * Una vez registrado, el usuario podr� convertir su cuenta en una cuenta �Premium�
 * pagando una cantidad anual. Existen descuentos como son aquellos dirigidos a
 * usuarios registrados en un intervalo de fechas o aquellos usuarios que han enviado
 * m�s de un cierto n�mero de mensajes en el �ltimo mes. Los usuarios Premium tienen
 * disponible la funcionalidad de las estad�sticas de uso comentada m�s adelante y la
 * de generar un documento pdf con la lista de contactos.
 * (Opcional) Un usuario puede tener un estado que consiste en una frase y una imagen.
 * Un usuario puede ver la lista de estados actuales de sus contactos.
 */
public class Usuario {

	// Attributes
	private int id;
	private String username;
	private String password;
	private String name;
	private Date birthday;
	private String email;
	private String phone;
	private String greeting;
	private String avatar;
	private boolean premium;
	private Estado story;
	private Map<Integer, Contacto> contacts;
	private Map<Usuario, ContactoIndividual> privateChats;
	private List<Contacto> adminGroups;
	
	// Constructors
	public Usuario(String username,
				   String password,
				   String name,
				   Date birthday,
				   String email,
				   String phone,
				   String greeting) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.greeting = greeting;
		this.avatar = "";
		this.premium = false;
		this.story = new Estado("", "");
		this.contacts = new HashMap<>();
		this.privateChats = new HashMap<>();
		this.adminGroups = new LinkedList<>();
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public Estado getStory() {
		return story;
	}

	public void setStory(Estado story) {
		this.story = story;
	}

	public List<Contacto> getContacts() {
		return new LinkedList<Contacto>(contacts.values());
	}

	public List<Contacto> getContactosIndividuales() {
		return new LinkedList<Contacto>(/*privateChats.values()*/);
	}

	public List<Contacto> getGrupos() {
		return contacts.values().stream()
							    .filter(c -> c instanceof Grupo)
							    .collect(Collectors.toList())
							    ;
	}

	public void addContact(Contacto contact) {
		if (contacts.containsKey(contact.getId())) return;
		
		contacts.put(contact.getId(), contact);
		
		if (contact instanceof ContactoIndividual) {
			ContactoIndividual c = (ContactoIndividual) contact;
			privateChats.put(c.getUser(), c);
		}
	}
	
	public void removeContact(Contacto contact) {
		contacts.remove(contact.getId());
	}

	public List<Contacto> getAdminGroups() {
		return new LinkedList<Contacto>(adminGroups);
	}

	public void addAdminGroup(Grupo adminGroup) {
		adminGroups.add(adminGroup);
	}

	public void removeAdminGroup(Grupo adminGroup) {
		adminGroups.remove(adminGroup);
	}

	// Methods
	public void sendMessage(Contacto chat, String text, Date timestamp) {
		chat.addMessage(this, text, timestamp);
	}
	
	/**
	 * Cuando un usuario recibe un mensaje de otro usuario que no esté en su
	 * lista de contactos lo puede añadir asociando un nombre a su teléfono.
	 */
	public void receivePrivateMessage(Mensaje message) {
		Usuario sender = message.getSender();
		ContactoIndividual c = privateChats.get(sender);
		if (c == null) {
			c = new ContactoIndividual(sender);
			addContact(c);
		}
		c.addMessage(message);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id +
				"\n         username=" + username +
				"\n         password=" + password + 
				"\n         name=" + name +
				"\n         birthday=" + birthday +
				"\n         email=" + email +
				"\n         phone=" + phone +
				"\n         greeting=" + greeting +
				"\n         avatar=" + avatar +
				"\n         premium=" + premium +
				"\n         story=" + story +
				"\n         contacts=" + contacts +
				"\n         privateChats=" + privateChats +
				"\n         adminGroups=" + adminGroups + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adminGroups == null) ? 0 : adminGroups.hashCode());
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((greeting == null) ? 0 : greeting.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + (premium ? 1231 : 1237);
		result = prime * result + ((privateChats == null) ? 0 : privateChats.hashCode());
		result = prime * result + ((story == null) ? 0 : story.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (adminGroups == null) {
			if (other.adminGroups != null)
				return false;
		} else if (!adminGroups.equals(other.adminGroups))
			return false;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (greeting == null) {
			if (other.greeting != null)
				return false;
		} else if (!greeting.equals(other.greeting))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (premium != other.premium)
			return false;
		if (privateChats == null) {
			if (other.privateChats != null)
				return false;
		} else if (!privateChats.equals(other.privateChats))
			return false;
		if (story == null) {
			if (other.story != null)
				return false;
		} else if (!story.equals(other.story))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
