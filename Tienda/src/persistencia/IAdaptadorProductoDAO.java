package persistencia;

import java.util.List;
import modelo.Producto;

public interface IAdaptadorProductoDAO {
	
	public void registrarProducto(Producto producto);
	public void borrarProducto(Producto producto);
	public void modificarProducto(Producto producto);
	public Producto recuperarProducto(int codigo);
	public List<Producto> recuperarTodosProductos();
	

}
