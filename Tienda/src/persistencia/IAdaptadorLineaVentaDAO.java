package persistencia;

import modelo.LineaVenta;

public interface IAdaptadorLineaVentaDAO {
	
	public void registrarLineaVenta(LineaVenta lineaVenta);
	public void borrarLineaVenta(LineaVenta lineaVenta);
	public void modificarLineaVenta(LineaVenta lineaVenta);
	public LineaVenta recuperarLineaVenta(int codigo);
}
