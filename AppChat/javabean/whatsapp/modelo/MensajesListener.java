package whatsapp.modelo;

import java.util.EventListener;

public interface MensajesListener extends EventListener {

	public void nuevosMensajes(MensajesEvent ev);
	
}
