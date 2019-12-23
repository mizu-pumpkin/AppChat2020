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

import umu.tds.controlador.ControladorAsistentes;


@SuppressWarnings("serial")
public class RegistroAsistente extends JPanel {
	static final int ANCHOW=400;
	static final int ALTOW=320;
	
	private JFrame ventana;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblDNI;
	private JLabel lblEdad;
	private JLabel lblMovil;
	private JLabel lblEmail;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDNI;
	private JTextField txtEdad;
	private JTextField txtMovil;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;
	private JButton btnRegistrar;
	private JButton btnVolver;
	
	private JPanel jpanelAnterior;
	private JLabel lblNombreError;
	private JLabel lblApellidosError;
	private JLabel lblDNIError;
	private JLabel lblEdadError;
	private JLabel lblMovilError;
	private JLabel lblEmailError;
	private JLabel lblUsuarioError;
	private JLabel lblPasswordError;
	
	public RegistroAsistente(JFrame frame){
		ventana=frame;
		jpanelAnterior = (JPanel) ventana.getContentPane();
		
		setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		add(tabbedPane, BorderLayout.CENTER);
		
		JPanel datosPersonales = new JPanel ();
		datosPersonales.setLayout(new BoxLayout(datosPersonales,BoxLayout.Y_AXIS));
		tabbedPane.addTab("Datos Asistente", null, datosPersonales, null);
		
		JPanel linea_1=new JPanel(); /*Nombre*/
		linea_1.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_1,ANCHOW,25);
		linea_1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_2=new JPanel(); /*Apellidos*/
		linea_2.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_2,ANCHOW,25);
		linea_2.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_3=new JPanel(); /*DNI*/
		linea_3.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_3,ANCHOW,25);
		linea_3.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_4=new JPanel(); /*Edad*/
		linea_4.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_4,ANCHOW,25);
		linea_4.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_5=new JPanel(); /*Email*/ 
		linea_5.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_4,ANCHOW,25);
		linea_5.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_6=new JPanel(); /*Usuario*/
		linea_6.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_5,ANCHOW,25);
		linea_6.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_7=new JPanel(); /*Password y passwordchk*/
		linea_7.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_6,ANCHOW,25);
		linea_7.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_8=new JPanel(); /*Movil*/
		linea_8.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_7,ANCHOW,25);
		linea_8.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JPanel linea_9=new JPanel(); /*Botones*/
		linea_9.setLayout(new FlowLayout(FlowLayout.LEFT)); fixedSize(linea_9,ANCHOW,50);
		linea_9.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		/*linea 1*/
		lblNombre = new JLabel("Nombre :",JLabel.RIGHT); fixedSize(lblNombre,75,20);
		txtNombre = new JTextField(); fixedSize(txtNombre,270,20);
		lblNombreError=new JLabel("El nombre es obligatorio",JLabel.RIGHT); fixedSize(lblNombreError,224,15);
		lblNombreError.setForeground(Color.RED);
		linea_1.add(lblNombre); linea_1.add(txtNombre);
		
		/*linea 2*/
		lblApellidos = new JLabel("Apellidos :",JLabel.RIGHT); fixedSize(lblApellidos,75,20);
		txtApellidos = new JTextField(); fixedSize(txtApellidos,270,20);
		lblApellidosError=new JLabel("Los apellidos son obligatorios",JLabel.RIGHT); fixedSize(lblApellidosError,255,15);
		lblApellidosError.setForeground(Color.RED);
		linea_2.add(lblApellidos); linea_2.add(txtApellidos);
		
		/*linea 3*/
		lblDNI = new JLabel("DNI :",JLabel.RIGHT); fixedSize(lblDNI,75,20);
		txtDNI = new JTextField(); fixedSize(txtDNI,125,20);
		lblDNIError=new JLabel("El DNI es obligatorio",JLabel.LEFT); fixedSize(lblDNIError,150,15);
		lblDNIError.setForeground(Color.RED);
		linea_3.add(lblDNI); linea_3.add(txtDNI); linea_3.add(lblDNIError);
		
		/*linea 4*/
		lblEdad = new JLabel("Edad :",JLabel.RIGHT); fixedSize(lblEdad,75,20);
		txtEdad = new JTextField(); fixedSize(txtEdad,60,20);
		lblEdadError=new JLabel("La edad es obligatoria",JLabel.LEFT); fixedSize(lblEdadError,150,15);
		lblEdadError.setForeground(Color.RED);
		linea_4.add(lblEdad); linea_4.add(txtEdad); linea_4.add(lblEdadError);
		
		/*linea 5*/
		lblEmail = new JLabel("Email :",JLabel.RIGHT); fixedSize(lblEmail,75,20);
		txtEmail = new JTextField(); fixedSize(txtEmail,125,20);
		lblEmailError=new JLabel("El Email es obligatorio",JLabel.LEFT); fixedSize(lblEmailError,150,15);
		lblEmailError.setForeground(Color.RED);
		linea_5.add(lblEmail); linea_5.add(txtEmail); linea_5.add(lblEmailError);
		
		/*linea 6*/
		lblUsuario = new JLabel("Usuario :",JLabel.RIGHT); fixedSize(lblUsuario,75,20);
		txtUsuario = new JTextField(); fixedSize(txtUsuario,125,20);
		lblUsuarioError=new JLabel("El usuario ya existe",JLabel.LEFT); fixedSize(lblUsuarioError,150,15);
		lblUsuarioError.setForeground(Color.RED);
		linea_6.add(lblUsuario); linea_6.add(txtUsuario); linea_6.add(lblUsuarioError);
		
		/*linea 7*/
		lblPassword = new JLabel("Password :",JLabel.RIGHT); fixedSize(lblPassword,75,20);
		txtPassword = new JPasswordField(); fixedSize(txtPassword,100,20);
		lblPasswordChk = new JLabel("Otra vez:",JLabel.RIGHT); fixedSize(lblPasswordChk,60,20);
		txtPasswordChk = new JPasswordField(); fixedSize(txtPasswordChk,100,20);
		lblPasswordError=new JLabel("Error al introducir las contraseñas",JLabel.CENTER); fixedSize(lblPasswordError,ANCHOW,15);
		lblPasswordError.setForeground(Color.RED);
		linea_7.add(lblPassword); linea_7.add(txtPassword); linea_7.add(lblPasswordChk); linea_7.add(txtPasswordChk); 
		
		/*linea 8*/
		lblMovil = new JLabel("Movil :",JLabel.RIGHT); fixedSize(lblMovil,75,20);
		txtMovil = new JTextField(); fixedSize(txtMovil,125,20);
		lblMovilError=new JLabel("Introduce movil",JLabel.LEFT); fixedSize(lblMovilError,150,15);
		lblMovilError.setForeground(Color.RED);
		linea_8.add(lblMovil); linea_8.add(txtMovil); linea_8.add(lblMovilError);
		
		/*linea 9*/
		btnVolver= new JButton("Volver"); fixedSize(btnVolver,90,30);
		btnRegistrar= new JButton("Registrar"); fixedSize(btnRegistrar,90,30);
		linea_9.add(Box.createHorizontalStrut(75)); linea_9.add(btnVolver);
		linea_9.add(Box.createHorizontalStrut(80)); linea_9.add(btnRegistrar);
		
		ocultarErrores();
		
		datosPersonales.add(linea_1);
		datosPersonales.add(lblNombreError);
		datosPersonales.add(linea_2);
		datosPersonales.add(lblApellidosError);
		datosPersonales.add(linea_3);
		datosPersonales.add(linea_4);
		datosPersonales.add(linea_5);
		datosPersonales.add(linea_6);
		datosPersonales.add(linea_7);
		datosPersonales.add(lblPasswordError);
		datosPersonales.add(linea_8);
		datosPersonales.add(lblMovilError);
		datosPersonales.add(linea_9);

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
						boolean registrado=false;
						registrado = ControladorAsistentes.getUnicaInstancia().registrarAsistente(
										txtNombre.getText(),
										txtApellidos.getText(),
										txtDNI.getText(),
										Integer.parseInt(txtEdad.getText()),
										txtMovil.getText(),
										txtEmail.getText(),
										txtUsuario.getText(),
										new String(txtPassword.getPassword()));
						if (registrado) {
							JOptionPane.showMessageDialog(
										ventana,
										"Asistente registrado correctamente.",
										"Registro",
										JOptionPane.INFORMATION_MESSAGE);
							ventana.setContentPane(jpanelAnterior);
							ventana.revalidate();
						} else JOptionPane.showMessageDialog(ventana,
								"No se ha podido llevar a cabo el registro.\n",
								"Registro",
								JOptionPane.ERROR_MESSAGE);
						ventana.setTitle("Login Gestor Eventos");	
				}
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
		if (txtApellidos.getText().trim().isEmpty()) {
			lblApellidosError.setVisible(true); salida=false;
		}
		if (txtDNI.getText().trim().isEmpty()) {
			lblDNIError.setVisible(true); salida=false;
		}
		if (txtEdad.getText().trim().isEmpty()) {
			lblEdadError.setVisible(true); salida=false;
		}
		if (txtEmail.getText().trim().isEmpty()) {
			lblEmailError.setVisible(true); salida=false;
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
		if (ControladorAsistentes.getUnicaInstancia().esAsistenteRegistrado(txtUsuario.getText())) {
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
		lblApellidosError.setVisible(false);
		lblDNIError.setVisible(false);
		lblEdadError.setVisible(false);
		lblMovilError.setVisible(false);
		lblEmailError.setVisible(false);
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