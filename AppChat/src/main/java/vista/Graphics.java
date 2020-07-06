package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Graphics {
	public static final Color MAIN = new Color(255, 153, 255);
	public static final Color SECONDARY = new Color(255, 255, 255);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	public static JButton makeButton(String txt) {
		JButton btn = new JButton(txt);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	public static JButton makeIconButton(ImageIcon icon) {
		return makeImageButton(icon, 25);
	}
	
	public static JButton makeImageButton(ImageIcon img) {
		return makeImageButton(img, 100);
	}
	
	public static JButton makeImageButton(ImageIcon img, int size) {
		JButton btn = new JButton(new ImageIcon(img.getImage()
			.getScaledInstance(size,size,java.awt.Image.SCALE_SMOOTH))
		);
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setOpaque(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	public static void showAvatar(JButton btn, String url) {
		if (url.isEmpty())
			return;
		File fichero = new File(url);
		try { 
			BufferedImage img = ImageIO.read(fichero);
			btn.setIcon(new ImageIcon(
				img.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)
			));
			btn.repaint();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
