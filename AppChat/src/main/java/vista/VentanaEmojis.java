package vista;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tds.BubbleText;

@SuppressWarnings("serial")
public class VentanaEmojis extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		VentanaEmojis frame = new VentanaEmojis();
		frame.setVisible(true);
	}

	public VentanaEmojis() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 250, 260);
		setResizable(false);
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		for (int i=0; i<=BubbleText.MAXICONO; i++) {
			JLabel label = new JLabel();
			label.setIcon(BubbleText.getEmoji(i));
			contentPane.add(label);
		}
	}

}
