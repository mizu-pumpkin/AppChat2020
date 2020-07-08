package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalScrollButton;

public class Graphics {
	public final static int MIN_WIDTH = 300;
	public final static int MIN_HEIGHT = 200;
	
	public static final int SIZE_AVATAR_BIG = 100;
	public static final int SIZE_AVATAR_SMALL = 50;
	public static final int SIZE_ICON = 25;
	
	public static final Image DEFAULT_AVATAR = new ImageIcon(
			Graphics.class.getResource("/default.png")).getImage();
	public static final Image GROUP_AVATAR = new ImageIcon(
			Graphics.class.getResource("/defaultg.png")).getImage();
	
	public static final Color MAIN = new Color(206, 147, 216);//new Color(255, 235, 59);
	public static final Color DARK = new Color(100, 71, 105);
	public static final Color MEDIUM = new Color(144,102,151);
	public static final Color LIGHT = new Color(255, 210, 255);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color SECONDARY = WHITE;
	public static final Color BACKGROUND = LIGHT;//new Color(255, 224, 178);

	public static final String[] FORMAT_OPTIONS = { "IOS", "ANDROID 1", "ANDROID 2" };
	
	public static JButton makeButton(String txt) {
		JButton btn = new JButton(txt);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setBackground(Graphics.MAIN);
		btn.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED,
				WHITE, LIGHT, DARK, MEDIUM));
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
	
	public static ImageIcon makeGroupAvatar(int size) {
		return new ImageIcon(GROUP_AVATAR
				.getScaledInstance(size,size,java.awt.Image.SCALE_SMOOTH));
	}
	
	public static void buildGreeting(JTextArea txtArea, int x, int y) {
		txtArea.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtArea.setEditable(false);
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		txtArea.setSize(x, y);
		txtArea.setMinimumSize(new Dimension(x, y));
		txtArea.setMaximumSize(new Dimension(x, y));
		txtArea.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtArea.setBackground(Graphics.BACKGROUND);
	}
	
	public static void buildScroll(JScrollBar sb) {
		sb.setBackground(BACKGROUND);
		sb.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED, 
				WHITE, LIGHT, DARK, MEDIUM));
		
		MetalScrollButton btn = (MetalScrollButton) sb.getComponent(0);
		btn.setForeground(WHITE);
		
		sb.setUI(new BasicScrollBarUI() {
		    @Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Graphics.MAIN;
		        this.thumbDarkShadowColor = Graphics.DARK;
		        this.thumbHighlightColor = Graphics.WHITE;
		        this.thumbLightShadowColor = Graphics.LIGHT;
		    }
	        @Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        private JButton createZeroButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0));
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
		});
	}
	
	public static void buildToolbar(JToolBar tb) {
		tb.setFloatable(false);
		tb.setBackground(Graphics.MAIN);
		tb.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED,
				WHITE, LIGHT, DARK, MEDIUM));
	}

	public static void uimanager() {
		UIManager.getLookAndFeelDefaults().put("Panel.background", BACKGROUND);
		UIManager.put("OptionPane.background", BACKGROUND);
		UIManager.getLookAndFeelDefaults().put("Label.foreground", BLACK);
		UIManager.getLookAndFeelDefaults().put("List.foreground", BLACK);
		
		UIManager.getLookAndFeelDefaults().put("ComboBox.background", BACKGROUND);
		UIManager.getLookAndFeelDefaults().put("ComboBox.selectionBackground", MAIN);
		
		UIManager.getLookAndFeelDefaults().put("Button.background", MAIN);
		UIManager.getLookAndFeelDefaults().put("Button.darkShadow", DARK);
		UIManager.getLookAndFeelDefaults().put("Button.shadow", MEDIUM);
		UIManager.getLookAndFeelDefaults().put("Button.foreground", DARK);
		UIManager.getLookAndFeelDefaults().put("Button.select", MEDIUM);
		UIManager.getLookAndFeelDefaults().put("Button.highlight", WHITE);
		UIManager.getLookAndFeelDefaults().put("Button.light", LIGHT);

		UIManager.getLookAndFeelDefaults().put("ToolTip.background", DARK);
		UIManager.getLookAndFeelDefaults().put("ToolTip.foreground", LIGHT);
		
		UIManager.getLookAndFeelDefaults().put("Viewport.background", BACKGROUND);
	}
}
