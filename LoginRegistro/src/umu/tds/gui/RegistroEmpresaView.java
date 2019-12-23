package umu.tds.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import umu.tds.controlador.ControladorEmpresas;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RegistroEmpresaView extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame ventana;
	private JPanel jpanelAnterior;
	private JPanel datosEmpresa;
	private JLabel lblNombre_1;
	private JTextField txtNombre;
	private JLabel lblDomicilio_1;
	private JTextField txtDomicilio;
	private JLabel lblCif;
	private JTextField txtCIF;
	private JLabel lblUsuario_1;
	private JTextField txtUsuario;
	private JLabel lblClave;
	private JLabel lblRepetir;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	private JPasswordField txtPassword;
	private JPasswordField txtPassword2;
	private JLabel warningAll;
	private JLabel warningNombre;
	private JLabel warningDomicilio;
	private JLabel warningCIF;
	private JLabel warningUsuario;
	private JLabel warningClave;
	private JLabel warningClave2;
	private JLabel warningExiste;
	
	public RegistroEmpresaView(JFrame frame){
		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		
		this.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		this.add(tabbedPane,BorderLayout.CENTER);
		
		datosEmpresa = new JPanel();
		tabbedPane.addTab("Datos Empresa", null, datosEmpresa, null);
		GridBagLayout gbl_datosEmpresa = new GridBagLayout();
		gbl_datosEmpresa.columnWidths = new int[]{30, 0, 0, 0, 0, 0, 0};
		gbl_datosEmpresa.rowHeights = new int[]{30, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0};
		gbl_datosEmpresa.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_datosEmpresa.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		datosEmpresa.setLayout(gbl_datosEmpresa);
		
		lblNombre_1 = new JLabel("Nombre : ");
		GridBagConstraints gbc_lblNombre_1 = new GridBagConstraints();
		gbc_lblNombre_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre_1.anchor = GridBagConstraints.EAST;
		gbc_lblNombre_1.gridx = 1;
		gbc_lblNombre_1.gridy = 1;
		datosEmpresa.add(lblNombre_1, gbc_lblNombre_1);
		
		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridwidth = 3;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 1;
		datosEmpresa.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(20);
		
		warningNombre = new JLabel("*");
		warningNombre.setForeground(Color.RED);
		GridBagConstraints gbc_warningNombre = new GridBagConstraints();
		gbc_warningNombre.anchor = GridBagConstraints.WEST;
		gbc_warningNombre.insets = new Insets(0, 0, 5, 0);
		gbc_warningNombre.gridx = 5;
		gbc_warningNombre.gridy = 1;
		datosEmpresa.add(warningNombre, gbc_warningNombre);
		
		lblDomicilio_1 = new JLabel("Domicilio : ");
		GridBagConstraints gbc_lblDomicilio_1 = new GridBagConstraints();
		gbc_lblDomicilio_1.anchor = GridBagConstraints.EAST;
		gbc_lblDomicilio_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDomicilio_1.gridx = 1;
		gbc_lblDomicilio_1.gridy = 2;
		datosEmpresa.add(lblDomicilio_1, gbc_lblDomicilio_1);
		
		txtDomicilio = new JTextField();
		GridBagConstraints gbc_txtDomicilio = new GridBagConstraints();
		gbc_txtDomicilio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDomicilio.gridwidth = 3;
		gbc_txtDomicilio.insets = new Insets(0, 0, 5, 5);
		gbc_txtDomicilio.gridx = 2;
		gbc_txtDomicilio.gridy = 2;
		datosEmpresa.add(txtDomicilio, gbc_txtDomicilio);
		txtDomicilio.setColumns(20);
		
		warningDomicilio = new JLabel("*");
		warningDomicilio.setForeground(Color.RED);
		GridBagConstraints gbc_warningDomicilio = new GridBagConstraints();
		gbc_warningDomicilio.anchor = GridBagConstraints.WEST;
		gbc_warningDomicilio.insets = new Insets(0, 0, 5, 0);
		gbc_warningDomicilio.gridx = 5;
		gbc_warningDomicilio.gridy = 2;
		datosEmpresa.add(warningDomicilio, gbc_warningDomicilio);
		
		lblCif = new JLabel("CIF : ");
		GridBagConstraints gbc_lblCif = new GridBagConstraints();
		gbc_lblCif.anchor = GridBagConstraints.EAST;
		gbc_lblCif.insets = new Insets(0, 0, 5, 5);
		gbc_lblCif.gridx = 1;
		gbc_lblCif.gridy = 3;
		datosEmpresa.add(lblCif, gbc_lblCif);
		
		txtCIF = new JTextField();
		GridBagConstraints gbc_txtCIF = new GridBagConstraints();
		gbc_txtCIF.anchor = GridBagConstraints.WEST;
		gbc_txtCIF.insets = new Insets(0, 0, 5, 5);
		gbc_txtCIF.gridx = 2;
		gbc_txtCIF.gridy = 3;
		datosEmpresa.add(txtCIF, gbc_txtCIF);
		txtCIF.setColumns(10);
		
		warningCIF = new JLabel("*");
		warningCIF.setForeground(Color.RED);
		GridBagConstraints gbc_warningCIF = new GridBagConstraints();
		gbc_warningCIF.anchor = GridBagConstraints.WEST;
		gbc_warningCIF.insets = new Insets(0, 0, 5, 5);
		gbc_warningCIF.gridx = 3;
		gbc_warningCIF.gridy = 3;
		datosEmpresa.add(warningCIF, gbc_warningCIF);
		
		lblUsuario_1 = new JLabel("Usuario : ");
		GridBagConstraints gbc_lblUsuario_1 = new GridBagConstraints();
		gbc_lblUsuario_1.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario_1.gridx = 1;
		gbc_lblUsuario_1.gridy = 4;
		datosEmpresa.add(lblUsuario_1, gbc_lblUsuario_1);
		
		txtUsuario = new JTextField();
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.anchor = GridBagConstraints.WEST;
		gbc_txtUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsuario.gridx = 2;
		gbc_txtUsuario.gridy = 4;
		datosEmpresa.add(txtUsuario, gbc_txtUsuario);
		txtUsuario.setColumns(10);
		
		warningUsuario = new JLabel("*");
		warningUsuario.setForeground(Color.RED);
		GridBagConstraints gbc_warningUSuario = new GridBagConstraints();
		gbc_warningUSuario.anchor = GridBagConstraints.WEST;
		gbc_warningUSuario.insets = new Insets(0, 0, 5, 5);
		gbc_warningUSuario.gridx = 3;
		gbc_warningUSuario.gridy = 4;
		datosEmpresa.add(warningUsuario, gbc_warningUSuario);
		
		lblClave = new JLabel("Clave : ");
		GridBagConstraints gbc_lblClave = new GridBagConstraints();
		gbc_lblClave.anchor = GridBagConstraints.EAST;
		gbc_lblClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblClave.gridx = 1;
		gbc_lblClave.gridy = 5;
		datosEmpresa.add(lblClave, gbc_lblClave);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.anchor = GridBagConstraints.WEST;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 5;
		datosEmpresa.add(txtPassword, gbc_txtPassword);
		
		lblRepetir = new JLabel("Repetir : ");
		GridBagConstraints gbc_lblRepetir = new GridBagConstraints();
		gbc_lblRepetir.anchor = GridBagConstraints.WEST;
		gbc_lblRepetir.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepetir.gridx = 3;
		gbc_lblRepetir.gridy = 5;
		datosEmpresa.add(lblRepetir, gbc_lblRepetir);
		
		txtPassword2 = new JPasswordField();
		txtPassword2.setColumns(10);
		GridBagConstraints gbc_txtPassword2 = new GridBagConstraints();
		gbc_txtPassword2.anchor = GridBagConstraints.WEST;
		gbc_txtPassword2.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword2.gridx = 4;
		gbc_txtPassword2.gridy = 5;
		datosEmpresa.add(txtPassword2, gbc_txtPassword2);
		
		warningClave = new JLabel("*");
		warningClave.setForeground(Color.RED);
		GridBagConstraints gbc_warningClave = new GridBagConstraints();
		gbc_warningClave.anchor = GridBagConstraints.WEST;
		gbc_warningClave.insets = new Insets(0, 0, 5, 0);
		gbc_warningClave.gridx = 5;
		gbc_warningClave.gridy = 5;
		datosEmpresa.add(warningClave, gbc_warningClave);
		
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFields()) {
						boolean registrado=false;
						registrado = ControladorEmpresas.getUnicaInstancia().registrarEmpresa(
										txtNombre.getText(),
										txtDomicilio.getText(),
										txtCIF.getText(),
										txtUsuario.getText(),
										new String(txtPassword.getPassword()));
						if (registrado) {
							JOptionPane.showMessageDialog(
										ventana,
										"Empresa registrada correctamente.",
										"Registro",
										JOptionPane.INFORMATION_MESSAGE);
							ventana.setContentPane(jpanelAnterior);
							ventana.revalidate();
						} else JOptionPane.showMessageDialog(ventana,
								"No se ha podido llevar a cabo el registro.\n",
								"Registro",
								JOptionPane.ERROR_MESSAGE);
						ventana.setTitle("Login Gestor Eventos");	
				} //if OK
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					ventana.setContentPane(jpanelAnterior);
					ventana.setTitle("Login Gestor Eventos");	
					ventana.revalidate();
			}
		});
		
		GridBagConstraints gbc_btnRegistrar = new GridBagConstraints();
		gbc_btnRegistrar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegistrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistrar.gridx = 2;
		gbc_btnRegistrar.gridy = 7;
		datosEmpresa.add(btnRegistrar, gbc_btnRegistrar);
		
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 7;
		datosEmpresa.add(btnCancelar, gbc_btnCancelar);
		
		
		warningAll = new JLabel("* Las campos indicados son obligatorios");
		warningAll.setForeground(Color.RED);
		GridBagConstraints gbc_warningAll = new GridBagConstraints();
		gbc_warningAll.anchor = GridBagConstraints.WEST;
		gbc_warningAll.gridwidth = 3;
		gbc_warningAll.insets = new Insets(0, 0, 5, 5);
		gbc_warningAll.gridx = 2;
		gbc_warningAll.gridy = 8;
		datosEmpresa.add(warningAll, gbc_warningAll);
		
		warningClave2 = new JLabel("* Las dos claves deben coincidir");
		warningClave2.setForeground(Color.RED);
		GridBagConstraints gbc_warningClave2 = new GridBagConstraints();
		gbc_warningClave2.anchor = GridBagConstraints.WEST;
		gbc_warningClave2.gridwidth = 3;
		gbc_warningClave2.insets = new Insets(0, 0, 5, 5);
		gbc_warningClave2.gridx = 2;
		gbc_warningClave2.gridy = 9;
		datosEmpresa.add(warningClave2, gbc_warningClave2);
		
		warningExiste = new JLabel("* El usuario ya existe");
		warningExiste.setForeground(Color.RED);
		GridBagConstraints gbc_warningExiste = new GridBagConstraints();
		gbc_warningExiste.gridwidth = 3;
		gbc_warningExiste.anchor = GridBagConstraints.WEST;
		gbc_warningExiste.insets = new Insets(0, 0, 0, 5);
		gbc_warningExiste.gridx = 2;
		gbc_warningExiste.gridy = 10;
		datosEmpresa.add(warningExiste, gbc_warningExiste);

		ocultarErrores();
		ventana.setContentPane(this);	
		
		ventana.revalidate();
		ventana.repaint();
	} /*constructor*/
	
	/**
	 * Comprueba que los campos de registro est�n bien
	 */
	private boolean checkFields() {
		boolean ok=true;
		/*borrar todos los errores en pantalla*/
		ocultarErrores();
		
		if (txtNombre.getText().trim().isEmpty()) {
			warningNombre.setVisible(true); 
			ok=false;
		}
		if (txtDomicilio.getText().trim().isEmpty()) {
			warningDomicilio.setVisible(true); 
			ok=false;
		}
		if (txtCIF.getText().trim().isEmpty()) {
			warningCIF.setVisible(true); 
			ok=false;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			warningUsuario.setVisible(true); ok=false;
		}
		String password = new String(txtPassword.getPassword());
		String password2 = new String(txtPassword2.getPassword());
		
		if (password.equals("")) {
			warningClave.setVisible(true); 
			ok=false;
		}
		if (!ok) warningAll.setVisible(true);
		
		if (ok && !password.equals(password2)) {
			warningClave2.setVisible(true);
			ok=false;
		}
		/* Comprobar que no exista otro usuario con igual login */
		if (ok && ControladorEmpresas.getUnicaInstancia().esEmpresaRegistrado(txtUsuario.getText())) {
			warningExiste.setVisible(true); ok=false;
		}
		return ok;
	}
	
	/**
	 * Oculta todos los errores que pueda haber en la pantalla
	 */
	private void ocultarErrores() {
		warningAll.setVisible(false);
		warningCIF.setVisible(false);
		warningClave.setVisible(false);
		warningClave2.setVisible(false);
		warningDomicilio.setVisible(false);
		warningNombre.setVisible(false);
		warningUsuario.setVisible(false);
		warningExiste.setVisible(false);
	}


}
