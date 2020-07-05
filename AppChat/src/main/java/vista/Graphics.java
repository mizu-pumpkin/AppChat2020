package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Graphics {
	public static final Color MAIN = new Color(255, 153, 255);
	public static final Color SECONDARY = new Color(255, 255, 255);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	public static JButton makeImageButton(ImageIcon img) {
		JButton btn = new JButton();
		btn.setFocusPainted(false);
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.setOpaque(false);
		btn.setIcon(img);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	public static JButton makeButton(String txt) {
		JButton btn = new JButton(txt);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}