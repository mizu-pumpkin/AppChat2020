package modelo;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Usuario {

// ---------------------------------------------------------------------
//                                                            Attributes
// ---------------------------------------------------------------------
	
	private int id;
	private final String username; // usuario
	private String password; // contraseña
	private String name; // nombre
	private Date birthday; // fechanacimiento
	private String email;
	private String phone; // movil
	private String greeting;
	private String avatar; // imagen
	private Estado story; // estado
	private boolean premium; // premium
	private Collection<Chat> chats; // contactos
	private HashMap<Usuario, ChatIndividual> privateChats;
	
// ---------------------------------------------------------------------
//                                                          Constructors
// ---------------------------------------------------------------------
	
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
		this.story = new Estado("", "");
		this.premium = false;
		this.chats = new HashSet<>();
		this.privateChats = new HashMap<>();
	}
	
// ---------------------------------------------------------------------
//                                                   Getters and Setters
// ---------------------------------------------------------------------
	
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

	public void setPremiumOn() {
		this.premium = true;
	}

	public void setPremiumOff() {
		this.premium = false;
	}

	public Estado getStory() {
		return story;
	}

	public void setStory(Estado story) {
		this.story = story;
	}

	public Collection<Chat> getChats() {
		return Collections.unmodifiableCollection(chats);
	}
	
	public Collection<ChatIndividual> getPrivateChats() {
		return Collections.unmodifiableCollection(privateChats.values());
	}
	
	public Collection<Chat> getGroups() {
		return chats.stream()
					.filter(c -> c instanceof ChatGrupo)
					.collect(Collectors.toSet())
					;
	}

	public Collection<Chat> getAdminGroups() { // gruposAdmin
		return getGroups().stream()
					.filter(g -> g.getOwner().equals(this))
					.collect(Collectors.toSet())
					;
	}
	
	public ChatIndividual getPrivateChat(Usuario user) {
		if (privateChats.containsKey(user))
			return privateChats.get(user);
		ChatIndividual c = new ChatIndividual(user);
		addChat(c);
		return c;
	}

	public boolean addChat(Chat chat) {
		if (chats.contains(chat)) return false;
		
		chats.add(chat);
		if (chat instanceof ChatIndividual)
			privateChats.put(chat.getOwner(), (ChatIndividual) chat);
		return true;
	}
	
	public void removeChat(Chat chat) {
		if (!chats.contains(chat)) return;
		
		chats.remove(chat);
		if (chat instanceof ChatIndividual)
			privateChats.remove(chat.getOwner());
	}
	
// ---------------------------------------------------------------------
//                                                               Methods
// ---------------------------------------------------------------------
	
	public ChatIndividual addContact(String name, Usuario contact) {
		if (privateChats.containsKey(contact)) return null;
		
		ChatIndividual chat = new ChatIndividual(name, contact);
		addChat(chat);
		return chat;
	}
	
	public ChatIndividual addContact(Usuario contact) {
		if (privateChats.containsKey(contact)) return null;
		
		ChatIndividual chat = new ChatIndividual(contact);
		addChat(chat);
		return chat;
	}
	
	public ChatGrupo makeGroup(String name) {
		ChatGrupo grupo = new ChatGrupo(name, this);
		addChat(grupo);
		return grupo;
	}
	
	public boolean joinGroup(ChatGrupo grupo) {
		return addChat(grupo);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id +
				", username=" + username +
				", phone=" + phone +
				", chats=" + chats +
				"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((chats == null) ? 0 : chats.hashCode());
		//result = prime * result + ((privateChats == null) ? 0 : privateChats.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((greeting == null) ? 0 : greeting.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + (premium ? 1231 : 1237);
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
