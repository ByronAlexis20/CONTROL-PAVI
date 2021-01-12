package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.JuntaVoto;

public class JuntaVotoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarPorRecinto(Integer idRecinto){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.bucarPorRecinto");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", idRecinto);
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarTodosActivo(){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.buscarTodosActivo");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarTodosActivoProvincia(String patron){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.buscarTodosActivoProvincia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarTodosActivoCanton(String patron){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.buscarTodosActivoCanton");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarTodosActivoParroquia(String patron){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.buscarTodosActivoParroquia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarTodosActivoRecinto(String patron){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.buscarTodosActivoRecinto");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<JuntaVoto> buscarPorRecintoGeneroNumero(Integer idRecinto, Integer numero, String genero){
		List<JuntaVoto> resultado = new ArrayList<JuntaVoto>();
		Query query = getEntityManager().createNamedQuery("JuntaVoto.buscarPorRecintoGeneroNumero");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idRecinto", idRecinto);
		query.setParameter("numero", numero);
		query.setParameter("genero", genero);
		resultado = (List<JuntaVoto>) query.getResultList();
		return resultado;
	}
}
