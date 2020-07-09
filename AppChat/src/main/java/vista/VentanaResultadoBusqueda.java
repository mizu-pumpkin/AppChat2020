package vista;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import modelo.Mensaje;

@SuppressWarnings("serial")
public class VentanaResultadoBusqueda extends JFrame {
	
	private String nombreChat;
	private List<Mensaje> listadoMensajes;
	
	public VentanaResultadoBusqueda(String nombreChat, String nombreUsuario, List<Mensaje> listadoMensajes) {
		this.nombreChat = nombreChat;
		this.listadoMensajes = listadoMensajes;
		initialize();
		setVisible(true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		setTitle("Mensajes encontrados - \""+ nombreChat + "\"");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(Graphics.MIN_WIDTH, Graphics.MIN_HEIGHT));
		
		String[] data = new String[listadoMensajes.size()];
		Iterator<Mensaje> it = listadoMensajes.iterator();
		for (int i = 0; it.hasNext(); i++)
			data[i] = it.next().toString();
		
		JScrollPane scroll = new JScrollPane(new JList(data));
		Graphics.buildScroll(scroll.getHorizontalScrollBar());
		Graphics.buildScroll(scroll.getVerticalScrollBar());
		add(scroll);
	}
}
