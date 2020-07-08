package whatsapp.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import whatsapp.parser.SimpleTextParser;

import java.io.IOException;
import java.io.Serializable;

public class CargadorMensajes implements Serializable {
	
	private static final long serialVersionUID = -51311273675689991L;
	
	private static final String FORMATO_IOS = "d/M/yy H:mm:ss";
	private static final String FORMATO_ANDROID_1 = "d/M/yy H:mm";
	private static final String FORMATO_ANDROID_2 = "d/M/yyyy H:mm";
	
	private static final String[] FORMATOS_CADENAS = { FORMATO_IOS, FORMATO_ANDROID_1, FORMATO_ANDROID_2 };
	
	// Propiedades
	private List<MensajeWhatsApp> listaMensajes;
	
	// Atributos
	private Vector<MensajesListener> listaMensajesListener;
	
	public CargadorMensajes() {
		this.listaMensajes = new ArrayList<>();
		this.listaMensajesListener = new Vector<>();
	}
	
	public synchronized void addCambioMensajesListener(MensajesListener l) {
		listaMensajesListener.addElement(l);
	}
	
	public synchronized void removeCambioMensajesListener(MensajesListener l) {
		listaMensajesListener.removeElement(l);
	}

	public List<MensajeWhatsApp> getListaMensajes() {
		return new ArrayList<>(listaMensajes);
	}
	
	public void setListaMensajes(List<MensajeWhatsApp> nuevaListaMensajes) {
		List<MensajeWhatsApp> anteListaMensajes = listaMensajes;
		this.listaMensajes = new ArrayList<>(nuevaListaMensajes);
		if (!anteListaMensajes.equals(nuevaListaMensajes)) {
			MensajesEvent ev = new MensajesEvent(this, anteListaMensajes, nuevaListaMensajes);
			notificarCambioListaMensajes(ev);
		}
	}
	
	public void setFichero(String rutaFichero, int formatoFecha) {
		List<MensajeWhatsApp> chat = null;
		Plataforma pt = getPlataforma(formatoFecha);
		try {
			chat = SimpleTextParser.parse(rutaFichero, FORMATOS_CADENAS[formatoFecha], pt);
			setListaMensajes(chat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Plataforma getPlataforma(int formatoFecha) {
		return (formatoFecha == 0 ? Plataforma.IOS : Plataforma.ANDROID);
	}
	
	@SuppressWarnings({ "unchecked" })
	private void notificarCambioListaMensajes(MensajesEvent ev) {
		Vector<MensajesListener> lista;
		synchronized (this) {
			lista = (Vector<MensajesListener>) listaMensajesListener.clone();
		}
		for (MensajesListener ml : lista)
			ml.nuevosMensajes(ev);
	}
}
