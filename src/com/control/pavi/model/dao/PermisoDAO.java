package com.control.pavi.model.dao;

import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Permiso;

public class PermisoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Permiso> getPermiso(int idPerfil){
		List<Permiso> resultado; 
		Query query = getEntityManager().createNamedQuery("Permiso.buscarPermiso");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idPerfil);
		resultado = (List<Permiso>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Permiso> getPermisoPerfil(int idPerfil){
		List<Permiso> resultado; 
		Query query = getEntityManager().createNamedQuery("Permiso.buscarPermisoPerfil");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", idPerfil);
		resultado = (List<Permiso>) query.getResultList();
		return resultado;
	}
}
