package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controlador.ControladorTienda;

@SuppressWarnings("serial")
public class PanelAltaProducto extends JPanel {
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private JLabel rotulo;
	private JPanel datosProducto;
	private JLabel lnombre, ldescripcion, lprecio, lalerta;
	private JTextField nombre, precio;
	private JTextArea descripcion;
	private JButton btnRegistrar, btnCancelar;
	private VentanaMain ventana;
	
	
	public PanelAltaProducto(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}

	private void crearPantalla() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		rotulo=new JLabel("Alta Producto",JLabel.CENTER);
		fixedSize(rotulo,Constantes.x_size,60);
		rotulo.setFont(fuenteGrande);
		add(rotulo,BorderLayout.NORTH);

		datosProducto=new JPanel();
		datosProducto.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		lnombre=new JLabel("Nombre:",JLabel.RIGHT);	
		fixedSize(lnombre,170,24);
		nombre=new JTextField(); 
		fixedSize(nombre,140,24); 
	
		lprecio=new JLabel("Precio:",JLabel.RIGHT); 
		fixedSize(lprecio,60,24);
		precio=new JTextField(); 
		fixedSize(precio,90,24);

		ldescripcion=new JLabel("Descripcion:",JLabel.RIGHT); 
		fixedSize(ldescripcion,170,24);
		descripcion=new JTextArea(); 
		fixedSize(descripcion,300,100);
		descripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	
		btnRegistrar=new JButton("Registrar"); fixedSize(btnRegistrar,100,30);
		btnCancelar=new JButton("Cancelar"); fixedSize(btnCancelar,100,30);
		
		lalerta=new JLabel("Los campos Nombre y Precio son obligtorios",JLabel.CENTER);
		lalerta.setForeground(Color.RED); fixedSize(lalerta,Constantes.x_size,30);
		lalerta.setVisible(false);
		
		datosProducto.add(Box.createRigidArea(new Dimension(Constantes.x_size,35)));
		datosProducto.add(lnombre); datosProducto.add(nombre);
		datosProducto.add(lprecio); datosProducto.add(precio);
		datosProducto.add(ldescripcion);
		datosProducto.add(descripcion);
		datosProducto.add(Box.createRigidArea(new Dimension(Constantes.x_size,39)));
		datosProducto.add(Box.createRigidArea(new Dimension(170,20)));
		datosProducto.add(btnRegistrar);
		datosProducto.add(Box.createRigidArea(new Dimension(90,20)));
		datosProducto.add(btnCancelar);
		datosProducto.add(lalerta);
		
		add(datosProducto,BorderLayout.CENTER);
		
		//Manejadores

		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				String auxNombre=nombre.getText().trim();
				String auxPrecio=precio.getText().trim();
				double doubleprecio=Double.parseDouble(auxPrecio);
				if (auxNombre.isEmpty()||auxPrecio.isEmpty()) lalerta.setVisible(true);
				else { lalerta.setVisible(false);
					   ControladorTienda.getUnicaInstancia().registrarProducto(doubleprecio, auxNombre,descripcion.getText());
					   JOptionPane.showMessageDialog(ventana,
								"Producto dado de alta",
								"Registrar producto",JOptionPane.PLAIN_MESSAGE);
					   precio.setText(""); nombre.setText(""); 
					   descripcion.setText(""); lalerta.setVisible(false); 
				}
			}	
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				precio.setText(""); nombre.setText("");
				descripcion.setText(""); lalerta.setVisible(false); 
			}
		});
		
	}
		
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
