package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modelo.ChatIndividual;
import modelo.Usuario;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import controlador.AppChat;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class VentanaEditorContacto extends JFrame implements ActionListener {
	
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
		setBounds(100, 100, Graphics.MIN_WIDTH, Graphics.MIN_HEIGHT);
		//setMinimumSize(new Dimension(Graphics.MIN_WIDTH, Graphics.MIN_HEIGHT));
		setResizable(false);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{10, 0, 0, 10, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gbl);
		
		configurarInfoUsuario();
		configurarInfoContacto();
		setVisible(true);
	}
	
	private void configurarInfoUsuario() {
		JLabel lblAvatar = new JLabel(Graphics.makeAvatar(user.getAvatar(), Graphics.SIZE_AVATAR_BIG));
		GridBagConstraints gbc_lblAvatar = new GridBagConstraints();
		gbc_lblAvatar.fill = GridBagConstraints.VERTICAL;
		gbc_lblAvatar.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvatar.gridx = 1;
		gbc_lblAvatar.gridy = 2;
		getContentPane().add(lblAvatar, gbc_lblAvatar);
		
		JPanel panel_info = new JPanel();
		GridBagConstraints gbc_panel_info = new GridBagConstraints();
		gbc_panel_info.anchor = GridBagConstraints.WEST;
		gbc_panel_info.insets = new Insets(0, 0, 5, 5);
		gbc_panel_info.fill = GridBagConstraints.VERTICAL;
		gbc_panel_info.gridx = 2;
		gbc_panel_info.gridy = 2;
		getContentPane().add(panel_info, gbc_panel_info);
		panel_info.setLayout(new BoxLayout(panel_info, BoxLayout.Y_AXIS));
		
		JLabel lblUsername = new JLabel(user.getUsername());
		panel_info.add(lblUsername);
		
		JLabel lblPhone = new JLabel(user.getPhone());
		panel_info.add(lblPhone);
		
		JLabel lblGreeting = new JLabel(user.getGreeting());
		GridBagConstraints gbc_greeting = new GridBagConstraints();
		gbc_greeting.fill = GridBagConstraints.BOTH;
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
		gbc_txtName.fill = GridBagConstraints.BOTH;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 4;
		getContentPane().add(txtName, gbc_txtName);
		
		JPanel panel_btns = new JPanel();
		GridBagConstraints gbc_panel_btns = new GridBagConstraints();
		gbc_panel_btns.gridwidth = 2;
		gbc_panel_btns.insets = new Insets(0, 0, 5, 5);
		gbc_panel_btns.fill = GridBagConstraints.BOTH;
		gbc_panel_btns.gridx = 1;
		gbc_panel_btns.gridy = 5;
		getContentPane().add(panel_btns, gbc_panel_btns);
		panel_btns.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_btns.add(btnEdit);
		panel_btns.add(btnCancel);
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
