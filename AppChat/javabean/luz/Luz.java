package luz;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Vector;

public class Luz extends Canvas implements Serializable {
	/**
	 * 
	 */
	//propiedades
	private Color color; 				 //color de la luz
	private boolean encendido=false; 	 //propiedad ligada
	private String nombre; 			     //identificador del pulsador
	
	private static Color colorBoton1=new Color(160,160,160);
	private static Color colorBoton2=new Color(200,200,200);
 
	//atributos
	private Vector encendidoListeners = new Vector(); 
	private boolean bPulsado=false; 					//indica si el bot�n est� presionado o no
 
	public Luz() { 
		setSize(30,30); 					//tama�o inicial por defecto del pulsador
		setMinimumSize(new Dimension(30,30));
		repaint();
		//A�adir eventos de rat�n
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				luzPressed(e);
			}
			public void mouseReleased(MouseEvent e) {
				luzReleased(e);
			} });
	}
	
	public void paint(Graphics g) {
		//public void paint(Graphics g) {
		//obtener el tama�o del pulsador
		int ancho=getSize().width;
		int alto=getSize().height;
		//bloquear relaci�n de aspecto
		if (ancho!=alto) {
			if (ancho<alto) alto=ancho;
			else ancho=alto;
		setSize(ancho,alto);
		invalidate();
		//repaint();
		}
		//int x=ancho/6; //grosor del bot�n
		int grosor=3; //grosor del bot�n
		int anchuraBoton=ancho-grosor;
		//int alturaBoton=alto-x;
		int bordeBoton=anchuraBoton/5;
		//int anchuraLuz=2*bordeBoton-2;
		int anchuraLuz=anchuraBoton-2*bordeBoton;
		int x=0; //desplazamiento;
		if (!bPulsado) { x=0;}
		else {x=grosor;}
		g.setColor(colorBoton1);
		g.fillOval(grosor, grosor, anchuraBoton, anchuraBoton); //dibuja grosor
		g.setColor(Color.BLACK); //dibujar circulos negros
		g.drawOval(grosor, grosor, anchuraBoton-1, anchuraBoton-1);
		g.setColor(colorBoton2);
		g.fillOval(x,x,anchuraBoton,anchuraBoton); //dibuja tapa
		if (encendido) g.setColor(color);
		else g.setColor(getBackground());
		g.fillOval(x+bordeBoton,x+bordeBoton,anchuraLuz,anchuraLuz);
		//dibujar luz
		g.setColor(Color.BLACK); //dibujar circulos negros
		g.drawOval(x,x,anchuraBoton-1,anchuraBoton-1);
		g.drawOval(x+bordeBoton,x+bordeBoton,anchuraLuz-1,anchuraLuz-1);
		//
		g.setColor(getForeground()); //restituir color
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		repaint();
	}

	public boolean isEncendido() {
		return encendido;
	}

	public void setEncendido(boolean newEncendido) {
		boolean oldEncendido=encendido;
		encendido = newEncendido;
		if(oldEncendido!=newEncendido){
			EncendidoEvent event=new EncendidoEvent(this, oldEncendido, newEncendido);
			notificarCambioEncendido(event);
		}
		}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void luzPressed(MouseEvent e){
		bPulsado=true; // repaint();
	}
	public void luzReleased(MouseEvent e){
		if (bPulsado) {
				bPulsado=false;
				if (encendido) setEncendido(false);
				else setEncendido(true);
				repaint();
		}
	}
	public synchronized void addEncendidoListener(IEncendidoListener listener){
		encendidoListeners.addElement(listener);
	}
	public synchronized void removeEncendidoListener(IEncendidoListener listener){
		encendidoListeners.removeElement(listener);
	}
	private void notificarCambioEncendido(EncendidoEvent evento){
		Vector lista;
		synchronized(this){
			lista=(Vector) encendidoListeners.clone();
		}
		for(int i=0; i<lista.size(); i++){
			IEncendidoListener listener=(IEncendidoListener)lista.elementAt(i);
			listener.enteradoCambioEncendido(evento);
		}
	}
	public Dimension getPreferredSize() {
		return new Dimension(30, 30);
	}
	//deprecated
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}