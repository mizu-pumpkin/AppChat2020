package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class VentanaListados extends JFrame{
	private Font fuenteMediana = new Font("Arial",Font.BOLD,24);
	private Font fuenteCourier = new Font("Courier New",Font.PLAIN,12);
	private JLabel rotulo,piePagina;
	private JTextArea listado;
	
	public VentanaListados(String cabecera){
		super();
		setSize(400,450);
		rotulo=new JLabel(cabecera, JLabel.CENTER);
		fixedSize(rotulo,400,50); rotulo.setFont(fuenteMediana);
		
		piePagina=new JLabel("",JLabel.CENTER);
		fixedSize(piePagina,400,50);
		
		listado=new JTextArea(); listado.setFont(fuenteCourier);
		listado.setEditable(false);
		JScrollPane contenedorListado = new JScrollPane(listado);
		
		add(rotulo,BorderLayout.NORTH);
		add(contenedorListado,BorderLayout.CENTER);
		add(piePagina,BorderLayout.SOUTH);
		
	}
	
	public JTextArea getTextArea() { return listado; }
	public void setPiePagina(String s) { piePagina.setText(s); }
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
