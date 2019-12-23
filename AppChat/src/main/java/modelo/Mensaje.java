package modelo;

import java.time.LocalDate;

/**
 * Un mensaje está formado por texto y puede contener emoticonos.
 * Además, un mensaje incluye el número de teléfono del emisor, quien podrá
 * estar o no en la lista de contactos del receptor con un nombre asociado.
 * Si no lo está, el usuario receptor puede añadirlo asociándole un nombre.
 * Un mensaje también se registrará con la hora de envío.
 * 
 */
public class Mensaje {
	
	public static final int TEXT_BODY = 1;
	public static final int EMOTICON_BODY = 2;

	// Attributes
	private int id;
	private Usuario sender;
	private Contacto receiver;
	private String body;
	private LocalDate timestamp;
	private int bodyType;

	// Constructors
	public Mensaje(Usuario sender, Contacto receiver, String text, LocalDate timestamp) {
		this.sender = sender;
		this.receiver = receiver;
		this.body = text;
		this.timestamp = timestamp;
		this.bodyType = TEXT_BODY;
	}
	
	public Mensaje(Usuario sender, Contacto receiver, String text) {
		this(sender, receiver, text, LocalDate.now());
	}
	
	public Mensaje(String text, LocalDate timestamp) {
		this(null, null, text, timestamp);
	}
	
	public Mensaje(Usuario sender, Contacto receiver, int emoticon, LocalDate timestamp) {
		this.sender = sender;
		this.receiver = receiver;
		this.body = Integer.toString(emoticon);
		this.timestamp = timestamp;
		this.bodyType = EMOTICON_BODY;
	}
	
	public Mensaje(Usuario sender, Contacto receiver, int emoticon) {
		this(sender, receiver, emoticon, LocalDate.now());
	}
	
	public Mensaje( int emoticon, LocalDate timestamp) {
		this(null, null, emoticon, timestamp);
	}
	
	// Getters and Setters
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

	public Contacto getReceiver() {
		return receiver;
	}

	public void setReceiver(Contacto receiver) {
		this.receiver = receiver;
	}

	public String getBody() {
		return body;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}
	
	public int getBodyType() {
		return bodyType;
	}

}
