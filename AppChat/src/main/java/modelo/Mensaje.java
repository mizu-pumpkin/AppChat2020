package modelo;

import java.util.Date;

public class Mensaje {

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

	@Override
	public String toString() {
		return "Mensaje [id=" + id + ", body=" + body + "]";
	}

}
