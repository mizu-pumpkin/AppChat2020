package vista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import java.awt.Cursor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import tds.BubbleText;

@SuppressWarnings("serial")
public class VentanaAppChat extends JFrame implements ActionListener {

	private final static int MIN_WIDTH = 600;
	private final static int MIN_HEIGHT = 600;
	
	private JFrame frame;
	private JTextField txtWrite;
	private JTextField txtFindName;
	private JTextField txtFindMessage;
	private PanelChat panelChat;
	private JLabel lblAvatar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAppChat window = new VentanaAppChat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setVisible(boolean b) {
		VentanaAppChat window = new VentanaAppChat();
		window.frame.setVisible(b);
	}

	/**
	 * Create the application.
	 */
	public VentanaAppChat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })	//FIXME
	private void initialize() {
		BubbleText.noZoom();
		frame = new JFrame();
		frame.setTitle("AppChat");
		frame.setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		frame.getContentPane().add(splitPane);
		
		JPanel panel_izq = new JPanel();
		splitPane.setLeftComponent(panel_izq);
		GridBagLayout gbl_panel_izq = new GridBagLayout();
		gbl_panel_izq.columnWidths = new int[]{0, 0, 0};
		gbl_panel_izq.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_izq.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_izq.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_izq.setLayout(gbl_panel_izq);
		
/* User info */
		lblAvatar = new JLabel();
		lblAvatar.setIcon(new ImageIcon(BubbleText.getEmoji(1).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH)));
		//lblAvatar.setIcon(new ImageIcon(avatar));
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.gridheight = 2;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		panel_izq.add(lblAvatar, gbc_lblAvatar);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		panel_izq.add(lblUsername, gbc_lblUsername);
		
		JLabel lblGreeting = new JLabel("greeting");
		lblGreeting.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblGreeting.insets = new Insets(0, 0, 5, 0);
		gbc_lblGreeting.gridx = 1;
		gbc_lblGreeting.gridy = 1;
		panel_izq.add(lblGreeting, gbc_lblGreeting);
		
/* Contacts */
		JComboBox cb_selector = new JComboBox();
		cb_selector.setModel(new DefaultComboBoxModel(new String[] {"Chat", "Contactos", "Estados"}));
		GridBagConstraints gbc_cb_selector = new GridBagConstraints();
		gbc_cb_selector.gridwidth = 2;
		gbc_cb_selector.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_selector.insets = new Insets(0, 0, 5, 0);
		gbc_cb_selector.gridx = 0;
		gbc_cb_selector.gridy = 2;
		panel_izq.add(cb_selector, gbc_cb_selector);
		
		txtFindName = new JTextField();
		GridBagConstraints gbc_txtFindName = new GridBagConstraints();
		gbc_txtFindName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindName.gridwidth = 2;
		gbc_txtFindName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindName.gridx = 0;
		gbc_txtFindName.gridy = 3;
		panel_izq.add(txtFindName, gbc_txtFindName);
		txtFindName.setColumns(10);
		
		JScrollPane scrollPane_names = new JScrollPane();
		GridBagConstraints gbc_scrollPane_names = new GridBagConstraints();
		gbc_scrollPane_names.gridwidth = 2;
		gbc_scrollPane_names.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_names.gridx = 0;
		gbc_scrollPane_names.gridy = 4;
		panel_izq.add(scrollPane_names, gbc_scrollPane_names);

/* Right half */
		JPanel panel_der = new JPanel();
		splitPane.setRightComponent(panel_der);
		GridBagLayout gbl_panel_der = new GridBagLayout();
		gbl_panel_der.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_der.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_der.columnWeights = new double[]{4.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_der.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_der.setLayout(gbl_panel_der);

/* Search */
		txtFindMessage = new JTextField();
		GridBagConstraints gbc_txtFindMessage = new GridBagConstraints();
		gbc_txtFindMessage.gridwidth = 2;
		gbc_txtFindMessage.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindMessage.gridx = 1;
		gbc_txtFindMessage.gridy = 0;
		panel_der.add(txtFindMessage, gbc_txtFindMessage);
		txtFindMessage.setColumns(10);

/* Chat panel */
		panelChat = new PanelChat();
		
		JScrollPane scrollPane_chat = new JScrollPane(panelChat);
		GridBagConstraints gbc_scrollPane_chat = new GridBagConstraints();
		gbc_scrollPane_chat.gridwidth = 3;
		gbc_scrollPane_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_chat.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_chat.gridx = 0;
		gbc_scrollPane_chat.gridy = 1;
		panel_der.add(scrollPane_chat, gbc_scrollPane_chat);

/* Text input */
		txtWrite = new JTextField();
		txtWrite.setToolTipText("");
		txtWrite.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		GridBagConstraints gbc_txtWrite = new GridBagConstraints();
		gbc_txtWrite.gridwidth = 2;
		gbc_txtWrite.insets = new Insets(0, 0, 0, 5);
		gbc_txtWrite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWrite.gridx = 0;
		gbc_txtWrite.gridy = 2;
		panel_der.add(txtWrite, gbc_txtWrite);
		txtWrite.setColumns(10);
		
		JLabel lblEmoticon = new JLabel();
		lblEmoticon.setIcon(new ImageIcon(BubbleText.getEmoji(6).getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_lblEmoticon = new GridBagConstraints();
		gbc_lblEmoticon.gridx = 2;
		gbc_lblEmoticon.gridy = 2;
		panel_der.add(lblEmoticon, gbc_lblEmoticon);
	}
	
	public void showAvatar(String nombre) {
		URL url = this.getClass().getResource(nombre);
		BufferedImage myPicture;
		try { 
			myPicture = ImageIO.read(url);			
			Image aux=myPicture.getScaledInstance(MIN_WIDTH, MIN_HEIGHT, Image.SCALE_DEFAULT);
			lblAvatar.setIcon(new ImageIcon(aux));
			lblAvatar.repaint();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
