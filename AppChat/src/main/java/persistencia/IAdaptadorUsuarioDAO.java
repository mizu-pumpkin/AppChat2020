package persistencia;

import java.util.List;
import modelo.Usuario;

public interface IAdaptadorUsuarioDAO {

	public void create(Usuario usuario);
	public void delete(Usuario usuario);
	public void update(Usuario usuario);
	public Usuario get(int id);
	public List<Usuario> getAll();
	
}
