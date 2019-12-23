package vista;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelVerImagen extends JPanel{
	private JLabel imagen;
	
	public PanelVerImagen(){
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		imagen=new JLabel();
		add(imagen,BorderLayout.CENTER);
	}
	
	public void cambiarImagen(String nombre) {
		URL url = this.getClass().getResource(nombre);
		BufferedImage myPicture;
		try { 
			myPicture = ImageIO.read(url);			
			Image aux=myPicture.getScaledInstance(Constantes.x_size,Constantes.y_size, Image.SCALE_DEFAULT);
			imagen.setIcon(new ImageIcon(aux));
			imagen.repaint();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
