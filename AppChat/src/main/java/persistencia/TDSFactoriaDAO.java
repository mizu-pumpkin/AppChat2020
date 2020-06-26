package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	
	public TDSFactoriaDAO () {
	}

	@Override
	public IAdaptadorChatDAO getAdaptadorChatDAO() {
		return AdaptadorChatTDS.getInstance();
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
