package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
	private List<Contacto> contacts;
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
		this.story = null;
		this.contacts = new LinkedList<>();
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
		return new LinkedList<Contacto>(contacts);
	}

	public List<Contacto> getContactosIndividuales() {
		return contacts.stream()
					   .filter(c -> c instanceof ContactoIndividual)
					   .collect(Collectors.toList())
					   ;
	}

	public List<Contacto> getGrupos() {
		return contacts.stream()
					   .filter(c -> c instanceof Grupo)
					   .collect(Collectors.toList())
					   ;
	}
	
	/**
	 * En cualquier momento, un usuario puede añadir contactos a su lista
	 * indicando un nombre para el contacto y su teléfono.
	 */
	public ContactoIndividual addContact(String contactName, String contactPhone) {
		ContactoIndividual contactoIndividual = new ContactoIndividual(contactName, contactPhone);
		contacts.add(contactoIndividual);
		return contactoIndividual;
	}

	public Grupo addContact(String groupName, Usuario groupAdmin) {
		Grupo group = new Grupo(groupName, groupAdmin);
		contacts.add(group);
		return group;
	}

	public void addContact(Contacto contact) {
		contacts.add(contact);
	}
	
	public void removeContact(Contacto contact) {
		contacts.remove(contact);
	}

	public List<Contacto> getAdminGroups() {
		return new LinkedList<Contacto>(adminGroups);
	}

	public Grupo addAdminGroup(String name) {
		Grupo group = new Grupo(name, this);
		adminGroups.add(group);
		return group;
	}

	public void addAdminGroup(Grupo adminGroup) {
		adminGroups.add(adminGroup);
	}

	public void removeAdminGroup(Grupo adminGroup) {
		adminGroups.remove(adminGroup);
	}

	// Methods
	public void sendMessageToChat(Contacto chat, String text) {
		chat.sendMessage(this, text);
	}
	
	/**
	 * Cuando un usuario recibe un mensaje de otro usuario que no esté en su
	 * lista de contactos lo puede añadir asociando un nombre a su teléfono.
	 */
	public void receiveMessage(Mensaje message) {
		//Usuario emisor = message.getSender();
	}
}
