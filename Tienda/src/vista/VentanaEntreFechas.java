package vista;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorTienda;

@SuppressWarnings("serial")
public class VentanaEntreFechas extends JFrame implements ActionListener{
	private JLabel linicio,lfin;
	private JTextField inicio,fin;
	private JButton btnVer;
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private VentanaMain ventanaPadre;
	
	public VentanaEntreFechas(VentanaMain v){
		super();
		ventanaPadre=v;
		setSize(450,100);
		JPanel contenido=(JPanel) getContentPane();
		contenido.setLayout(new FlowLayout());
		linicio=new JLabel("F.inicio:");
		lfin=new JLabel("F.fin:");
		inicio=new JTextField();	
		fixedSize(inicio,80,24);
		fin=new JTextField();
		fixedSize(fin,80,24);
		btnVer= new JButton("Ver listado");
		contenido.add(Box.createRigidArea(new Dimension(10,20)));
		contenido.add(linicio); contenido.add(inicio);
		contenido.add(Box.createRigidArea(new Dimension(20,20)));
		contenido.add(lfin); contenido.add(fin);
		contenido.add(Box.createRigidArea(new Dimension(20,20)));
		contenido.add(btnVer);
		
		//manejador
		btnVer.addActionListener(this);
	}
	
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Date f1=null, f2=null;
		try {f1=dateFormat.parse(inicio.getText());
		} catch (ParseException e1) { e1.printStackTrace();	}
		try {f2=dateFormat.parse(fin.getText());
		} catch (ParseException e1) { e1.printStackTrace();	}
		VentanaListados vl=new VentanaListados("Listado "+inicio.getText()+"-"+fin.getText());
		vl.setLocationRelativeTo(ventanaPadre);
		vl.setVisible(true);
		ventanaPadre.listadoVentas(vl.getTextArea(),f1,f2);
		dispose();
	}
}
