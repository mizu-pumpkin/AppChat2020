package vista;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.ChatIndividual;
import modelo.Usuario;
import tds.BubbleText;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import controlador.AppChat;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class VentanaEditorContacto extends JFrame implements ActionListener {

	private final static int MIN_WIDTH = 300;
	private final static int MIN_HEIGHT = 200;
	
	private ChatIndividual contact;
	private Usuario user;
	private PanelListaChats listaChats;
	
	private JButton btnEdit;
	private JButton btnCancel;
	private JTextField txtName;

	public VentanaEditorContacto(Usuario user, PanelListaChats listaChats) {
		this.user = user;
		this.contact = null;
		this.listaChats = listaChats;
		setTitle("Añadir - "+user.getPhone());
		initialize();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaEditorContacto(ChatIndividual contact, PanelListaChats listaChats) {
		this.user = contact.getUser();
		this.contact = contact;
		this.listaChats = listaChats;
		setTitle("Editar - "+contact.getName());
		initialize();
	}
	
	private void initialize() {
		setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{10, 0, 0, 10, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gbl);
		
		configurarInfoUsuario();
		configurarInfoContacto();
		setVisible(true);
	}
	
	private void configurarInfoUsuario() {
		JLabel lblAvatar = new JLabel(new ImageIcon(//FIXME
			BubbleText.getEmoji(2)
			.getImage()
			.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH))
		);
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.fill = GridBagConstraints.VERTICAL;
		gbc_lblAvatar.gridheight = 2;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 1;
		gbc_lblAvatar.gridy = 1;
		getContentPane().add(lblAvatar, gbc_lblAvatar);
		
		JLabel lblUsername = new JLabel(user.getUsername());
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.WEST;
		gbc_lblUsername.fill = GridBagConstraints.VERTICAL;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 2;
		gbc_lblUsername.gridy = 1;
		getContentPane().add(lblUsername, gbc_lblUsername);
		
		JLabel lblPhone = new JLabel(user.getPhone());
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.WEST;
		gbc_lblPhone.fill = GridBagConstraints.VERTICAL;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 2;
		gbc_lblPhone.gridy = 2;
		getContentPane().add(lblPhone, gbc_lblPhone);
		
		JLabel lblGreeting = new JLabel(user.getGreeting());
		GridBagConstraints gbc_greeting = new GridBagConstraints();
		gbc_greeting.fill = GridBagConstraints.HORIZONTAL;
		gbc_greeting.insets = new Insets(0, 0, 5, 5);
		gbc_greeting.gridx = 1;
		gbc_greeting.gridy = 3;
		gbc_greeting.gridwidth = 2;
		getContentPane().add(lblGreeting, gbc_greeting);
	}
	
	private void configurarInfoContacto() {
/* Parte funcional */
		txtName = new JTextField();
		if (contact != null)
			txtName.setText(contact.getName());
		else
			txtName.setText("Elige un nombre");
		txtName.setColumns(10);
		
		btnEdit = Graphics.makeButton("Guardar");
		btnEdit.addActionListener(this);
		getRootPane().setDefaultButton(btnEdit);
		
		btnCancel = Graphics.makeButton("Cancelar");
		btnCancel.addActionListener(this);
/* Parte gráfica */
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.gridwidth = 2;
		gbc_txtName.insets = new Insets(0, 0, 5, 5);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 4;
		getContentPane().add(txtName, gbc_txtName);
		
		JPanel panelBtns = new JPanel();
		GridBagConstraints gbc_panelBtns = new GridBagConstraints();
		gbc_panelBtns.gridwidth = 2;
		gbc_panelBtns.insets = new Insets(0, 0, 5, 5);
		gbc_panelBtns.fill = GridBagConstraints.BOTH;
		gbc_panelBtns.gridx = 1;
		gbc_panelBtns.gridy = 5;
		getContentPane().add(panelBtns, gbc_panelBtns);
		panelBtns.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelBtns.add(btnEdit);
		panelBtns.add(btnCancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEdit) {
			if (contact == null) {
				AppChat.getInstance().registerContact(txtName.getText(), user.getPhone());
				listaChats.reloadList();
			} else {
				AppChat.getInstance().editContact(contact, txtName.getText());
				listaChats.reloadList();
			}
			dispose();
			return;
		}
		if (e.getSource() == btnCancel) {
			dispose();
			return;
		}
	}

}
