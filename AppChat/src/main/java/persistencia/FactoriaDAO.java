package persistencia;

//Define una factoria abstracta que devuelve todos los DAO de la aplicacion

public abstract class FactoriaDAO {
	private static FactoriaDAO instance;
	
	public static final String DAO_TDS = "persistencia.TDSFactoriaDAO";
		
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	public static FactoriaDAO getInstance(String tipo) throws DAOException{
		if (instance == null)
			try { instance=(FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
			} 
		return instance;
	}


	public static FactoriaDAO getInstance() throws DAOException{
			if (instance == null) return getInstance (FactoriaDAO.DAO_TDS);
					else return instance;
		}

	/* Constructor */
	protected FactoriaDAO (){}
		
	// Metodos factoria que devuelven adaptadores que implementen estos interfaces
	public abstract IAdaptadorContactoIndividualDAO getAdaptadorContactoIndividualDAO();
	public abstract IAdaptadorGrupoDAO getAdaptadorGrupoDAO();
	public abstract IAdaptadorMensajeDAO getAdaptadorMensajeDAO();
	public abstract IAdaptadorUsuarioDAO getAdaptadorUsuarioDAO();

}
