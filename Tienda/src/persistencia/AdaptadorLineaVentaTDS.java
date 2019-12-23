package persistencia;

import java.util.ArrayList;
import java.util.Arrays;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.LineaVenta;
import modelo.Producto;

public class AdaptadorLineaVentaTDS implements IAdaptadorLineaVentaDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorLineaVentaTDS unicaInstancia;

	public static AdaptadorLineaVentaTDS getUnicaInstancia() { // patron
																// singleton
		if (unicaInstancia == null)
			return new AdaptadorLineaVentaTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorLineaVentaTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia(); 
	}

	/*
	 * cuando se registra una linea de venta se le asigna un identificador unico
	 */
	public void registrarLineaVenta(LineaVenta lineaVenta) {
		Entidad eLineaVenta;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eLineaVenta = servPersistencia.recuperarEntidad(lineaVenta.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}		
		if (existe) return;


		// registrar primero los atributos que son objetos
		AdaptadorProductoTDS adaptadorProducto = AdaptadorProductoTDS.getUnicaInstancia();
		adaptadorProducto.registrarProducto(lineaVenta.getProducto());

		// crear entidad linea de venta
		eLineaVenta = new Entidad();
		eLineaVenta.setNombre("lineaventa");
		eLineaVenta.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("unidades", String.valueOf(lineaVenta.getUnidades())),
						new Propiedad("producto", String.valueOf(lineaVenta.getProducto().getCodigo())))));
		
		// registrar entidad linea de venta
		eLineaVenta = servPersistencia.registrarEntidad(eLineaVenta);
		// asignar identificador unico. 
		//Se aprovecha el que genera el servicio de persistencia
		lineaVenta.setCodigo(eLineaVenta.getId()); 
	}

	public void borrarLineaVenta(LineaVenta lineaVenta) {
		// No se comprueba integridad con venta
		Entidad eLineaVenta = servPersistencia.recuperarEntidad(lineaVenta.getCodigo());
		servPersistencia.borrarEntidad(eLineaVenta);
	}

	public void modificarLineaVenta(LineaVenta lineaVenta) {
		Entidad eLineaVenta;

		eLineaVenta = servPersistencia.recuperarEntidad(lineaVenta.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eLineaVenta, "unidades");
		servPersistencia.anadirPropiedadEntidad(eLineaVenta, "unidades", String.valueOf(lineaVenta.getUnidades()));
		servPersistencia.eliminarPropiedadEntidad(eLineaVenta, "producto");
		servPersistencia.anadirPropiedadEntidad(eLineaVenta, "producto",
				String.valueOf(lineaVenta.getProducto().getCodigo()));
	}

	public LineaVenta recuperarLineaVenta(int codigo) {
		Entidad eLineaVenta;
		int unidades;
		Producto producto;

		eLineaVenta = servPersistencia.recuperarEntidad(codigo);
		unidades = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eLineaVenta, "unidades"));

		// Para recuperar el producto se lo solicita al adaptador producto
		AdaptadorProductoTDS adaptadorProducto = AdaptadorProductoTDS.getUnicaInstancia();
		producto = adaptadorProducto.recuperarProducto(
				Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eLineaVenta, "producto")));

		LineaVenta lineaVenta = new LineaVenta(unidades, producto);
		lineaVenta.setCodigo(codigo);
		return lineaVenta;
	}

}
