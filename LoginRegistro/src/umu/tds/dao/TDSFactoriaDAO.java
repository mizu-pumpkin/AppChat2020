package umu.tds.dao;

/** 
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 * 
 */

public final class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO() {	}
	
	@Override
	public TDSAsistenteDAO getAsistenteDAO() {	
		return new TDSAsistenteDAO(); 
	}

	@Override
	public TDSEmpresaDAO getEmpresaDAO() {
		return new TDSEmpresaDAO(); 
		
	}

}
