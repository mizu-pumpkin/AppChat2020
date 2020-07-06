package vista;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Usuario;

@SuppressWarnings("serial")
public class VentanaEstadisticas extends JFrame {

	private Usuario user;
	
	private JPanel contentPane;

	public VentanaEstadisticas(Usuario user) {
		this.user = user;
		setTitle("Estadísticas de uso");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setVisible(true);
	}

}
