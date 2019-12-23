package controlador;

import java.util.Date;
import java.util.List;

import modelo.CatalogoClientes;
import modelo.CatalogoProductos;
import modelo.CatalogoVentas;
import modelo.Cliente;
import modelo.Producto;
import modelo.Venta;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorClienteDAO;
import persistencia.IAdaptadorProductoDAO;
import persistencia.IAdaptadorVentaDAO;

public class ControladorTienda {

	private static ControladorTienda unicaInstancia;

	private IAdaptadorClienteDAO adaptadorCliente;
	private IAdaptadorProductoDAO adaptadorProducto;
	private IAdaptadorVentaDAO adaptadorVenta;

	private CatalogoClientes catalogoClientes;
	private CatalogoVentas catalogoVentas;
	private CatalogoProductos catalogoProductos;

	private Venta ventaActual;

	private ControladorTienda() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error
								  // de sincronización
		inicializarCatalogos();
	}

	public static ControladorTienda getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorTienda();
		return unicaInstancia;
	}

	public void registrarCliente(String dni, String nombre) {
		// No se controla que existan dnis duplicados
		Cliente cliente = new Cliente(dni, nombre);
		adaptadorCliente.registrarCliente(cliente);
		catalogoClientes.addCliente(cliente);
	}

	public void registrarProducto(double precio, String nombre, String descripcion) {
		// No se controla que el valor del string precio sea un double
		Producto producto = new Producto(precio, nombre, descripcion);
		adaptadorProducto.registrarProducto(producto);

		catalogoProductos.addProducto(producto);
	}

	public void crearVenta() {
		ventaActual = new Venta();
	}
	
	public void anadirLineaVenta(int unidades, Producto producto) {
		ventaActual.addLineaVenta(unidades, producto);
	}

	public void registrarVenta(String dni, Date fecha) {
		Cliente cliente = catalogoClientes.getCliente(dni);
		ventaActual.setCliente(cliente);
		ventaActual.setFecha(fecha);

		adaptadorVenta.registrarVenta(ventaActual);

		catalogoVentas.addVenta(ventaActual);

		cliente.addVenta(ventaActual);
		adaptadorCliente.modificarCliente(cliente);
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorCliente = factoria.getClienteDAO();
		adaptadorProducto = factoria.getProductoDAO();
		adaptadorVenta = factoria.getVentaDAO();
	}

	private void inicializarCatalogos() {
		catalogoClientes = CatalogoClientes.getUnicaInstancia();
		catalogoVentas = CatalogoVentas.getUnicaInstancia();
		catalogoProductos = CatalogoProductos.getUnicaInstancia();
	}

	public boolean existeCliente(String dni) {
		return CatalogoClientes.getUnicaInstancia().getCliente(dni) != null;
	}

	public List<Producto> getProductos() {
		return catalogoProductos.getProductos();
	}
}
