package whatsapp.modelo;

import java.util.EventObject;
import java.util.ArrayList;
import java.util.List;

public class MensajesEvent extends EventObject {
	
	private static final long serialVersionUID = 7624573348582103290L;
	
	private List<MensajeWhatsApp> anteMensajes, nuevoMensajes;
	
	public MensajesEvent(Object fuente, 
			List<MensajeWhatsApp> anteMensajes, 
			List<MensajeWhatsApp> nuevoMensajes) {
		super(fuente);
		this.anteMensajes = new ArrayList<>(anteMensajes);
		this.nuevoMensajes = new ArrayList<>(nuevoMensajes);
	}
	
	public List<MensajeWhatsApp> getAnteMensajes() {
		return new ArrayList<>(anteMensajes);
	}
	
	public List<MensajeWhatsApp> getNuevoMensajes() {
		return new ArrayList<>(nuevoMensajes);
	}
}
