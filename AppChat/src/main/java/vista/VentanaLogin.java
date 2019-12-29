package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

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

	private JFrame frame;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField textField_username;
	private JPasswordField passwField_password;
	private JButton btnLogin;
	private JButton btnRegister;
	private JPanel panel;
	
	public static void main(final String[] args){
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					try {
						new VentanaLogin();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		);
	}
	
	/**
	 * Create the application.
	 */
	public VentanaLogin() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("AppChat - Login");
		frame.setBounds(100, 100, MIN_WIDTH, MIN_HEIGHT);
		frame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_center = new JPanel();
		frame.getContentPane().add(panel_center, BorderLayout.CENTER);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0};
		panel_center.setLayout(gridBagLayout);
		
/* Username */
		lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 1;
		panel_center.add(lblUsername, gbc_lblUsername);
		
		textField_username = new JTextField();
		GridBagConstraints gbc_textField_username = new GridBagConstraints();
		gbc_textField_username.insets = new Insets(0, 0, 5, 5);
		gbc_textField_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_username.gridx = 2;
		gbc_textField_username.gridy = 1;
		panel_center.add(textField_username, gbc_textField_username);
		textField_username.setColumns(10);

/* Password */
		lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 2;
		panel_center.add(lblPassword, gbc_lblPassword);
		
		passwField_password = new JPasswordField();
		GridBagConstraints gbc_textField_password = new GridBagConstraints();
		gbc_textField_password.insets = new Insets(0, 0, 5, 5);
		gbc_textField_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_password.gridx = 2;
		gbc_textField_password.gridy = 2;
		panel_center.add(passwField_password, gbc_textField_password);
		passwField_password.setColumns(10);
		
/* Buttons */
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		panel_center.add(panel, gbc_panel);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		panel.add(btnLogin);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(this);
		panel.add(btnRegister);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			boolean login = AppChat.getInstance().login(
					textField_username.getText(), new String(passwField_password.getPassword()));
			if (login) {
				VentanaAppChat window = new VentanaAppChat();
				window.setVisible(true);
				frame.dispose();
			} else
				JOptionPane.showMessageDialog(frame,
					"Nombre de usuario o contrase\u00F1a no valido",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (e.getSource() == btnRegister) {
			frame.setTitle("AppChat - Register");
			new PanelRegistro(frame);
			return;	
		}
	}

}
