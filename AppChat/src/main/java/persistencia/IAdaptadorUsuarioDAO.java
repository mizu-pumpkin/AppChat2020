package persistencia;

import java.util.List;
import modelo.Usuario;

public interface IAdaptadorUsuarioDAO {

	// CRUD methods
	public void create(Usuario user);
	public Usuario read(int id);
	public void update(Usuario user);
	public void delete(Usuario user);
	
	public List<Usuario> readAll();
	
}
