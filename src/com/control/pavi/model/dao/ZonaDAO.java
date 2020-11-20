package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Zona;

public class ZonaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Zona> buscarPorPatron(String patron){
		List<Zona> resultado = new ArrayList<Zona>();
		Query query = getEntityManager().createNamedQuery("Zona.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Zona>) query.getResultList();
		return resultado;
	}
}
