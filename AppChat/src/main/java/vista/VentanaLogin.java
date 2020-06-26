package vista;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import controlador.AppChat;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class VentanaLogin extends JFrame implements ActionListener {

	private final static int MIN_WIDTH = 540;
	private final static int MIN_HEIGHT = 360;
	
	private JPanel contentPane;

	private JTextField textField_username;
	private JPasswordField passwField_password;
	private JButton btnLogin;
	private JButton btnRegister;
	
	public static void main(final String[] args){
		new VentanaLogin();
	}
	
	public VentanaLogin() {
		setTitle("AppChat - Login");
		setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = (JPanel) this.getContentPane();
		
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{0, 0, 0, 0};
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0};
		gbl.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0};
		contentPane.setLayout(gbl);

		configurarCampos();
		configurarBotones();
		
		pack();
		setVisible(true);
	}

	private void configurarCampos() {	
/* Username */
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 1;
		contentPane.add(new JLabel("Username"), gbc_lblUsername);
		
		GridBagConstraints gbc_textField_username = new GridBagConstraints();
		gbc_textField_username.insets = new Insets(0, 0, 5, 5);
		gbc_textField_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_username.gridx = 2;
		gbc_textField_username.gridy = 1;
		textField_username = new JTextField();
		textField_username.setColumns(10);
		contentPane.add(textField_username, gbc_textField_username);

/* Password */
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 2;
		contentPane.add(new JLabel("Password"), gbc_lblPassword);
		
		GridBagConstraints gbc_textField_password = new GridBagConstraints();
		gbc_textField_password.insets = new Insets(0, 0, 5, 5);
		gbc_textField_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_password.gridx = 2;
		gbc_textField_password.gridy = 2;
		passwField_password = new JPasswordField();
		passwField_password.setColumns(10);
		contentPane.add(passwField_password, gbc_textField_password);
	}
	
	private void configurarBotones() {
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		JPanel panel_buttons = new JPanel();
		contentPane.add(panel_buttons, gbc_panel);
		
		btnLogin = new JButton("Login");
		panel_buttons.add(btnLogin);
		
		btnRegister = new JButton("Register");
		panel_buttons.add(btnRegister);
		
		btnLogin.addActionListener(this);
		btnRegister.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			boolean login = AppChat.getInstance().login(
					textField_username.getText(), new String(passwField_password.getPassword()));
			if (login) {
				new VentanaAppChat(AppChat.getInstance().getUsuarioActual());
				dispose();
			} else
				JOptionPane.showMessageDialog(this,
					"Nombre de usuario o contrase\u00F1a no valido",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (e.getSource() == btnRegister) {
			setTitle("AppChat - Register");
			new PanelRegistro(this);
			return;
		}
	}

}
