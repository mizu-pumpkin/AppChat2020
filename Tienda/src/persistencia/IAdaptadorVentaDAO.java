package persistencia;

import java.util.List;
import modelo.Venta;

public interface IAdaptadorVentaDAO {

	public void registrarVenta(Venta venta);
	public void borrarVenta(Venta venta);
	public void modificarVenta(Venta venta);
	public Venta recuperarVenta(int codigo);
	public List<Venta> recuperarTodasVentas();
}
