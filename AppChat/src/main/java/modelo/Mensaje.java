package modelo;

import java.time.ZoneId;
import java.util.Date;

import whatsapp.modelo.MensajeWhatsApp;

public class Mensaje implements Comparable<Mensaje> {

// ---------------------------------------------------------------------
//                                                             Constants
// ---------------------------------------------------------------------
	
	public static final int TEXT_BODY = 1;
	public static final int EMOJI_BODY = 2;

// ---------------------------------------------------------------------
//                                                            Attributes
// ---------------------------------------------------------------------
	
	private int id;
	private Usuario sender; // emisor
	private Chat chat; // receptor
	private final String body;
	private final Date timestamp;
	private final int bodyType;
	
// ---------------------------------------------------------------------
//                                                          Constructors
// ---------------------------------------------------------------------

	public Mensaje(Usuario sender, Chat chat, String text, Date timestamp) {
		this.sender = sender;
		this.chat = chat;
		this.body = text;
		this.timestamp = timestamp;
		this.bodyType = TEXT_BODY;
	}

	public Mensaje(Usuario sender, Chat chat, String text) {
		this(sender, chat, text, new Date());
	}
	
	public Mensaje(String text, Date timestamp) {
		this(null, null, text, timestamp);
	}
	
	public Mensaje(Usuario sender, Chat chat, int emoji, Date timestamp) {
		this.sender = sender;
		this.chat = chat;
		this.body = Integer.toString(emoji);
		this.timestamp = timestamp;
		this.bodyType = EMOJI_BODY;
	}

	public Mensaje(Usuario sender, Chat chat, int emoji) {
		this(sender, chat, emoji, new Date());
	}
	
	public Mensaje(int emoji, Date timestamp) {
		this(null, null, emoji, timestamp);
	}
	
	/**
	 * Constructor con mensaje de WhatsApp
	 * @param sender Usuario que ha escrito mwa.
	 * @param chat Chat donde se ha escrito mwa.
	 * @param mwa El objeto que se va a transformar en Mensaje.
	 */
	public Mensaje(Usuario sender, Chat chat, MensajeWhatsApp mwa) {
		this(sender, chat, mwa.getTexto(), Date.from(mwa.getFecha().atZone(ZoneId.systemDefault()).toInstant()));
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

	public Usuario getSender() {
		return sender;
	}

	public void setSender(Usuario sender) {
		this.sender = sender;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public String getBody() {
		return body;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public int getBodyType() {
		return bodyType;
	}

// ---------------------------------------------------------------------
//	                                                 Getters and Setters
// ---------------------------------------------------------------------
	
	public String getSenderName() {
		return sender.getUsername();
	}
	
	public boolean sentBefore(Date d) {
		return timestamp.before(d);
	}
	
	public boolean sentAfter(Date d) {
		return timestamp.after(d);
	}
	
	public boolean isSender(Usuario u) {
		return u.equals(sender);
	}
	
	public boolean isSender(String username) {
		return getSenderName().equals(username);
	}
	
	public boolean containsText(String text) {
		return body.contains(text.trim());
	}
	
	@Override
	public int compareTo(Mensaje m) {
		return timestamp.compareTo(m.timestamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + bodyType;
		result = prime * result + ((chat == null) ? 0 : chat.hashCode());
		result = prime * result + id;
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Mensaje other = (Mensaje) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (bodyType != other.bodyType)
			return false;
		if (chat == null) {
			if (other.chat != null)
				return false;
		} else if (!chat.equals(other.chat))
			return false;
		if (id != other.id)
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
}
