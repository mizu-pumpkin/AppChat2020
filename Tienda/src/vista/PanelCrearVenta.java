package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controlador.ControladorTienda;
import modelo.LineaVenta;
import modelo.Producto;

@SuppressWarnings("serial")
public class PanelCrearVenta extends JPanel implements ActionListener{
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private Date fechaActual=new Date();
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private JLabel rotulo;
	private JPanel datosVenta, introducirLV;
	private JLabel ldni, lfecha, lunidades, lproducto, lalerta;
	private JTextField dni, fecha, unidades;
	private JComboBox<Producto> productoCombo;
	private JTable tabla;
	private DefaultTableModel modelo;
	private JButton btnAddLinea, btnBorrarLinea, btnRegistrar, btnCancelar;
	private VentanaMain ventana;
	
	
	public PanelCrearVenta(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}
	private void crearPantalla() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		//rotulo=new JLabel("Crear Venta",JLabel.CENTER);
		//fixedSize(rotulo,Constantes.x_size,60);
		//rotulo.setFont(fuenteGrande);
		//add(rotulo,BorderLayout.NORTH);

		datosVenta=new JPanel();
		datosVenta.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ldni=new JLabel("Dni cliente:",JLabel.RIGHT);	
		fixedSize(ldni,100,24);
		dni=new JTextField(); 
		fixedSize(dni,80,24); 
	
		lfecha=new JLabel("Fecha:",JLabel.RIGHT); 
		fixedSize(lfecha,275,24);
		fecha=new JTextField(); 
		fixedSize(fecha,80,24);
		fecha.setEditable(false); fecha.setBackground(Color.WHITE); 
		fecha.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		fecha.setText(dateFormat.format(fechaActual));
		
		introducirLV=new JPanel();
		introducirLV.setLayout(new FlowLayout(FlowLayout.LEFT,10,1));
		fixedSize(introducirLV,Constantes.x_size-100,90);
		introducirLV.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Introducir linea de venta"));
		lunidades=new JLabel("Unidades",JLabel.RIGHT); fixedSize(lunidades,60,12);
		lproducto=new JLabel("Producto",JLabel.RIGHT); fixedSize(lproducto,100,12);
		unidades=new JTextField(); fixedSize(unidades,70,24);
		unidades.setHorizontalAlignment(JTextField.RIGHT);
		productoCombo=new JComboBox<Producto>();
		cargarProductos(); fixedSize(productoCombo,140,24);
		btnAddLinea= new JButton("Añadir a la venta"); fixedSize(btnAddLinea,130,30);
		btnBorrarLinea= new JButton("Quitar linea"); fixedSize(btnBorrarLinea,90,40);
		btnBorrarLinea.setBorder(BorderFactory.createLineBorder(Color.RED));
			
		introducirLV.add(lunidades);introducirLV.add(lproducto);
		introducirLV.add(Box.createRigidArea(new Dimension(300,5)));
		introducirLV.add(unidades); introducirLV.add(productoCombo);
		introducirLV.add(btnAddLinea); 
		introducirLV.add(Box.createRigidArea(new Dimension(25,5)));
		introducirLV.add(btnBorrarLinea);
		
		crearTabla();
		
		JScrollPane tablaConScroll= new JScrollPane(tabla);
		fixedSize(tablaConScroll,Constantes.x_size-100,200);
		
		btnRegistrar=new JButton("Registrar"); fixedSize(btnRegistrar,100,30);
		btnCancelar=new JButton("Cancelar"); fixedSize(btnCancelar,100,30);
		
		
		datosVenta.add(Box.createRigidArea(new Dimension(Constantes.x_size,5)));
		datosVenta.add(ldni); datosVenta.add(dni);
		datosVenta.add(lfecha); datosVenta.add(fecha);
		datosVenta.add(Box.createRigidArea(new Dimension(70,12)));
		datosVenta.add(Box.createRigidArea(new Dimension(25,12)));
		datosVenta.add(introducirLV);
		datosVenta.add(Box.createRigidArea(new Dimension(40,20)));
		datosVenta.add(Box.createRigidArea(new Dimension(26,5)));
		datosVenta.add(tablaConScroll);
		datosVenta.add(Box.createRigidArea(new Dimension(40,20)));
		datosVenta.add(Box.createRigidArea(new Dimension(26,5)));
		datosVenta.add(btnRegistrar);
		datosVenta.add(Box.createRigidArea(new Dimension(320,5)));
		datosVenta.add(btnCancelar);
		
		add(datosVenta, BorderLayout.CENTER);
		
		//manejadores
		btnAddLinea.addActionListener(this);
		btnBorrarLinea.addActionListener(this);
		btnCancelar.addActionListener(this);
		btnRegistrar.addActionListener(this);
	}
	
	//almacena los productos en la ComboBox
	public void cargarProductos() {
		List<Producto> listaproductos= ControladorTienda.getUnicaInstancia().getProductos();
		productoCombo.removeAllItems();
		for(Producto p:listaproductos) productoCombo.addItem(p);
	}
	
	private void crearTabla() {
		tabla= new JTable();
		//tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setCellSelectionEnabled(false);
		tabla.setShowGrid(true);
		tabla.setShowVerticalLines(true);
		tabla.setGridColor(Color.gray);

		modelo = new DefaultTableModel() {
			private String[] columnNames = {"Unidades","Producto","Precio unidad","Subtotal"};
			public String getColumnName(int column) {
			    return columnNames[column];
			}
		    public int getColumnCount() {return 4;}
		    public boolean isCellEditable(int row, int col){ return false;}
		};
		tabla.setModel(modelo);
		TableColumn columna = tabla.getColumn("Unidades"); 
		columna.setPreferredWidth(70);
		columna.setMinWidth(70);
		columna.setMaxWidth(70);
		columna = tabla.getColumn("Producto"); 
		columna.setPreferredWidth(250);
		columna.setMinWidth(250);
		columna.setMaxWidth(250);

	}
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		unidades.setBackground(Color.WHITE);
		dni.setBackground(Color.WHITE);
		if (e.getSource()== btnAddLinea) {
			if (unidades.getText().trim().isEmpty()) {
				unidades.setBackground(Color.ORANGE);
				return;
			}
			Object [] nuevaFila = new Object[4];
			Producto auxProducto=(Producto) productoCombo.getSelectedItem();
			Integer auxUnidades=Integer.parseInt(unidades.getText());
			
			ControladorTienda.getUnicaInstancia().anadirLineaVenta(auxUnidades,auxProducto);
			
			double total=auxUnidades*auxProducto.getPrecio();
			nuevaFila[0]=auxUnidades; 				//Integer
			nuevaFila[1]=auxProducto;	      		//Producto
			nuevaFila[2]=auxProducto.getPrecio();	//Double
			nuevaFila[3]=total; 					//Double
			modelo.addRow(nuevaFila);
			unidades.setText("");
		    
			
			return;
			}
		if (e.getSource()== btnBorrarLinea) {
			if (modelo.getRowCount()>0)
				modelo.removeRow(modelo.getRowCount()-1);
			return;
		}
		if (e.getSource()== btnCancelar) {
			while(modelo.getRowCount()>0) modelo.removeRow(0);
			unidades.setText("");
			dni.setText("");
			return;
		}
		if (e.getSource()== btnRegistrar) {
			if (dni.getText().trim().isEmpty()) {
				dni.setBackground(Color.ORANGE);
				return;
			}
			if (modelo.getRowCount()==0) return;
			if (!(ControladorTienda.getUnicaInstancia().existeCliente(dni.getText()))) {
				JOptionPane.showMessageDialog(ventana,
						"No existe un cliente con ese DNI",
						"Registrar venta",JOptionPane.ERROR_MESSAGE);
				return;
			}
			ControladorTienda.getUnicaInstancia().registrarVenta(dni.getText(),fechaActual);
			JOptionPane.showMessageDialog(ventana,
					"Venta registrada correctamente",
					"Crear venta",JOptionPane.PLAIN_MESSAGE);
			while(modelo.getRowCount()>0) modelo.removeRow(0);
			unidades.setText("");
			dni.setText("");
			ControladorTienda.getUnicaInstancia().crearVenta();
		}
	}
}
