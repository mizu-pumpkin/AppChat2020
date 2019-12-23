package persistencia;

import modelo.Contacto;

public interface IAdaptadorContactoDAO {

	public void create(Contacto contacto);
	public void delete(Contacto contacto);
	public void update(Contacto contacto);
	public Contacto get(int id);
	
}
