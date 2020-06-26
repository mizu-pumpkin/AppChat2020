package persistencia;

import java.util.List;

import modelo.Chat;

public interface IAdaptadorChatDAO {

	// CRUD methods
	public void create(Chat chat);
	public Chat read(int id);
	public void update(Chat chat);
	public void delete(Chat chat);
	
	public List<Chat> readAll();
	
}
