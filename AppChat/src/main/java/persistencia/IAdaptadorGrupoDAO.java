package persistencia;

import java.util.List;

import modelo.Grupo;

public interface IAdaptadorGrupoDAO {

	public void create(Grupo grupo);
	public void delete(Grupo grupo);
	public void update(Grupo grupo);
	public Grupo get(int id);
	public List<Grupo> getAll();
	
}
