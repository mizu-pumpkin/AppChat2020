package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
import java.awt.Component;

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
		setSize(300,200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0, 0};
		gbl.rowHeights = new int[]{10, 0, 0, 10};
		gbl.columnWeights = new double[]{1.0, 0.0, 1.0};
		gbl.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0};
		getContentPane().setLayout(gbl);
		
		configurarInfoUsuario();
		configurarInfoContacto();
		setVisible(true);
	}
	
	private void configurarInfoUsuario() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 2;
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		getContentPane().add(panel, gbc);
		
		JLabel lblAvatar = new JLabel(Graphics.makeAvatar(user.getAvatar(), Graphics.SIZE_AVATAR_BIG));
		panel.add(lblAvatar);
		
		JPanel panel_personalInfo = new JPanel();
		panel_personalInfo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(panel_personalInfo);
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{110, 0};
		gbl.rowHeights = new int[]{14, 14, 0, 0};
		gbl.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_personalInfo.setLayout(gbl);
		
		JLabel lblUsername = new JLabel("Username: "+user.getUsername());
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUsername.insets = new Insets(0, 0, 0, 0);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 0;
		panel_personalInfo.add(lblUsername, gbc_lblUsername);
		
		JLabel lblPhone = new JLabel("Teléfono: "+user.getPhone());
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPhone.insets = new Insets(0, 0, 0, 0);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 1;
		panel_personalInfo.add(lblPhone, gbc_lblPhone);
		
		JTextArea lblGreeting = new JTextArea(user.getGreeting());
		Graphics.buildGreeting(lblGreeting,150,50);
		GridBagConstraints gbc_lblGreeting = new GridBagConstraints();
		gbc_lblGreeting.fill = GridBagConstraints.BOTH;
		gbc_lblGreeting.gridx = 0;
		gbc_lblGreeting.gridy = 2;
		panel_personalInfo.add(lblGreeting, gbc_lblGreeting);
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
		gbc_txtName.insets = new Insets(0, 0, 0, 0);
		gbc_txtName.fill = GridBagConstraints.BOTH;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 1;
		getContentPane().add(txtName, gbc_txtName);
		
		JPanel panel_btns = new JPanel();
		GridBagConstraints gbc_panel_btns = new GridBagConstraints();
		gbc_panel_btns.anchor = GridBagConstraints.NORTH;
		gbc_panel_btns.insets = new Insets(0, 0, 0, 0);
		gbc_panel_btns.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_btns.gridx = 1;
		gbc_panel_btns.gridy = 3;
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
