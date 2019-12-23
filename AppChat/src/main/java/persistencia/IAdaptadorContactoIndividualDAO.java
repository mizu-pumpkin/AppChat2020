package persistencia;

import java.util.List;

import modelo.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO {

	public void create(ContactoIndividual cont_ind);
	public void delete(ContactoIndividual cont_ind);
	public void update(ContactoIndividual cont_ind);
	public ContactoIndividual get(int id);
	public List<ContactoIndividual> getAll();
	
}
