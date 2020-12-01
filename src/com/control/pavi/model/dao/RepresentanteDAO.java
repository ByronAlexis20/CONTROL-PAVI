package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Representante;

public class RepresentanteDAO extends ClaseDAO{
	//para recuperar usuario
	@SuppressWarnings("unchecked")
	public List<Representante> buscarPorNombreApellido(String nombreApellido){
		List<Representante> resultado = new ArrayList<Representante>();
		Query query = getEntityManager().createNamedQuery("Representante.buscarPorPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + nombreApellido.toLowerCase() + "%");
		resultado = (List<Representante>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Representante> buscarPorCedula(String cedula){
		List<Representante> resultado = new ArrayList<Representante>();
		Query query = getEntityManager().createNamedQuery("Representante.buscarPorCedula");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("cedula", cedula);
		resultado = (List<Representante>) query.getResultList();
		return resultado;
	}
}
