package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.AsignacionJunta;


public class AsignacionJuntaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<AsignacionJunta> buscarPordelegadoJunta(Integer idRepresentante) {
		List<AsignacionJunta> resultado = new ArrayList<AsignacionJunta>();
		Query query = getEntityManager().createNamedQuery("AsignacionJunta.buscarDelegadoJunta");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idRep", idRepresentante);
		resultado = (List<AsignacionJunta>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<AsignacionJunta> buscarPorProvincia(Integer id) {
		List<AsignacionJunta> resultado = new ArrayList<AsignacionJunta>();
		Query query = getEntityManager().createNamedQuery("AsignacionJunta.buscarPorProvincia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", id);
		resultado = (List<AsignacionJunta>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<AsignacionJunta> buscarPorCanton(Integer id) {
		List<AsignacionJunta> resultado = new ArrayList<AsignacionJunta>();
		Query query = getEntityManager().createNamedQuery("AsignacionJunta.buscarPorCanton");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", id);
		resultado = (List<AsignacionJunta>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<AsignacionJunta> buscarPorParroquia(Integer id) {
		List<AsignacionJunta> resultado = new ArrayList<AsignacionJunta>();
		Query query = getEntityManager().createNamedQuery("AsignacionJunta.buscarPorParroquia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", id);
		resultado = (List<AsignacionJunta>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<AsignacionJunta> buscarPorRecinto(Integer id) {
		List<AsignacionJunta> resultado = new ArrayList<AsignacionJunta>();
		Query query = getEntityManager().createNamedQuery("AsignacionJunta.buscarPorRecinto");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", id);
		resultado = (List<AsignacionJunta>) query.getResultList();
		return resultado;
	}
}
