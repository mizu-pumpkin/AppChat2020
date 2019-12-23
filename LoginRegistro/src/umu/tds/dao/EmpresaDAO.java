package umu.tds.dao;

import java.util.List;

import umu.tds.dominio.Empresa;

public interface EmpresaDAO {
	void create(Empresa empresa);
	boolean delete(Empresa empresa);
	void updatePerfil(Empresa empresa);
	Empresa get(int id);
	List<Empresa> getAll();
}