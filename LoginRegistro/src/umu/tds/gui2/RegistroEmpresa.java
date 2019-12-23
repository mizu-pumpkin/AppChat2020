package umu.tds.gui2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import umu.tds.controlador.ControladorEmpresas;

public class RegistroEmpresa extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int ANCHOW=400;
	static final int ALTOW=320;
	
	private JFrame ventana;
	private JLabel lblNombre;
	private JLabel lblDomicilio;
	private JLabel lblCIF;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	private JTextField txtNombre;
	private JTextField txtDomicilio;
	private JTextField txtCIF;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;
	private JButton btnRegistrar;
	private JButton btnVolver;
	
	private JPanel jpanelAnterior;
	private JLabel lblNombreError;
	private JLabel lblDomicilioError;
	private JLabel lblCIFError;
	private JLabel lblUsuarioError;
	private JLabel lblPasswordError;
	
	public RegistroEmpresa(JFrame frame){
		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		
		setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel datosPersonales = new JPanel ();
		datosPersonales.setLayout(new BoxLayout(datosPersonales,BoxLayout.Y_AXIS));
		tabbedPane.addTab("Datos Empresa", null, datosPersonales, null);
		
		JPanel linea_1=new JPanel(); /*Nombre*/
		linea_1.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_1,ANCHOW,25);
		linea_1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_2=new JPanel(); /*Domicilio*/
		linea_2.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_2,ANCHOW,25);
		linea_2.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_3=new JPanel(); /*CIF*/
		linea_3.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_3,ANCHOW,25);
		linea_3.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		
		JPanel linea_4=new JPanel(); /*Usuario*/
		linea_4.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_4,ANCHOW,25);
		linea_4.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_5=new JPanel(); /*Password y passwordchk*/
		linea_5.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_5,ANCHOW,25);
		linea_5.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		
		JPanel linea_6=new JPanel(); /*Botones*/
		linea_6.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_6,ANCHOW,50);
		linea_6.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		/*linea 1*/
		lblNombre = new JLabel("Nombre :",JLabel.RIGHT); fixedSize(lblNombre,75,20);
		txtNombre = new JTextField(); fixedSize(txtNombre,270,20);
		lblNombreError=new JLabel("El nombre es obligatorio",JLabel.RIGHT); fixedSize(lblNombreError,224,15);
		lblNombreError.setForeground(Color.RED);
		linea_1.add(lblNombre); linea_1.add(txtNombre);
		
		/*linea 2*/
		lblDomicilio = new JLabel("Domicilio :",JLabel.RIGHT); fixedSize(lblDomicilio,75,20);
		txtDomicilio = new JTextField(); fixedSize(txtDomicilio,270,20);
		lblDomicilioError=new JLabel("Los Domicilio son obligatorios",JLabel.RIGHT); fixedSize(lblDomicilioError,255,15);
		lblDomicilioError.setForeground(Color.RED);
		linea_2.add(lblDomicilio); linea_2.add(txtDomicilio);
		
		/*linea 3*/
		lblCIF = new JLabel("CIF :",JLabel.RIGHT); fixedSize(lblCIF,75,20);
		txtCIF = new JTextField(); fixedSize(txtCIF,125,20);
		lblCIFError=new JLabel("El CIF es obligatorio",JLabel.LEFT); fixedSize(lblCIFError,150,15);
		lblCIFError.setForeground(Color.RED);
		linea_3.add(lblCIF); linea_3.add(txtCIF); linea_3.add(lblCIFError);
		
		
		/*linea 4*/
		lblUsuario = new JLabel("Usuario :",JLabel.RIGHT); fixedSize(lblUsuario,75,20);
		txtUsuario = new JTextField(); fixedSize(txtUsuario,125,20);
		lblUsuarioError=new JLabel("El usuario ya existe",JLabel.LEFT); fixedSize(lblUsuarioError,150,15);
		lblUsuarioError.setForeground(Color.RED);
		linea_4.add(lblUsuario); linea_4.add(txtUsuario); linea_4.add(lblUsuarioError);
		
		/*linea 5*/
		lblPassword = new JLabel("Password :",JLabel.RIGHT); fixedSize(lblPassword,75,20);
		txtPassword = new JPasswordField(); fixedSize(txtPassword,100,20);
		lblPasswordChk = new JLabel("Otra vez:",JLabel.RIGHT); fixedSize(lblPasswordChk,60,20);
		txtPasswordChk = new JPasswordField(); fixedSize(txtPasswordChk,100,20);
		lblPasswordError=new JLabel("Error al introducir las contraseñas",JLabel.CENTER); fixedSize(lblPasswordError,ANCHOW,15);
		lblPasswordError.setForeground(Color.RED);
		linea_5.add(lblPassword); linea_5.add(txtPassword); linea_5.add(lblPasswordChk); linea_5.add(txtPasswordChk); 
		
		
		/*linea 6*/
		btnVolver= new JButton("Volver"); fixedSize(btnVolver,90,30);
		btnRegistrar= new JButton("Registrar"); fixedSize(btnRegistrar,90,30);
		linea_6.add(Box.createHorizontalStrut(75)); linea_6.add(btnVolver);
		linea_6.add(Box.createHorizontalStrut(80)); linea_6.add(btnRegistrar);
		
		ocultarErrores();
		
		datosPersonales.add(Box.createRigidArea(new Dimension(20, 20)));
		datosPersonales.add(linea_1);
		datosPersonales.add(lblNombreError);
		datosPersonales.add(linea_2);
		datosPersonales.add(lblDomicilioError);
		datosPersonales.add(linea_3);
		datosPersonales.add(linea_4);
		datosPersonales.add(linea_5);
		datosPersonales.add(lblPasswordError);
		datosPersonales.add(Box.createRigidArea(new Dimension(60, 60)));
		datosPersonales.add(linea_6);

		ventana.setContentPane(this);
		ventana.revalidate(); /*redibujar con el nuevo JPanel*/
		
		/*Manejador botón volver*/
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventana.setContentPane(jpanelAnterior);
				ventana.setTitle("Login Gestor Eventos");	
				ventana.revalidate();
			}
		});
		
		/*Manejador botón Registrar*/
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean OK=false;
				OK=checkFields();
				if (OK) {
					//todo bien registrar
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
	} /*constructor*/
	
	/**
	 * Comprueba que los campos de registro estén bien
	 */
	private boolean checkFields() {
		boolean salida=true;
		/*borrar todos los errores en pantalla*/
		ocultarErrores();
		if (txtNombre.getText().trim().isEmpty()) {
			lblNombreError.setVisible(true); salida=false;
		}
		if (txtDomicilio.getText().trim().isEmpty()) {
			lblDomicilioError.setVisible(true); salida=false;
		}
		if (txtCIF.getText().trim().isEmpty()) {
			lblCIFError.setVisible(true); salida=false;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			lblUsuarioError.setText("El usuario es obligatorio");
			lblUsuarioError.setVisible(true); salida=false;
		}
		String password = new String(txtPassword.getPassword());
		String password2 = new String(txtPasswordChk.getPassword());
		if (password.equals("")) {
			lblPasswordError.setText("El password no puede estar vacio");
			lblPasswordError.setVisible(true); salida=false;
		} else if (!password.equals(password2)) {
			lblPasswordError.setText("Los dos passwords no coinciden");
			lblPasswordError.setVisible(true); salida=false;
		}
		/* Comprobar que no exista otro usuario con igual login */
		if (ControladorEmpresas.getUnicaInstancia().esEmpresaRegistrado(txtUsuario.getText())) {
			lblUsuarioError.setText("Ya existe ese usuario");
			lblUsuarioError.setVisible(true); salida=false;
		}
		return salida;
	}
	
	/**
	 * Oculta todos los errores que pueda haber en la pantalla
	 */
	private void ocultarErrores() {
		lblNombreError.setVisible(false);
		lblDomicilioError.setVisible(false);
		lblCIFError.setVisible(false);
		lblUsuarioError.setVisible(false);
		lblPasswordError.setVisible(false);
	}
	/**
	 * Fija el tamaño de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d= new Dimension(x,y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}

}
