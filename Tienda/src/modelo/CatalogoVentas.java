package modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorVentaDAO;

/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaría
 * directamente la base de datos
 */
public class CatalogoVentas {
	private Map<Integer,Venta> ventas;
	private static CatalogoVentas unicaInstancia = new CatalogoVentas();
	
	private FactoriaDAO dao;
	private IAdaptadorVentaDAO adaptadorVenta;
	
	private CatalogoVentas() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorVenta = dao.getVentaDAO();
  			ventas = new HashMap<Integer,Venta>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	public static CatalogoVentas getUnicaInstancia(){
		return unicaInstancia;
	}
	
	public List<Venta> getAllVentas(){
		ArrayList<Venta> lista = new ArrayList<Venta>();
		for (Venta venta:ventas.values()) 
			lista.add(venta);
		return lista;
	}
	
	public Venta getVenta(int key) {
		return ventas.get(key); 
	}
	
	public List<Venta> getVentasPeriodo (Date inicio, Date fin) {
		Collection<Venta> ventasAll = this.ventas.values();
		List<Venta> ventasPeriodo = new ArrayList<Venta>();
		for (Venta venta:ventasAll)
			if (venta.esEnPeriodo(inicio,fin))
			    ventasPeriodo.add(venta);    
		return ventasPeriodo;
	}
	
	public void addVenta(Venta venta) {
		ventas.put(venta.getCodigo(),venta);
	}
	public void removeVenta (Venta venta) {
		ventas.remove(venta.getCodigo());
	}
	
	/*Recupera todas las ventas para trabajar con ellas en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Venta> ventasBD = adaptadorVenta.recuperarTodasVentas();
		 for (Venta venta: ventasBD) 
			     ventas.put(venta.getCodigo(), venta);
	}
}
