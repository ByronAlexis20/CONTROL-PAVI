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
}
