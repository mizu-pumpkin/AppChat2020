package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO () {
	}

	@Override
	public IAdaptadorContactoIndividualDAO getAdaptadorContactoIndividualDAO() {
		return AdaptadorContactoIndividualTDS.getInstance();
	}

	@Override
	public IAdaptadorGrupoDAO getAdaptadorGrupoDAO() {
		return AdaptadorGrupoTDS.getInstance();
	}

	@Override
	public IAdaptadorMensajeDAO getAdaptadorMensajeDAO() {
		return AdaptadorMensajeTDS.getInstance();
	}

	@Override
	public IAdaptadorUsuarioDAO getAdaptadorUsuarioDAO() {
		return AdaptadorUsuarioTDS.getInstance();
	}

}
