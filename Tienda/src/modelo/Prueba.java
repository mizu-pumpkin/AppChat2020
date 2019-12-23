package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorClienteDAO;
import persistencia.IAdaptadorProductoDAO;
import persistencia.IAdaptadorVentaDAO;
import persistencia.PoolDAO;

public class Prueba {

	public static void main(String[] args) {
		Producto p1 = new Producto(10.0, "danone", "yogurt desnatado");
		Cliente c1 = new Cliente("123", "Paco Martinez");
		LineaVenta l1 = new LineaVenta(12, p1);
		LineaVenta l2 = new LineaVenta(5, p1);
		LineaVenta otrol1 = new LineaVenta(1, p1);
		Calendar calendario = GregorianCalendar.getInstance();
		calendario.set(2016, 10, 20);
		Venta venta1 = new Venta(calendario.getTime());
		calendario.set(2016, 10, 21);
		Venta venta2 = new Venta(calendario.getTime());
		venta1.setCliente(c1);
		venta1.addLineaVenta(l1);
		venta1.addLineaVenta(l2);

		venta2.setCliente(c1);
		venta2.addLineaVenta(otrol1);
		FactoriaDAO miFactoria = null;

		try {
			miFactoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		IAdaptadorClienteDAO adaptadorCliente = miFactoria.getClienteDAO();
		IAdaptadorVentaDAO adaptadorVenta = miFactoria.getVentaDAO();
		IAdaptadorProductoDAO adaptadorProducto = miFactoria.getProductoDAO();

		// registrar
		adaptadorProducto.registrarProducto(p1);
		System.out.println("--registrado producto con codigo: " + p1.getCodigo());
		adaptadorCliente.registrarCliente(c1);
		System.out.println("--registrado cliente con codigo: " + c1.getCodigo());
		adaptadorVenta.registrarVenta(venta1);
		System.out.println("--registrada venta con codigo: " + venta1.getCodigo());
		c1.addVenta(venta1);
		adaptadorCliente.modificarCliente(c1);
		System.out.println("modificado cliente codigo:" + c1.getCodigo() + "con la venta:" + venta1.getCodigo());
		adaptadorVenta.registrarVenta(venta2);
		System.out.println("--registrada venta2 con codigo: " + venta2.getCodigo());
		c1.addVenta(venta2);
		adaptadorCliente.modificarCliente(c1);
		System.out.println("modificado cliente codigo:" + c1.getCodigo() + "con la venta2:" + venta2.getCodigo());

		System.out.println("prueba POOLcliente");

		System.out.println("a�ado cliente c1 al pool");
		PoolDAO.getUnicaInstancia().addObjeto(12, c1);
		System.out.println("pool contiene c1=" + PoolDAO.getUnicaInstancia().contiene(12));
		// System.exit(0);

		// listar
		Cliente auxC;
		System.out.println("recupero cliente " + c1.getCodigo());
		auxC = adaptadorCliente.recuperarCliente(c1.getCodigo());
		System.out.println("DNI:" + auxC.getDni());
		System.out.println("Nombre" + auxC.getNombre());
		System.out.println("Ventas-----");
		for (Venta v : auxC.getVentas()) {
			System.out.println("codigo:" + v.getCodigo() + "  Total=" + v.getTotal());
		}
	}

}
