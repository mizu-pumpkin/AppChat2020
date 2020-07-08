package whatsapp.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main implements MensajesListener {

	public static void main(String[] args) {
		List<MensajeWhatsApp> lista = new LinkedList<>();
		lista.add(new MensajeWhatsApp("Yo", "hola", LocalDateTime.now()));
		CargadorMensajes cargador = new CargadorMensajes();
		Main mano = new Main();
		cargador.addCambioMensajesListener(mano);
		cargador.setListaMensajes(lista);
	}

	@Override
	public void nuevosMensajes(MensajesEvent ev) {
		System.out.println("Holaaa");
	}

}
