package vista;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JTextField;

import controlador.AppChat;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import tds.BubbleText;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class VentanaAppChat extends JFrame implements ActionListener {

	private final static int MIN_WIDTH = 600;
	private final static int MIN_HEIGHT = 600;
	
	private final Usuario loggedUser;
	
	private PanelChat panelChat;
	private PanelListaChats panelList;
	
	private JPanel contentPane;
	
	private JTextField txtWriteMsg;
	private JTextField txtFindUser;
	private JTextField txtFindMessage;
	private JButton btnAvatar;
	private JButton btnEmoji;
	private JButton btnNewGroup;
	private JPanel panel_izq_1;
	private JPopupMenu popupMenuEmoji;

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
		
		panel_izq_1 = new JPanel();
		splitPane.setLeftComponent(panel_izq_1);
		configurarPanelIzquierdo(panel_izq_1);
		
		JPanel panel_der = new JPanel();
		splitPane.setRightComponent(panel_der);
		configurarPanelDerecho(panel_der);
	}
	
	public void configurarPanelIzquierdo(JPanel panel_izq) {
		GridBagLayout gbl_panel_izq_1 = new GridBagLayout();
		gbl_panel_izq_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_izq_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_izq_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_izq_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_izq.setLayout(gbl_panel_izq_1);
		
		configurarInfoUsuario(panel_izq);
		configurarListaContactos(panel_izq);
	}

	public void configurarInfoUsuario(JPanel panel) {
/* Avatar */
		btnAvatar = new JButton(); //TODO
		btnAvatar.addActionListener(this);
		showAvatar(loggedUser.getAvatar());
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
/* Username */
		JLabel lblUsername = new JLabel(loggedUser.getUsername());
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		panel.add(lblUsername, gbc_lblUsername);
/* Greeting */
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
		cb_selector.setModel(new DefaultComboBoxModel(new String[] {"Chats", "Estados"}));
		GridBagConstraints gbc_cb_selector = new GridBagConstraints();
		gbc_cb_selector.gridwidth = 2;
		gbc_cb_selector.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_selector.insets = new Insets(0, 0, 5, 0);
		gbc_cb_selector.gridx = 0;
		gbc_cb_selector.gridy = 2;
		panel.add(cb_selector, gbc_cb_selector);
/* Search */
		txtFindUser = new JTextField();
		txtFindUser.setColumns(10);
		txtFindUser.addActionListener(this);
		
		btnNewGroup = new JButton("+");
		btnNewGroup.addActionListener(this);
		GridBagConstraints gbc_btnNewGroup = new GridBagConstraints();
		gbc_btnNewGroup.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewGroup.gridx = 0;
		gbc_btnNewGroup.gridy = 3;
		panel_izq_1.add(btnNewGroup, gbc_btnNewGroup);
		GridBagConstraints gbc_txtFindName = new GridBagConstraints();
		gbc_txtFindName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindName.gridx = 1;
		gbc_txtFindName.gridy = 3;
		panel.add(txtFindUser, gbc_txtFindName);
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
/* Search message */
		txtFindMessage = new JTextField();
		txtFindMessage.setColumns(10);
		GridBagConstraints gbc_txtFindMessage = new GridBagConstraints();
		gbc_txtFindMessage.gridwidth = 2;
		gbc_txtFindMessage.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindMessage.gridx = 1;
		gbc_txtFindMessage.gridy = 0;
		panel.add(txtFindMessage, gbc_txtFindMessage);
/* Chat panel */
		GridBagConstraints gbc_scrollPane_chat = new GridBagConstraints();
		gbc_scrollPane_chat.gridwidth = 3;
		gbc_scrollPane_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_chat.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_chat.gridx = 0;
		gbc_scrollPane_chat.gridy = 1;
		panel.add(new JScrollPane(panelChat), gbc_scrollPane_chat);
/* Text input */
		txtWriteMsg = new JTextField();
		txtWriteMsg.setColumns(10);
		txtWriteMsg.addActionListener(this);
		GridBagConstraints gbc_txtWrite = new GridBagConstraints();
		gbc_txtWrite.gridwidth = 2;
		gbc_txtWrite.insets = new Insets(0, 0, 0, 5);
		gbc_txtWrite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtWrite.gridx = 0;
		gbc_txtWrite.gridy = 2;
		panel.add(txtWriteMsg, gbc_txtWrite);
		
		configurarBotonEmoji();
		GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
		gbc_btnEmoji.gridx = 2;
		gbc_btnEmoji.gridy = 2;
		panel.add(btnEmoji, gbc_btnEmoji);
	}
	
	private void configurarBotonEmoji() {
		ActionListener popupListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelChat.sendEmoji(loggedUser.getUsername(), Integer.parseInt(e.getActionCommand()));
			}
		};
		
		popupMenuEmoji = new JPopupMenu();
		popupMenuEmoji.setLayout(new GridLayout(5, 5, 0, 0));//5x5 porque BubbleText.MAXICONO==25
		popupMenuEmoji.setCursor(new Cursor(Cursor.HAND_CURSOR));
		for (int i=0; i<=BubbleText.MAXICONO; i++) {
			JMenuItem emoji = new JMenuItem(BubbleText.getEmoji(i));
			emoji.addActionListener(popupListener);
			popupMenuEmoji.add(emoji).setActionCommand(""+i);
		}
		
		btnEmoji = new JButton();
		btnEmoji.addActionListener(this);
		btnEmoji.setFocusPainted(false);
		btnEmoji.setMargin(new Insets(0, 0, 0, 0));
		btnEmoji.setContentAreaFilled(false);
		btnEmoji.setBorderPainted(false);
		btnEmoji.setOpaque(false);
		btnEmoji.setIcon(new ImageIcon(
			BubbleText.getEmoji(0)
			.getImage()
			.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH))
		);
	}
	
	// Dado una ruta almacenada en "nombre", establece la imagen apuntada en esa ruta
	// como nuevo icono de "btnAvatar".
	public void showAvatar(String nombre) {
		File fichero = new File(nombre);
		BufferedImage myPicture;
		try { 
			myPicture = ImageIO.read(fichero);			
			Image aux=myPicture.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
			btnAvatar.setIcon(new ImageIcon(aux));
			btnAvatar.repaint();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAvatar) {
			new VentanaPerfil(loggedUser);
			showAvatar(loggedUser.getAvatar());
			return;
		}
		if (e.getSource() == btnNewGroup) {
			new VentanaNuevoGrupo(loggedUser);
			return;
		}
		if (e.getSource() == btnEmoji) {
			//Se llama show() dos veces porque la primera no sabe la altura del menu
			popupMenuEmoji.show(btnEmoji, 0, 0);
			popupMenuEmoji.show(btnEmoji, 0, -popupMenuEmoji.getHeight());
			return;
		}
		if (e.getSource() == txtWriteMsg) {
			panelChat.sendText(loggedUser.getUsername(), txtWriteMsg.getText());
			txtWriteMsg.setText("");
			return;
		}
		if (e.getSource() == txtFindUser) {
			Usuario user = AppChat.getInstance().findUser(txtFindUser.getText());
			txtFindUser.setText("");
			if (AppChat.getInstance().registerContact(user)) {
				panelList.loadList(loggedUser.getChats());
				JOptionPane.showMessageDialog(
					this,
					user.getUsername() +" añadido correctamente.",
					"New contact success",
					JOptionPane.INFORMATION_MESSAGE
				);
			}
			return;
		}
	}
}
