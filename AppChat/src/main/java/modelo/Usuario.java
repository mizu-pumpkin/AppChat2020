package modelo;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {

// ---------------------------------------------------------------------
//                                                            Attributes
// ---------------------------------------------------------------------
	
	private int id;
	private final String username; // usuario
	private String password; // contraseña
	private String name; // nombre
	private final Date birthday; // fechanacimiento
	private String email;
	private String phone; // movil
	private String greeting;
	private String avatar; // imagen
	private Estado story; // estado
	private boolean premium; // premium
	private Collection<Chat> chats; // contactos
	private Map<Integer, ChatIndividual> privateChats;
	
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

	public List<Chat> getChats() {
		return chats
			.stream()
			.sorted(new Comparator<Chat>() {//TODO: mover a chat
				@Override
				public int compare(Chat c1, Chat c2) {
					return c2.getTimeOfMostRecentMessage().compareTo(
						   c1.getTimeOfMostRecentMessage());
				}
			})
			.collect(Collectors.toList())
			;
	}
	
	public List<ChatIndividual> getPrivateChats() {
		return privateChats.values()
			.stream()
			.sorted(new Comparator<ChatIndividual>() {//TODO: mover a chatindividual
				@Override
				public int compare(ChatIndividual c1, ChatIndividual c2) {
					return c1.getName().compareToIgnoreCase(c2.getName());
				}
			})
			.collect(Collectors.toList())
			;
	}
	
	public List<ChatGrupo> getGroups() {
		return chats
			.stream()
			.filter(c -> c instanceof ChatGrupo)
			.map(c -> (ChatGrupo) c)
			.collect(Collectors.toList())
			;
	}

	public List<ChatGrupo> getAdminGroups() { // gruposAdmin
		return getGroups()
			.stream()
			.filter(g -> g.getAdmin().equals(this))
			.collect(Collectors.toList())
			;
	}

	public boolean addChat(Chat chat) {
		if (chats.contains(chat)) return false;
		
		chats.add(chat);
		if (chat instanceof ChatIndividual) {
			ChatIndividual c = (ChatIndividual) chat;
			privateChats.put(c.getUser().getId(), c);
		}
		return true;
	}
	
	/**
	 * Borra un contacto y si es un contacto individual, también
	 * lo elimina de los grupos administrados por el usuario a los
	 * que el contacto borrado pertenece.
	 */
	public boolean removeChat(Chat chat) {
		if (!chats.contains(chat)) return false;
		
		chats.remove(chat);
		if (chat instanceof ChatIndividual) {
			ChatIndividual c = (ChatIndividual) chat;
			privateChats.remove(c.getUser().getId());
			for (ChatGrupo g : getAdminGroups())
				g.removeMember(c);
		}
		return true;
	}
	
// ---------------------------------------------------------------------
//                                                               Methods
// ---------------------------------------------------------------------
	
	public boolean knowsUser(Usuario user) {
		return privateChats.containsKey(user.getId());
	}
	
	public ChatIndividual getPrivateChat(Usuario user) {
		if (knowsUser(user)) return privateChats.get(user.getId());
		
		ChatIndividual c = new ChatIndividual(user);
		addChat(c);
		return c;
	}
	
	public ChatIndividual addContact(String name, Usuario user) {
		if (knowsUser(user)) return privateChats.get(user.getId());
		
		ChatIndividual c = new ChatIndividual(name, user);
		addChat(c);
		return c;
	}
	
	public ChatGrupo makeGroup(String name) {
		ChatGrupo g = new ChatGrupo(name, this);
		addChat(g);
		return g;
	}
	
	public boolean joinGroup(ChatGrupo g) {
		return addChat(g);
	}

	public boolean leaveGroup(ChatGrupo g) {
		return removeChat(g);
	}
	
	private List<Mensaje> getMessagesSent(Date d1, Date d2) {
		return chats
				.stream()
				.flatMap(c -> c.findMessagesByDate(d1, d2).stream())
				.filter(m -> m.isSender(this))
				.collect(Collectors.toList());
	}
	
	public long getNumberOfMessagesSent(Date d1, Date d2) {
		return getMessagesSent(d1, d2)
				.stream()
				.count();
	}
	
	public List<ChatGrupo> getGroupsSortedByMostUsed() {
		return getGroups()
			.stream()
			.sorted((g1,g2) -> //FIXME: chapuzilla
				(int) (g2.getNumberOfMessagesSent(this) - g1.getNumberOfMessagesSent(this)))
			.collect(Collectors.toList())
			;
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
		if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id +
				", username=" + username +
				", chats=" + chats +
				"]";
	}

}
