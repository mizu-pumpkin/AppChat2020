package umu.tds.dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import umu.tds.dao.DAOException;
import umu.tds.dao.FactoriaDAO;

public class CatalogoEmpresas {	
	private FactoriaDAO factoria;
	private static CatalogoEmpresas unicaInstancia;
	
	private HashMap<Integer, Empresa> empresasPorID;
	private HashMap<String, Empresa> empresasPorLogin;

	public static CatalogoEmpresas getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new CatalogoEmpresas();
		return unicaInstancia;
	}

	private CatalogoEmpresas (){
		empresasPorID = new HashMap<Integer, Empresa>();
		empresasPorLogin = new HashMap<String, Empresa>();
		try {
			factoria = FactoriaDAO.getInstancia();
			
			List<Empresa> listaEmpresas = factoria.getEmpresaDAO().getAll();
			for (Empresa empresa : listaEmpresas) {
				empresasPorID.put(empresa.getId(), empresa);
				empresasPorLogin.put(empresa.getLogin(), empresa);
			}
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}
	
	public List<Empresa> getEmpresas() {
		return new LinkedList<Empresa> (empresasPorLogin.values());
	}
	
	public Empresa getEmpresa(String nombre) {
		return empresasPorLogin.get(nombre);
	}

	public Empresa getEmpresa(int id) {
		return empresasPorID.get(id);
	}
	
	public void addEmpresa(Empresa Empresa) {
		empresasPorID.put(Empresa.getId(), Empresa);
		empresasPorLogin.put(Empresa.getLogin(), Empresa);
	}
	
	public void removeEmpresa(Empresa Empresa) {
		empresasPorID.remove(Empresa.getId());
		empresasPorLogin.remove(Empresa.getLogin());
	}

}
