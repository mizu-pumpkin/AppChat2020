package vista;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JTextField;

import controlador.AppChat;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	
	private final Usuario loggedUser;
	
	private PanelChat panelChat;
	private PanelListaChats panelList;
	
	private JPanel contentPane;
	
	private JTextField fieldWriteMsg;
	private JTextField fieldFindUser;
	private JTextField fieldFindMessage;
	private JButton btnAvatar;
	private JButton btnEmoticon;

	public VentanaAppChat(Usuario user) {
		this.loggedUser = user;
		panelChat = new PanelChat(user.getUsername());
		panelList = new PanelListaChats(panelChat, user.getChats());
		initialize();
		setVisible(true);
	}

	private void initialize() {
		BubbleText.noZoom();
		setTitle("AppChat");
		setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.2);
		contentPane.add(splitPane);
		
		JPanel panel_izq = new JPanel();
		splitPane.setLeftComponent(panel_izq);
		configurarPanelIzquierdo(panel_izq);
		
		JPanel panel_der = new JPanel();
		splitPane.setRightComponent(panel_der);
		configurarPanelDerecho(panel_der);
	}
	
	public void configurarPanelIzquierdo(JPanel panel_izq) {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_izq.setLayout(gbl);
		
		configurarInfoUsuario(panel_izq);
		configurarListaContactos(panel_izq);
	}

	public void configurarInfoUsuario(JPanel panel) {
		btnAvatar = new JButton(); //FIXME
		btnAvatar.addActionListener(this);
		btnAvatar.setIcon(new ImageIcon(BubbleText.getEmoji(2).getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH))); //FIXME
		btnAvatar.setFocusPainted(false);
		btnAvatar.setMargin(new Insets(0, 0, 0, 0));
		btnAvatar.setContentAreaFilled(false);
		btnAvatar.setBorderPainted(false);
		btnAvatar.setOpaque(false);
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.gridheight = 2;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		panel.add(btnAvatar, gbc_lblAvatar);
		
		JLabel lblUsername = new JLabel(loggedUser.getUsername());
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		panel.add(lblUsername, gbc_lblUsername);
		
		JLabel lblGreeting = new JLabel(loggedUser.getGreeting());
		lblGreeting.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblGreeting.insets = new Insets(0, 0, 5, 0);
		gbc_lblGreeting.gridx = 1;
		gbc_lblGreeting.gridy = 1;
		panel.add(lblGreeting, gbc_lblGreeting);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })//FIXME
	private void configurarListaContactos(JPanel panel) {
/* Contacts */
		JComboBox cb_selector = new JComboBox();
		cb_selector.setModel(new DefaultComboBoxModel(new String[] {"Chats", "Contactos", "Estados"}));
		GridBagConstraints gbc_cb_selector = new GridBagConstraints();
		gbc_cb_selector.gridwidth = 2;
		gbc_cb_selector.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_selector.insets = new Insets(0, 0, 5, 0);
		gbc_cb_selector.gridx = 0;
		gbc_cb_selector.gridy = 2;
		panel.add(cb_selector, gbc_cb_selector);
		
		fieldFindUser = new JTextField();
		fieldFindUser.setColumns(10);
		fieldFindUser.addActionListener(this);
		GridBagConstraints gbc_txtFindName = new GridBagConstraints();
		gbc_txtFindName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindName.gridwidth = 2;
		gbc_txtFindName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindName.gridx = 0;
		gbc_txtFindName.gridy = 3;
		panel.add(fieldFindUser, gbc_txtFindName);

/* Contact List panel */
		GridBagConstraints gbc_scrollPane_names = new GridBagConstraints();
		gbc_scrollPane_names.gridwidth = 2;
		gbc_scrollPane_names.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_names.gridx = 0;
		gbc_scrollPane_names.gridy = 4;
		panel.add(new JScrollPane(panelList), gbc_scrollPane_names);
	}

	private void configurarPanelDerecho(JPanel panel) {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0, 0, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0};
		gbl.columnWeights = new double[]{4.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl);
		
/* Search */
		fieldFindMessage = new JTextField();
		fieldFindMessage.setColumns(10);
		GridBagConstraints gbc_txtFindMessage = new GridBagConstraints();
		gbc_txtFindMessage.gridwidth = 2;
		gbc_txtFindMessage.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindMessage.gridx = 1;
		gbc_txtFindMessage.gridy = 0;
		panel.add(fieldFindMessage, gbc_txtFindMessage);

/* Chat panel */
		GridBagConstraints gbc_scrollPane_chat = new GridBagConstraints();
		gbc_scrollPane_chat.gridwidth = 3;
		gbc_scrollPane_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_chat.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_chat.gridx = 0;
		gbc_scrollPane_chat.gridy = 1;
		panel.add(new JScrollPane(panelChat), gbc_scrollPane_chat);

/* Text input */
		fieldWriteMsg = new JTextField();
		fieldWriteMsg.setColumns(10);
		fieldWriteMsg.addActionListener(this);
		//txtWriteMsg.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		GridBagConstraints gbc_txtWrite = new GridBagConstraints();
		gbc_txtWrite.gridwidth = 2;
		gbc_txtWrite.insets = new Insets(0, 0, 0, 5);
		gbc_txtWrite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWrite.gridx = 0;
		gbc_txtWrite.gridy = 2;
		panel.add(fieldWriteMsg, gbc_txtWrite);
		
		btnEmoticon = new JButton();
		btnEmoticon.addActionListener(this);
		btnEmoticon.setIcon(new ImageIcon(BubbleText.getEmoji(6).getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH)));
		GridBagConstraints gbc_lblEmoticon = new GridBagConstraints();
		gbc_lblEmoticon.gridx = 2;
		gbc_lblEmoticon.gridy = 2;
		panel.add(btnEmoticon, gbc_lblEmoticon);
	}
	
	public void showAvatar(String nombre) {//TODO: entender como usar
		URL url = this.getClass().getResource(nombre);
		BufferedImage myPicture;
		try { 
			myPicture = ImageIO.read(url);			
			Image aux=myPicture.getScaledInstance(MIN_WIDTH, MIN_HEIGHT, Image.SCALE_DEFAULT);
			btnAvatar.setIcon(new ImageIcon(aux));
			btnAvatar.repaint();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAvatar) {
			new VentanaPerfil(loggedUser);
			return;
		}
		if (e.getSource() == fieldWriteMsg) {
			panelChat.sendText(fieldWriteMsg.getText());
			fieldWriteMsg.setText("");
			return;
		}
		if (e.getSource() == fieldFindUser) {
			Usuario user = AppChat.getInstance().findUser(fieldFindUser.getText());
			AppChat.getInstance().registerContact(user);
			fieldFindUser.setText("");
			panelList = new PanelListaChats(panelChat, loggedUser.getChats());
			panelList.repaint();
			return;
		}
	}

}
