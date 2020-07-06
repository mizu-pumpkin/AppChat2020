package modelo;

import java.util.Date;

public class Mensaje implements Comparable {

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
	
	public String getSenderName() {
		return sender.getUsername();
	}

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", body=" + body + ", date=" + timestamp + "]";
	}

	// FIXME: supongo que el identificador es único.
	@Override
	public int compareTo(Object arg0) {
		if (!equals(arg0))
			return 0;
		Mensaje other = (Mensaje) arg0;
		return timestamp.compareTo(other.timestamp);
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
