package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Zona;

public class ZonaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Zona> buscarActivos(){
		List<Zona> resultado = new ArrayList<Zona>();
		Query query = getEntityManager().createNamedQuery("Zona.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Zona>) query.getResultList();
		return resultado;
	}
}
