package modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorProductoDAO;


/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaría
 * directamente la base de datos
 */
public class CatalogoProductos {
	private Map<String,Producto> productos; 
	private static CatalogoProductos unicaInstancia = new CatalogoProductos();
	
	private FactoriaDAO dao;
	private IAdaptadorProductoDAO adaptadorProducto;
	
	private CatalogoProductos() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorProducto = dao.getProductoDAO();
  			productos = new HashMap<String,Producto>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoProductos getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todos los Productos*/
	public List<Producto> getProductos(){
		ArrayList<Producto> lista = new ArrayList<Producto>();
		for (Producto c:productos.values()) 
			lista.add(c);
		return lista;
	}
	
	public Producto getProducto(int codigo) {
		for (Producto p : productos.values()) {
			if (p.getCodigo()==codigo) return p;
		}
		return null;
	}
	public Producto getProducto(String nombre) {
		return productos.get(nombre); 
	}
	
	public void addProducto(Producto pro) {
		productos.put(pro.getNombre(),pro);
	}
	public void removeProducto(Producto pro) {
		productos.remove(pro.getNombre());
	}
	
	/*Recupera todos los Productos para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Producto> productosBD = adaptadorProducto.recuperarTodosProductos();
		 for (Producto pro: productosBD) 
			     productos.put(pro.getNombre(),pro);
	}
	
}
