package vista;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;

import controlador.AppChat;

import javax.swing.filechooser.FileNameExtensionFilter;

import luz.*;
import modelo.Chat;
import modelo.ChatGrupo;
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
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class VentanaAppChat extends JFrame implements ActionListener, IEncendidoListener {
	
	private static final AppChat appChat = AppChat.getInstance();
	
	private final Usuario loggedUser;
	
	private PanelChat panelChat;
	private PanelListaChats listaChats;
	
	private JPanel contentPane;
	
	private JTextField txtWriteMsg;
	private JTextField txtFindUser;
	private JTextArea lblGreeting;
	private JPanel panel_izq;
	private JPanel panel_der;
	private JPopupMenu popupMenuEmoji;
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
		this.panelChat = new PanelChat();
		this.listaChats = new PanelListaChats(panelChat,user);
		initialize(); 
		setVisible(true);
	}

	private void initialize() {
		BubbleText.noZoom();
		setTitle("AppChat");
		setSize(800, 600);
		setMinimumSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.1);
		splitPane.setDividerSize(2);
		contentPane.add(splitPane);
		
		panel_izq = new JPanel();
		panel_izq.setMinimumSize(new Dimension(200, 0));
		splitPane.setLeftComponent(panel_izq);
		configurarPanelIzquierdo();
		
		panel_der = new JPanel();
		panel_der.setMinimumSize(new Dimension(200, 0));
		splitPane.setRightComponent(panel_der);
		configurarPanelDerecho();
	}
	
	private void configurarPanelIzquierdo() {
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0};
		gbl.columnWeights = new double[]{0.0, 1.0};
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		panel_izq.setLayout(gbl);
		
		configurarInfoUsuario();
		configurarToolbar();
		configurarListaContactos();
	}

	private void configurarInfoUsuario() {
/* Avatar */
		btnAvatar = Graphics.makeAvatarButton(loggedUser.getAvatar());
		btnAvatar.setToolTipText("Mi perfil");
		btnAvatar.addActionListener(this);

		GridBagConstraints gbc_avatar = new GridBagConstraints();
		gbc_avatar.insets = new Insets(0, 0, 0, 0);
		gbc_avatar.gridx = 0;
		gbc_avatar.gridy = 0;
		panel_izq.add(btnAvatar, gbc_avatar);
/* Username */
		JPanel panel_info = new JPanel();
		GridBagConstraints gbc_info = new GridBagConstraints();
		gbc_info.fill = GridBagConstraints.BOTH;
		gbc_info.insets = new Insets(0, 0, 0, 0);
		gbc_info.gridx = 1;
		gbc_info.gridy = 0;
		panel_izq.add(panel_info, gbc_info);
		panel_info.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUsername = new JLabel(loggedUser.getUsername());
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_info.add(lblUsername, BorderLayout.NORTH);
/* Greeting */
		lblGreeting = new JTextArea(loggedUser.getGreeting());
		Graphics.buildGreeting(lblGreeting,50,50);
		panel_info.add(lblGreeting, BorderLayout.CENTER);
	}
	
	private void configurarToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
		Graphics.buildToolbar(toolbar);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 0, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
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
		gbc_txtFindName.insets = new Insets(0, 0, 0, 0);
		gbc_txtFindName.gridx = 0;
		gbc_txtFindName.gridy = 2;
		panel_izq.add(txtFindUser, gbc_txtFindName);
/* Contact List panel */
		getContentPane().add(listaChats);
		JScrollPane scroll = new JScrollPane(listaChats);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Graphics.buildScroll(scroll.getVerticalScrollBar());
		Graphics.buildScroll(scroll.getHorizontalScrollBar());
		GridBagConstraints gbc_scrollPane_names = new GridBagConstraints();
		gbc_scrollPane_names.gridwidth = 2;
		gbc_scrollPane_names.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_names.gridx = 0;
		gbc_scrollPane_names.gridy = 3;
		panel_izq.add(scroll, gbc_scrollPane_names);
	}

	private void configurarPanelDerecho() {
		panel_der.setLayout(new BorderLayout(0, 0));
		JToolBar toolbarChat = new JToolBar();
		toolbarChat.setLayout(new FlowLayout(FlowLayout.RIGHT));
		Graphics.buildToolbar(toolbarChat);
		panel_der.add(toolbarChat, BorderLayout.NORTH);
/* Borrar chat */
		btnDeleteChat = Graphics.makeIconButton("/trash.png");
		btnDeleteChat.setToolTipText("Eliminar chat");
		btnDeleteChat.addActionListener(this);
		toolbarChat.add(btnDeleteChat);
/* Search message */
		btnFindMessage = Graphics.makeIconButton("/searchmsg.png");
		btnFindMessage.setToolTipText("Buscar mensajes");
		btnFindMessage.addActionListener(this);
		toolbarChat.add(btnFindMessage);
/* Luz button */
		btnLuz = new Luz();
		btnLuz.addEncendidoListener(this);
		toolbarChat.add(btnLuz);
/* Chat panel */
		JScrollPane scrollChat = new JScrollPane(panelChat);
		//scrollChat.getViewport().setBackground(Graphics.BACKGROUND);
		Graphics.buildScroll(scrollChat.getVerticalScrollBar());
		Graphics.buildScroll(scrollChat.getHorizontalScrollBar());
		panel_der.add(scrollChat, BorderLayout.CENTER);
/* Text input */
		JPanel panel_writeMsg = new JPanel();
		panel_writeMsg.setLayout(new BoxLayout(panel_writeMsg, BoxLayout.X_AXIS));
		panel_der.add(panel_writeMsg, BorderLayout.SOUTH);
		
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
				listaChats.reloadList();
			}
		};
		
		UIManager.getLookAndFeelDefaults().put("MenuItem.background", Graphics.WHITE);
		UIManager.getLookAndFeelDefaults().put("MenuItem.selectionBackground", Graphics.LIGHT);
		popupMenuEmoji = new JPopupMenu();
		popupMenuEmoji.setLayout(new GridLayout(5, 5, 0, 0));//NOTE: 5x5 porque BubbleText.MAXICONO==25
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
		Graphics.reloadAvatarButton(btnAvatar, loggedUser.getAvatar());
	}
	
	public void reloadGreeting() {
		lblGreeting.setText(loggedUser.getGreeting());
	}
	
	public void changePremium() {
		appChat.togglePremium();
		btnStats.setVisible(loggedUser.isPremium());
	}
	
	private void mostrarTarta() {
		Thread t = new Thread(new Runnable() {
		    @Override
		    public void run() {
			    new SwingWrapper<PieChart>(new DiagramaTarta(loggedUser).getChart())
			    	.displayChart()
			    	.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		    }
		   });
		t.start();
	}

	private void mostrarHistograma() {
		Thread t = new Thread(new Runnable() {
		    @Override
		    public void run() {
			    new SwingWrapper<CategoryChart>(new Histograma(loggedUser).getChart())
			    	.displayChart()
			    	.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		    }
		   });
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAvatar) {
			new VentanaEditorPerfil(loggedUser, this);
			return;
		}
		if (e.getSource() == btnNewGroup) {
			new VentanaEditorGrupo(loggedUser, listaChats);
			return;
		}
		if (e.getSource() == btnPremium) {
			if (loggedUser.isPremium()) {
				if (JOptionPane.showConfirmDialog(this,
						"Quieres dejar de ser usuario Premium?",
						"Stop Premium Service",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
					changePremium();
			}
			else
				new VentanaPremium(loggedUser, this);
			return;
		}
		if (e.getSource() == btnContacts) {
			new VentanaContactos(loggedUser);
			return;
		}
		if (e.getSource() == btnStats) {
			Object[] options = {"Tarta","Histograma","Cancelar"};
			int opt = JOptionPane.showOptionDialog(this,
					"Selecciona el tipo de gráfico:",
					"Estadísticas de uso",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(new ImageIcon(Graphics.class.getResource("/stats.png")).getImage()
					.getScaledInstance(Graphics.SIZE_ICON,Graphics.SIZE_ICON,java.awt.Image.SCALE_SMOOTH)),
					options,
					options[2]
			);
			if (opt == JOptionPane.YES_OPTION)
				mostrarTarta();
			else if (opt == JOptionPane.NO_OPTION)
				mostrarHistograma();
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
			listaChats.reloadList();
			txtWriteMsg.setText("");
			return;
		}
		if (e.getSource() == btnDeleteChat) {
			String msg = "";
			Chat chat = panelChat.getActualChat();
			if (chat == null) return;
			if (chat instanceof ChatGrupo && !((ChatGrupo) chat).isAdmin(loggedUser))
					msg = "Abandonar el grupo?";
			else
				msg = "Borrar el chat y todos sus mensajes?";
			int opt = JOptionPane.showOptionDialog(this,
					msg,
					"Deleting chat...",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null,
					null,
					null
			);
			if (opt == JOptionPane.YES_OPTION) {
				panelChat.removeActualChat();
				appChat.deleteChat(chat);
				listaChats.remove(chat);
			}
			return;
		}
		if (e.getSource() == btnFindMessage) {
			if (panelChat.hasChat())
				new VentanaBusquedaMensaje(panelChat.getActualChat(), loggedUser.getUsername());
			return;
		}
		if (e.getSource() == txtFindUser) {
			Usuario user = appChat.findUser(txtFindUser.getText());
			if (user == null) {
				JOptionPane.showMessageDialog(
					this,
					"El usuario " + txtFindUser.getText() +" no existe.",
					"User doesn't exist",
					JOptionPane.ERROR_MESSAGE
				);
			} else if (user.equals(loggedUser)) {
				new VentanaEditorPerfil(loggedUser, this);
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
					appChat.readFileChat(chooser.getSelectedFile(), returnVal);
				}
				return;
			}
		}
	}
}
