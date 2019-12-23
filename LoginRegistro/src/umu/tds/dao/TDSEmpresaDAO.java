package umu.tds.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.dominio.Empresa;

public class TDSEmpresaDAO implements EmpresaDAO {
	
private ServicioPersistencia servPersistencia;
	
	public TDSEmpresaDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private Empresa entidadToEmpresa(Entidad eEmpresa) {
		
		String nombre = servPersistencia.recuperarPropiedadEntidad(eEmpresa, "nombre");
		String domicilio = servPersistencia.recuperarPropiedadEntidad(eEmpresa, "domicilio");
		String cif = servPersistencia.recuperarPropiedadEntidad(eEmpresa, "cif");
		String login = servPersistencia.recuperarPropiedadEntidad(eEmpresa, "login");
		String password = servPersistencia.recuperarPropiedadEntidad(eEmpresa, "password");
		
		Empresa empresa = new Empresa(nombre,domicilio,cif,login,password);
		empresa.setId(eEmpresa.getId());
		return empresa;
	}
	
	private Entidad empresaToEntidad(Empresa empresa) {
		Entidad  eEmpresa = new Entidad();
		eEmpresa.setNombre("Empresa"); 
		
		eEmpresa.setPropiedades(
				new ArrayList<Propiedad>(Arrays.asList(
						new Propiedad("nombre", empresa.getNombre()), 
						new Propiedad("domicilio", empresa.getDomicilio()),
						new Propiedad("cif", empresa.getCif()),
						new Propiedad("login", empresa.getLogin()),
						new Propiedad("password", empresa.getPassword())
						))
				);
		return eEmpresa;
	}

	@Override
	public void create(Empresa empresa) {
		Entidad  eEmpresa = this.empresaToEntidad(empresa);
		eEmpresa = servPersistencia.registrarEntidad(eEmpresa);
		empresa.setId(eEmpresa.getId());
	}

	@Override
	public boolean delete(Empresa empresa) {
		Entidad eEmpresa;
		eEmpresa = servPersistencia.recuperarEntidad(empresa.getId());
		return servPersistencia.borrarEntidad(eEmpresa);
	}

	@Override
	public void updatePerfil(Empresa empresa) {
		Entidad eEmpresa = servPersistencia.recuperarEntidad(empresa.getId());
		servPersistencia.eliminarPropiedadEntidad(eEmpresa, "password");
		servPersistencia.anadirPropiedadEntidad(eEmpresa, "password",empresa.getPassword());
	}

	@Override
	public Empresa get(int id) {
		Entidad eEmpresa = servPersistencia.recuperarEntidad(id);
		return entidadToEmpresa(eEmpresa);
	}

	@Override
	public List<Empresa> getAll() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Empresa");
		
		List<Empresa> empresas  = new LinkedList<Empresa>();
		for (Entidad eEmpresa : entidades) {
			empresas.add(get(eEmpresa.getId()));
		}
		return empresas;
	}

}
