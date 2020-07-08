package vista;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelo.Mensaje;

@SuppressWarnings("serial")
public class VentanaResultadoBusqueda extends JFrame {
	
	private final static int MIN_WIDTH = 400;
	private final static int MIN_HEIGHT = 400;
	
	private JPanel contentPane;
	
	private String nombreChat;
	private String nombreUsuario;
	private List<Mensaje> listadoMensajes;
	
	public VentanaResultadoBusqueda(String nombreChat, String nombreUsuario, List<Mensaje> listadoMensajes) {
		this.nombreChat = nombreChat;
		this.nombreUsuario = nombreUsuario;
		this.listadoMensajes = listadoMensajes;
		initialize();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Mensajes encontrados - \""+ nombreChat + "\"");
		setSize(MIN_WIDTH, MIN_HEIGHT);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		contentPane = (JPanel) getContentPane();
		//contentPane.setBackground(Graphics.BACKGROUND);
		
		PanelChat panelChat = new PanelChat(nombreUsuario);
		contentPane.add(new JScrollPane(panelChat));
		panelChat.loadChat(listadoMensajes);
	}
}
