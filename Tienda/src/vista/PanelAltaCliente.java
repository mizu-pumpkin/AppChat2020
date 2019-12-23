package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorTienda;

@SuppressWarnings("serial")
public class PanelAltaCliente extends JPanel {
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private JLabel rotulo;
	private JPanel datosCliente;
	private JLabel ldni, lnombre, lalerta;
	private JTextField dni, nombre;
	private JButton btnRegistrar, btnCancelar;
	private VentanaMain ventana;
	
	
	public PanelAltaCliente(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}

	private void crearPantalla() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		rotulo=new JLabel("Alta Cliente",JLabel.CENTER);
		fixedSize(rotulo,Constantes.x_size,60);
		rotulo.setFont(fuenteGrande);
		add(rotulo,BorderLayout.NORTH);

		datosCliente=new JPanel();
		datosCliente.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		ldni=new JLabel("Dni:",JLabel.RIGHT); 
		fixedSize(ldni,170,24);
		dni=new JTextField(); 
		fixedSize(dni,300,24); 
	
		lnombre=new JLabel("Nombre:",JLabel.RIGHT);	
		fixedSize(lnombre,170,24);
		nombre=new JTextField(); 
		fixedSize(nombre,300,24); 
	
		btnRegistrar=new JButton("Registrar"); fixedSize(btnRegistrar,100,30);
		btnCancelar=new JButton("Cancelar"); fixedSize(btnCancelar,100,30);
		
		lalerta=new JLabel("Los campos Dni y Nombre son obligtorios",JLabel.CENTER);
		lalerta.setForeground(Color.RED); fixedSize(lalerta,Constantes.x_size,30);
		lalerta.setVisible(false);
		
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size,75)));
		datosCliente.add(ldni); datosCliente.add(dni);
		datosCliente.add(lnombre); datosCliente.add(nombre);
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size,75)));
		datosCliente.add(Box.createRigidArea(new Dimension(170,20)));
		datosCliente.add(btnRegistrar);
		datosCliente.add(Box.createRigidArea(new Dimension(90,20)));
		datosCliente.add(btnCancelar);
		datosCliente.add(lalerta);
		
		add(datosCliente,BorderLayout.CENTER);
		
		//Manejadores
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auxDni=dni.getText().trim();	
				String auxNombre=nombre.getText().trim();
				if (auxDni.isEmpty()||auxNombre.isEmpty()) lalerta.setVisible(true);
				else { lalerta.setVisible(false);
					   ControladorTienda.getUnicaInstancia().registrarCliente(auxDni, auxNombre);
					   JOptionPane.showMessageDialog(ventana,
								"Cliente dado de alta correctamente",
								"Registrar cliente",JOptionPane.PLAIN_MESSAGE);
					   dni.setText(""); nombre.setText(""); lalerta.setVisible(false); 
				}
			}	
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dni.setText(""); nombre.setText(""); lalerta.setVisible(false); 
			}
		});
	}
		
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
