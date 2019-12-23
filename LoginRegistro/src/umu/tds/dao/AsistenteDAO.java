package umu.tds.dao;

import java.util.List;

import umu.tds.dominio.Asistente;

public interface AsistenteDAO {
	
	void create(Asistente asistente);
	boolean delete(Asistente asistente);
	void updatePerfil(Asistente asistente);
	Asistente get(int id);
	List<Asistente> getAll();
	
}
