package persistencia;

import java.util.List;

import modelo.Mensaje;

public interface IAdaptadorMensajeDAO {

	// CRUD methods
	public void create(Mensaje msg);
	public Mensaje read(int id);
	public void update(Mensaje msg);
	public void delete(Mensaje msg);
	
	public List<Mensaje> readAll();

}
