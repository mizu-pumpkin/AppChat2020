package persistencia;

import java.util.Hashtable;

public class PoolDAO {
	private static PoolDAO instance;
	private Hashtable<Integer, Object> pool;

	private PoolDAO() {
		pool = new Hashtable<Integer, Object>();
	}

	public static PoolDAO getInstance() {
		if (instance == null)
			instance = new PoolDAO();
		
		return instance;
	}
	
	public Object getObject(int id) {
		return pool.get(id); // devuelve null si no encuentra el objeto
	}

	public void addObject(int id, Object object) {
		pool.put(id, object);
	}

	public boolean contains(int id) {
		return pool.containsKey(id);
	}
}
