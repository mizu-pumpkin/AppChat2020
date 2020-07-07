package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Graphics {
	public final static int MIN_WIDTH = 300;
	public final static int MIN_HEIGHT = 200;
	
	public static final int SIZE_AVATAR_BIG = 100;
	public static final int SIZE_AVATAR_SMALL = 50;
	public static final int SIZE_ICON = 25;
	
	public static final Image DEFAULT_AVATAR = new ImageIcon(
			Graphics.class.getResource("/default.png")).getImage();
	
	public static final Color MAIN = new Color(255, 153, 255);
	public static final Color SECONDARY = new Color(255, 255, 255);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	public static JButton makeButton(String txt) {
		JButton btn = new JButton(txt);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
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

	public static JButton makeIconButton(String src) {
		return makeImageButton(new ImageIcon(Graphics.class.getResource(src)), SIZE_ICON);
	}

	public static JButton makeAvatarButton(String src) {
		return makeImageButton(makeAvatar(src, SIZE_AVATAR_BIG), SIZE_AVATAR_BIG);
	}
	
	public static void reloadAvatarButton(JButton btn, String url) {
		ImageIcon img = makeAvatar(url, SIZE_AVATAR_BIG);
		btn.setIcon(img);
		btn.repaint();
	}
	
	public static ImageIcon makeAvatar(String url, int size) {
		if (url.isEmpty())
			return new ImageIcon(DEFAULT_AVATAR
					.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
		try {
			return new ImageIcon(ImageIO.read(new File(url))
				.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
		} catch (IOException e) {
			return new ImageIcon(DEFAULT_AVATAR
					.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
		}
	}
}
