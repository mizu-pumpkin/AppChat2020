package umu.tds.gui2;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;

import umu.tds.controlador.ControladorEmpresas;
import umu.tds.controlador.ControladorAsistentes;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;

public class LoginView {

	private JFrame frmLoginGestorEventos;
	private JTextField textLogin;
	private JPasswordField textPassword;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginGestorEventos = new JFrame();
		frmLoginGestorEventos.setTitle("Login Gestor Eventos");
		frmLoginGestorEventos.setBounds(100, 100, 450, 320);
		frmLoginGestorEventos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginGestorEventos.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Norte = new JPanel();
		panel_Norte.setPreferredSize(new Dimension(100, 70));
		frmLoginGestorEventos.getContentPane().add(panel_Norte, BorderLayout.NORTH);
		panel_Norte.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		
		JLabel lblGestorEventos = new JLabel("Gestor Eventos");
		lblGestorEventos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGestorEventos.setForeground(Color.DARK_GRAY);
		panel_Norte.add(lblGestorEventos);
		
		JPanel panel_Oeste = new JPanel();
		frmLoginGestorEventos.getContentPane().add(panel_Oeste, BorderLayout.WEST);
		panel_Oeste.setLayout(new GridLayout(3, 1, 0, 0));
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		panel_Oeste.add(rigidArea_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_Oeste.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 5, 8));
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Empresa");
		rdbtnNewRadioButton_1.setSelected(true);
		rdbtnNewRadioButton_1.setActionCommand("Empresa");
		panel_2.add(rdbtnNewRadioButton_1);
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Asistente");
		rdbtnNewRadioButton.setActionCommand("Asistente");
		panel_2.add(rdbtnNewRadioButton);
		buttonGroup.add(rdbtnNewRadioButton);
		
		JPanel panel_Centro = new JPanel();
		frmLoginGestorEventos.getContentPane().add(panel_Centro, BorderLayout.CENTER);
		panel_Centro.setLayout(new GridLayout(4, 2, 0, 0));
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		panel_Centro.add(rigidArea_3);
		
		JPanel panel = new JPanel();
		panel_Centro.add(panel);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setPreferredSize(new Dimension(56, 14));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(lblNewLabel);
		
		textLogin = new JTextField();
		panel.add(textLogin);
		textLogin.setColumns(15);
		
		JPanel panel_1 = new JPanel();
		panel_Centro.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(lblNewLabel_1);
		
		textPassword = new JPasswordField();
		panel_1.add(textPassword);
		textPassword.setColumns(15);
		
		JPanel panel_Sur = new JPanel();
		frmLoginGestorEventos.getContentPane().add(panel_Sur, BorderLayout.SOUTH);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_Sur.add(btnLogin);
		btnLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean login;
				if (buttonGroup.getSelection().getActionCommand() == "Empresa")
					 login = ControladorEmpresas.getUnicaInstancia().loginEmpresa(
						textLogin.getText(), new String(textPassword.getPassword()));
				else 
					 login = ControladorAsistentes.getUnicaInstancia().loginAsistente(
							textLogin.getText(), new String(textPassword.getPassword()));
				if (login) {
						GestionEventosMainWindow window = new GestionEventosMainWindow();
						window.setVisible(true);
						frmLoginGestorEventos.dispose();
				} else
						JOptionPane.showMessageDialog(frmLoginGestorEventos,
								"Nombre de usuario o contraseña no valido",
								"Error", JOptionPane.ERROR_MESSAGE);
			} 
	    }); 
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_Sur.add(rigidArea_1);
		
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_Sur.add(btnRegistro);
		btnRegistro.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (buttonGroup.getSelection().getActionCommand() == "Empresa"){
					frmLoginGestorEventos.setTitle("Registro Empresa");
					new RegistroEmpresa(frmLoginGestorEventos);
				}		 
				else {
					frmLoginGestorEventos.setTitle("Registro Asistente");	
					new RegistroAsistente(frmLoginGestorEventos);
				}
			}
		});
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(160, 40));
		panel_Sur.add(rigidArea);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_Sur.add(btnSalir);
		btnSalir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frmLoginGestorEventos.dispose(); /*Termina la maquina virtual*/
				System.exit(0);  /*no sería necesario en este caso*/
			}
		});
	}
	public void mostrarVentana() {
		frmLoginGestorEventos.setVisible(true);
		
	}

}
