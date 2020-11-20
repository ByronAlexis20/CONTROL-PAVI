package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Provincia;

public class ProvinciaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Provincia> buscarPorPatron(String patron){
		List<Provincia> resultado = new ArrayList<Provincia>();
		Query query = getEntityManager().createNamedQuery("Provincia.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Provincia>) query.getResultList();
		return resultado;
	}
}
