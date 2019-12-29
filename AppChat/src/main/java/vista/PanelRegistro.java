package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;

import com.toedter.calendar.JDateChooser;
import controlador.AppChat;


@SuppressWarnings("serial")
public class PanelRegistro extends JPanel implements ActionListener {
	
	private JFrame frame;
	private JPanel panel_previous;
	private JPanel panel_registration;
	private JPanel panel_buttons;
	
	private JLabel warningName;
	private JLabel warningUsername;
	private JLabel warningPassword;
	private JLabel warningPasswordChk;
	private JLabel warningBday;
	private JLabel warningEmail;
	private JLabel warningPhone;
	private JLabel warningGreeting;
	private JLabel warningExiste;
	
	private JTextField field_name;
	private JDateChooser field_bday;
	private JTextField field_phone;
	private JTextField field_email;
	private JTextField field_username;
	private JPasswordField field_password;
	private JPasswordField field_passwordchk;
	private JTextField field_greeting;
	
	private JButton btn_register;
	private JButton btn_cancel;

	public PanelRegistro(JFrame _frame) {
		frame = _frame;
		panel_previous = (JPanel) frame.getContentPane();
		
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane, BorderLayout.CENTER);
		
		panel_registration = new JPanel();
		tabbedPane.addTab("User Registrarion", null, panel_registration, null);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 100, 0, 0, 100, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		panel_registration.setLayout(gridBagLayout);
		
/* Name */
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		panel_registration.add(new JLabel("Nombre:"), gbc_lblNombre);
		
		field_name = new JTextField();
		field_name.setColumns(10);
		GridBagConstraints gbc_textField_name = new GridBagConstraints();
		gbc_textField_name.gridwidth = 3;
		gbc_textField_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_name.insets = new Insets(0, 0, 5, 5);
		gbc_textField_name.gridx = 2;
		gbc_textField_name.gridy = 1;
		panel_registration.add(field_name, gbc_textField_name);
		
		warningName = new JLabel("*");
		warningName.setForeground(Color.RED);
		GridBagConstraints gbc_warningName = new GridBagConstraints();
		gbc_warningName.insets = new Insets(0, 0, 5, 5);
		gbc_warningName.anchor = GridBagConstraints.WEST;
		gbc_warningName.gridx = 5;
		gbc_warningName.gridy = 1;
		panel_registration.add(warningName, gbc_warningName);
		
/* Birthday */
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeNacimiento.gridx = 1;
		gbc_lblFechaDeNacimiento.gridy = 2;
		panel_registration.add(new JLabel("Fecha de nacimiento:"), gbc_lblFechaDeNacimiento);
		
		field_bday = new JDateChooser();
		field_bday.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 2;
		panel_registration.add(field_bday, gbc_dateChooser);
		
		warningBday = new JLabel("*");
		warningBday.setForeground(Color.RED);
		GridBagConstraints gbc_warningBday = new GridBagConstraints();
		gbc_warningBday.insets = new Insets(0, 0, 5, 5);
		gbc_warningBday.anchor = GridBagConstraints.WEST;
		gbc_warningBday.gridx = 3;
		gbc_warningBday.gridy = 2;
		panel_registration.add(warningBday, gbc_warningBday);
		
/* Email */
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 3;
		panel_registration.add(new JLabel("Email:"), gbc_lblEmail);
		
		field_email = new JTextField();
		field_email.setColumns(10);
		GridBagConstraints gbc_textField_email = new GridBagConstraints();
		gbc_textField_email.gridwidth = 3;
		gbc_textField_email.insets = new Insets(0, 0, 5, 5);
		gbc_textField_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_email.gridx = 2;
		gbc_textField_email.gridy = 3;
		panel_registration.add(field_email, gbc_textField_email);
		
		warningEmail = new JLabel("*");
		warningEmail.setForeground(Color.RED);
		GridBagConstraints gbc_warningEmail = new GridBagConstraints();
		gbc_warningEmail.insets = new Insets(0, 0, 5, 5);
		gbc_warningEmail.anchor = GridBagConstraints.WEST;
		gbc_warningEmail.gridx = 5;
		gbc_warningEmail.gridy = 3;
		panel_registration.add(warningEmail, gbc_warningEmail);
		
/* Phone */
		GridBagConstraints gbc_lblMovil = new GridBagConstraints();
		gbc_lblMovil.anchor = GridBagConstraints.EAST;
		gbc_lblMovil.insets = new Insets(0, 0, 5, 5);
		gbc_lblMovil.gridx = 1;
		gbc_lblMovil.gridy = 4;
		panel_registration.add(new JLabel("Movil:"), gbc_lblMovil);
		
		field_phone = new JTextField();
		field_phone.setColumns(10);
		GridBagConstraints gbc_textField_phone = new GridBagConstraints();
		gbc_textField_phone.insets = new Insets(0, 0, 5, 5);
		gbc_textField_phone.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_phone.gridx = 2;
		gbc_textField_phone.gridy = 4;
		panel_registration.add(field_phone, gbc_textField_phone);
		
		warningPhone = new JLabel("*");
		warningPhone.setForeground(Color.RED);
		GridBagConstraints gbc_warningPhone = new GridBagConstraints();
		gbc_warningPhone.insets = new Insets(0, 0, 5, 5);
		gbc_warningPhone.anchor = GridBagConstraints.WEST;
		gbc_warningPhone.gridx = 3;
		gbc_warningPhone.gridy = 4;
		panel_registration.add(warningPhone, gbc_warningPhone);
		
/* Username */
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 5;
		panel_registration.add(new JLabel("Username:"), gbc_lblUsername);
		
		field_username = new JTextField();
		field_username.setColumns(10);
		GridBagConstraints gbc_textField_username = new GridBagConstraints();
		gbc_textField_username.insets = new Insets(0, 0, 5, 5);
		gbc_textField_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_username.gridx = 2;
		gbc_textField_username.gridy = 5;
		panel_registration.add(field_username, gbc_textField_username);
		
		warningUsername = new JLabel("*");
		warningUsername.setForeground(Color.RED);
		GridBagConstraints gbc_warningUsername = new GridBagConstraints();
		gbc_warningUsername.insets = new Insets(0, 0, 5, 5);
		gbc_warningUsername.anchor = GridBagConstraints.WEST;
		gbc_warningUsername.gridx = 3;
		gbc_warningUsername.gridy = 5;
		panel_registration.add(warningUsername, gbc_warningUsername);
		
		warningExiste = new JLabel("El usuario ya existe");
		warningExiste.setForeground(Color.RED);
		GridBagConstraints gbc_warningUsername2 = new GridBagConstraints();
		gbc_warningUsername2.gridwidth = 2;
		gbc_warningUsername2.insets = new Insets(0, 0, 5, 5);
		gbc_warningUsername2.anchor = GridBagConstraints.WEST;
		gbc_warningUsername2.gridx = 4;
		gbc_warningUsername2.gridy = 5;
		panel_registration.add(warningExiste, gbc_warningUsername2);
		
/* Password */
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 6;
		panel_registration.add(new JLabel("Password:"), gbc_lblPassword);
		
		field_password = new JPasswordField();
		field_password.setColumns(10);
		GridBagConstraints gbc_textField_password = new GridBagConstraints();
		gbc_textField_password.insets = new Insets(0, 0, 5, 5);
		gbc_textField_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_password.gridx = 2;
		gbc_textField_password.gridy = 6;
		panel_registration.add(field_password, gbc_textField_password);
		
		warningPassword = new JLabel("*");
		warningPassword.setForeground(Color.RED);
		GridBagConstraints gbc_warningPassword = new GridBagConstraints();
		gbc_warningPassword.insets = new Insets(0, 0, 5, 5);
		gbc_warningPassword.anchor = GridBagConstraints.EAST;
		gbc_warningPassword.gridx = 3;
		gbc_warningPassword.gridy = 6;
		panel_registration.add(warningPassword, gbc_warningPassword);
		
/* Password check */
		GridBagConstraints gbc_lblRepetirPassword = new GridBagConstraints();
		gbc_lblRepetirPassword.anchor = GridBagConstraints.EAST;
		gbc_lblRepetirPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepetirPassword.gridx = 4;
		gbc_lblRepetirPassword.gridy = 6;
		panel_registration.add(new JLabel("Repetir:"), gbc_lblRepetirPassword);
		
		field_passwordchk = new JPasswordField();
		field_passwordchk.setColumns(10);
		GridBagConstraints gbc_textField_password2 = new GridBagConstraints();
		gbc_textField_password2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_password2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_password2.gridx = 5;
		gbc_textField_password2.gridy = 6;
		panel_registration.add(field_passwordchk, gbc_textField_password2);
		
		warningPasswordChk = new JLabel("* No coinciden");
		warningPasswordChk.setForeground(Color.RED);
		GridBagConstraints gbc_warningPasswordChk = new GridBagConstraints();
		gbc_warningPasswordChk.insets = new Insets(0, 0, 5, 5);
		gbc_warningPasswordChk.anchor = GridBagConstraints.WEST;
		gbc_warningPasswordChk.gridx = 6;
		gbc_warningPasswordChk.gridy = 6;
		panel_registration.add(warningPasswordChk, gbc_warningPasswordChk);
		
/* Greeting */
		GridBagConstraints gbc_lblSaludo = new GridBagConstraints();
		gbc_lblSaludo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaludo.anchor = GridBagConstraints.EAST;
		gbc_lblSaludo.gridx = 1;
		gbc_lblSaludo.gridy = 7;
		panel_registration.add(new JLabel("Saludo (opcional):"), gbc_lblSaludo);

		warningGreeting = new JLabel("*");
		warningGreeting.setForeground(Color.RED);
		GridBagConstraints gbc_warningGreeting = new GridBagConstraints();
		gbc_warningGreeting.insets = new Insets(0, 0, 5, 5);
		gbc_warningGreeting.anchor = GridBagConstraints.WEST;
		gbc_warningGreeting.gridx = 6;
		gbc_warningGreeting.gridy = 7;
		panel_registration.add(warningGreeting, gbc_warningGreeting);
		
		field_greeting = new JTextField();
		field_greeting.setColumns(10);
		GridBagConstraints gbc_textField_greeting = new GridBagConstraints();
		gbc_textField_greeting.gridwidth = 4;
		gbc_textField_greeting.insets = new Insets(0, 0, 5, 5);
		gbc_textField_greeting.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_greeting.gridx = 2;
		gbc_textField_greeting.gridy = 7;
		panel_registration.add(field_greeting, gbc_textField_greeting);
		
/* Buttons */
		panel_buttons = new JPanel();
		GridBagConstraints gbc_panel_botones = new GridBagConstraints();
		gbc_panel_botones.insets = new Insets(0, 0, 5, 5);
		gbc_panel_botones.gridwidth = 6;
		gbc_panel_botones.fill = GridBagConstraints.BOTH;
		gbc_panel_botones.gridx = 1;
		gbc_panel_botones.gridy = 8;
		panel_registration.add(panel_buttons, gbc_panel_botones);
		
		btn_register = new JButton("Registrar");
		btn_register.addActionListener(this);
		panel_buttons.add(btn_register);

		btn_cancel = new JButton("Cancelar");
		btn_cancel.addActionListener(this);
		panel_buttons.add(btn_cancel);
		
		ocultarErrores();
		
		frame.setContentPane(this);
		frame.revalidate(); //redibujar con el nuevo JPanel
		frame.repaint();
	}
	
	private boolean checkFields() {
		ocultarErrores();

		boolean ok = true;
		if (field_name.getText().trim().isEmpty()) {
			warningName.setVisible(true); 
			ok=false;
		}
		if (field_bday.getDate() == null) {
			warningBday.setVisible(true); 
			ok=false;
		}
		if (field_phone.getText().trim().isEmpty()) {
			warningPhone.setVisible(true); 
			ok=false;
		}
		if (field_email.getText().trim().isEmpty()) {
			warningEmail.setVisible(true); 
			ok=false;
		}
		if (field_username.getText().trim().isEmpty()) {
			warningUsername.setVisible(true); 
			ok=false;
		}		
		String pwd = new String(field_password.getPassword());
		if (pwd.isEmpty()) {
			warningPassword.setVisible(true); 
			ok=false;
		}
		String pwdchk = new String(field_passwordchk.getPassword());
		if (!pwd.equals(pwdchk)) {
			warningPasswordChk.setVisible(true); 
			ok=false;
		}
		if (AppChat.getInstance().isRegistered(field_username.getText())) {
			warningUsername.setVisible(true); 
			warningExiste.setVisible(true); 
			ok=false;		
		}
		return ok;
	}
	
	private boolean register() {
		boolean ok = AppChat.getInstance().register(
								field_username.getText(),
								new String(field_password.getPassword()),
								field_name.getText(),
								field_bday.getDate(),
								field_email.getText(),
								field_phone.getText(),
								field_greeting.getText()
								);
		if (ok) {
			JOptionPane.showMessageDialog(
						frame,
						"Usuario registrado correctamente.",
						"Registration success",
						JOptionPane.INFORMATION_MESSAGE
					  	);
			frame.setContentPane(panel_previous);
			frame.revalidate();
		} else {
			JOptionPane.showMessageDialog(
						frame,
						"No se ha podido llevar a cabo el registro.",
						"Registration fail",
						JOptionPane.ERROR_MESSAGE
						);
		}
		return ok;
	}

	private void ocultarErrores() {
		warningExiste.setVisible(false);
		warningUsername.setVisible(false);
		warningPassword.setVisible(false);
		warningPasswordChk.setVisible(false);
		warningName.setVisible(false);
		warningBday.setVisible(false);
		warningEmail.setVisible(false);
		warningPhone.setVisible(false);
		warningGreeting.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_register) {
			if (checkFields()) register();
			return;
		}
		if (e.getSource() == btn_cancel) {
			frame.setContentPane(panel_previous);
			frame.setTitle("AppChat - Login");	
			frame.revalidate();
			return;
		}
	}
}
