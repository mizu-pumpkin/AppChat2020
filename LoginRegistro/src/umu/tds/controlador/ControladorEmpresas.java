package umu.tds.controlador;

import umu.tds.dao.DAOException;
import umu.tds.dao.EmpresaDAO;
import umu.tds.dao.FactoriaDAO;
import umu.tds.dominio.CatalogoEmpresas;
import umu.tds.dominio.Empresa;

public class ControladorEmpresas {
	private Empresa empresaActual;
	private static ControladorEmpresas unicaInstancia;
	private FactoriaDAO factoria;
	
	private ControladorEmpresas() {	
		empresaActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public static ControladorEmpresas getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new ControladorEmpresas();
		return unicaInstancia;
	}
	
	public Empresa getEmpresaActual() {
		return empresaActual;
	}
	
	public boolean esEmpresaRegistrado(String login) {
		return CatalogoEmpresas.getUnicaInstancia().getEmpresa(login)!=null;
	}
	
	public boolean registrarEmpresa(String nombre,
									String domicilio, 
									String cif,
									String login,
									String password) {

			if (esEmpresaRegistrado(login)) return false;
			Empresa Empresa = new Empresa(nombre,domicilio,cif,login,password);
			
			EmpresaDAO empresaBD = factoria.getEmpresaDAO(); /*Adaptador DAO para almacenar el nuevo Empresa en la BD*/
			empresaBD.create(Empresa);
			
			CatalogoEmpresas.getUnicaInstancia().addEmpresa(Empresa);
			return true;
	}
	
	public boolean loginEmpresa(String nombre,String password) {
		Empresa Empresa = CatalogoEmpresas.getUnicaInstancia().getEmpresa(nombre);
		if (Empresa != null && Empresa.getPassword().equals(password)) {
				this.empresaActual = Empresa;
				return true;
		}
		return false;
	}
	
	public boolean borrarEmpresa(Empresa empresa) {
		if (!esEmpresaRegistrado(empresa.getLogin())) return false;
		
		EmpresaDAO empresaDAO = factoria.getEmpresaDAO();  /*Adaptador DAO para borrar la Empresa de la BD*/
		empresaDAO.delete(empresa);
		
		CatalogoEmpresas.getUnicaInstancia().removeEmpresa(empresa);
		return true;
	}

}
