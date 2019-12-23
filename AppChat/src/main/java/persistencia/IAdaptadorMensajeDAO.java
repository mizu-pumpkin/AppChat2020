package persistencia;

import java.util.List;

import modelo.Mensaje;

public interface IAdaptadorMensajeDAO {

	public void create(Mensaje mensaje);
	public void delete(Mensaje mensaje);
	public void update(Mensaje mensaje);
	public Mensaje get(int id);
	public List<Mensaje> getAll();

}
