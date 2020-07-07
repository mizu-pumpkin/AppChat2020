package vista;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.AppChat;
import luz.*;
import modelo.Chat;
import modelo.Usuario;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import tds.BubbleText;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class VentanaAppChat extends JFrame implements ActionListener, IEncendidoListener {

	private final static int MIN_WIDTH = 600;
	private final static int MIN_HEIGHT = 600;
	
	private final Usuario loggedUser;
	
	private PanelChat panelChat;
	private PanelListaChats listaChats;
	
	private JPanel contentPane;
	
	private JTextField txtWriteMsg;
	private JTextField txtFindUser;
	private JLabel lblGreeting;
	private JPanel panel_izq;
	private JPanel panel_der;
	private JPopupMenu popupMenuEmoji;
	private JToolBar toolbar;
	private JButton btnFindMessage;
	private JButton btnAvatar;
	private JButton btnEmoji;
	private JButton btnNewGroup;
	private JButton btnPremium;
	private JButton btnStats;
	private JButton btnContacts;
	private JButton btnDeleteChat;
	private Luz btnLuz;

	public VentanaAppChat(Usuario user) {
		this.loggedUser = user;
		this.panelChat = new PanelChat(user.getUsername());
		this.listaChats = new PanelListaChats(panelChat,user);
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
		
		panel_izq = new JPanel();
		splitPane.setLeftComponent(panel_izq);
		configurarPanelIzquierdo();
		
		panel_der = new JPanel();
		splitPane.setRightComponent(panel_der);
		configurarPanelDerecho();
	}
	
	public void configurarPanelIzquierdo() {
		GridBagLayout gbl_panel_izq_1 = new GridBagLayout();
		gbl_panel_izq_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_izq_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_izq_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_izq_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_izq.setLayout(gbl_panel_izq_1);
		
		configurarInfoUsuario();
		configurarToolbar();
		configurarListaContactos();
	}

	public void configurarInfoUsuario() {
/* Avatar */
		btnAvatar = Graphics.makeAvatarButton(loggedUser.getAvatar());
		btnAvatar.setToolTipText("Mi perfil");
		btnAvatar.addActionListener(this);

		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.gridheight = 2;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 0;
		gbc_lblAvatar.gridy = 0;
		panel_izq.add(btnAvatar, gbc_lblAvatar);
/* Username */
		JLabel lblUsername = new JLabel(loggedUser.getUsername());
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		panel_izq.add(lblUsername, gbc_lblUsername);
/* Greeting */
		lblGreeting = new JLabel(loggedUser.getGreeting());
		lblGreeting.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblGreeting.insets = new Insets(0, 0, 5, 0);
		gbc_lblGreeting.gridx = 1;
		gbc_lblGreeting.gridy = 1;
		panel_izq.add(lblGreeting, gbc_lblGreeting);
	}
	
	private void configurarToolbar() {
		toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		panel_izq.add(toolbar, gbc_panel);

		btnNewGroup = Graphics.makeIconButton("/newgroup.png");
		btnNewGroup.setToolTipText("Crear nuevo grupo");
		btnNewGroup.addActionListener(this);
		toolbar.add(btnNewGroup);
		
		btnContacts = Graphics.makeIconButton("/contacts.png");
		btnContacts.setToolTipText("Lista de contactos");
		btnContacts.addActionListener(this);
		toolbar.add(btnContacts);

		btnPremium = Graphics.makeIconButton("/premium.png");
		btnPremium.setToolTipText("Convertirse en usuario premium");
		btnPremium.addActionListener(this);
		toolbar.add(btnPremium);
/* Botones premium */
		btnStats = Graphics.makeIconButton("/stats.png");
		btnStats.setToolTipText("Estadísticas de uso");
		btnStats.addActionListener(this);
		toolbar.add(btnStats);
		if (!loggedUser.isPremium()) {
			btnStats.setVisible(false);
		}
	}
	
	private void configurarListaContactos() {
/* Search */
		txtFindUser = new JTextField();
		txtFindUser.setColumns(10);
		txtFindUser.addActionListener(this);
		GridBagConstraints gbc_txtFindName = new GridBagConstraints();
		gbc_txtFindName.gridwidth = 2;
		gbc_txtFindName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFindName.insets = new Insets(0, 0, 5, 0);
		gbc_txtFindName.gridx = 0;
		gbc_txtFindName.gridy = 3;
		panel_izq.add(txtFindUser, gbc_txtFindName);
/* Contact List panel */
		JScrollPane scroll = new JScrollPane(listaChats);
		GridBagConstraints gbc_scrollPane_names = new GridBagConstraints();
		gbc_scrollPane_names.gridwidth = 2;
		gbc_scrollPane_names.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_names.gridx = 0;
		gbc_scrollPane_names.gridy = 4;
		panel_izq.add(scroll, gbc_scrollPane_names);
	}

	private void configurarPanelDerecho() {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0};
		gbl.columnWeights = new double[]{4.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_der.setLayout(gbl);
/* Search message */
		JToolBar toolbarChat = new JToolBar();
		toolbarChat.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolbarChat.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.fill = GridBagConstraints.BOTH;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		panel_der.add(toolbarChat, gbc_toolBar);
		
		btnDeleteChat = Graphics.makeIconButton("/trash.png");
		btnDeleteChat.setToolTipText("Eliminar chat");
		btnDeleteChat.addActionListener(this);
		toolbarChat.add(btnDeleteChat);
		
		btnFindMessage = Graphics.makeIconButton("/searchmsg.png");
		btnFindMessage.setToolTipText("Buscar mensajes");
		btnFindMessage.addActionListener(this);
		toolbarChat.add(btnFindMessage);
		
/* Luz button */
		
		btnLuz = new Luz();
		btnLuz.addEncendidoListener(this);
		toolbarChat.add(btnLuz);
		
/* Chat panel */
		GridBagConstraints gbc_scrollPane_chat = new GridBagConstraints();
		gbc_scrollPane_chat.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_chat.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_chat.gridx = 0;
		gbc_scrollPane_chat.gridy = 1;
		panel_der.add(new JScrollPane(panelChat), gbc_scrollPane_chat);
/* Text input */
		JPanel panel_writeMsg = new JPanel();
		panel_writeMsg.setLayout(new BoxLayout(panel_writeMsg, BoxLayout.X_AXIS));
		GridBagConstraints gbc_write = new GridBagConstraints();
		gbc_write.fill = GridBagConstraints.BOTH;
		gbc_write.gridx = 0;
		gbc_write.gridy = 2;
		panel_der.add(panel_writeMsg, gbc_write);
		
		txtWriteMsg = new JTextField();
		txtWriteMsg.setColumns(10);
		txtWriteMsg.addActionListener(this);
		panel_writeMsg.add(txtWriteMsg);
		
		configurarBotonEmoji();
		panel_writeMsg.add(btnEmoji);
	}
	
	private void configurarBotonEmoji() {
		ActionListener popupListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelChat.sendEmoji(Integer.parseInt(e.getActionCommand()));
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
		
		btnEmoji = Graphics.makeIconButton("/emoji.png");
		btnEmoji.setToolTipText("Enviar emoji");
		btnEmoji.addActionListener(this);
	}
	
	public void reloadAvatar() {
		Graphics.showAvatar(btnAvatar, loggedUser.getAvatar());
	}
	
	public void reloadGreeting() {
		lblGreeting.setText(loggedUser.getGreeting());
	}
	
	private void mostrarBotonesPremium(boolean b) {
		btnStats.setVisible(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAvatar) {
			VentanaPerfil v = new VentanaPerfil(loggedUser);
			v.setVAC(this);
			return;
		}
		if (e.getSource() == btnNewGroup) {
			new VentanaEditorGrupo(loggedUser, listaChats);
			return;
		}
		if (e.getSource() == btnPremium) {//TODO
			AppChat.getInstance().togglePremium();
			mostrarBotonesPremium(loggedUser.isPremium());
			return;
		}
		if (e.getSource() == btnContacts) {//TODO
			new VentanaListaContactos(loggedUser);
			return;
		}
		if (e.getSource() == btnStats) {//TODO
			new VentanaEstadisticas(loggedUser);
			return;
		}
		if (e.getSource() == btnEmoji) {
			//NOTE: Se llama show() dos veces porque la primera vez
			//aún no sabe la altura del menu
			popupMenuEmoji.show(btnEmoji, 0, 0);
			popupMenuEmoji.show(btnEmoji, 0, -popupMenuEmoji.getHeight());
			return;
		}
		if (e.getSource() == txtWriteMsg) {
			panelChat.sendText(txtWriteMsg.getText());
			txtWriteMsg.setText("");
			return;
		}
		if (e.getSource() == btnDeleteChat) {
			Chat chat = panelChat.removeActualChat();
			AppChat.getInstance().deleteChat(chat);
			listaChats.remove(chat);
		}
		if (e.getSource() == btnFindMessage) {
			if (panelChat.hasChat())
				new VentanaBusquedaMensaje(panelChat.getChat(), loggedUser.getUsername());
			return;
		}
		if (e.getSource() == txtFindUser) {
			Usuario user = AppChat.getInstance().findUser(txtFindUser.getText());
			if (user == null) {
				JOptionPane.showMessageDialog(
					this,
					"El usuario " + txtFindUser.getText() +" no existe.",
					"User doesn't exist",
					JOptionPane.ERROR_MESSAGE
				);
			} else if (user.equals(loggedUser)) {
				new VentanaPerfil(loggedUser);
			} else if (loggedUser.knowsUser(user)) {
				new VentanaEditorContacto(loggedUser.getPrivateChat(user), listaChats);
			} else {
				new VentanaEditorContacto(user, listaChats);
			}
			txtFindUser.setText("");
			return;
		}
	}

	@Override
	public void enteradoCambioEncendido(EventObject ev) {
		if (ev.getSource() == btnLuz) {
			EncendidoEvent ecEv = (EncendidoEvent) ev;
			if (ecEv.getNewEncendido()) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT con chat", "txt");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					returnVal = JOptionPane.showOptionDialog(null, 
							"¿Cuál es el formato?", 
							"Importar chat", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE, 
							null, 
							Graphics.FORMAT_OPTIONS, 
							null);
					AppChat.getInstance().readFileChat(chooser.getSelectedFile(), returnVal);
				}
				return;
			}
		}
	}
}
