package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Canton;


public class CantonDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Canton> buscarPorPatron(String patron){
		List<Canton> resultado = new ArrayList<Canton>();
		Query query = getEntityManager().createNamedQuery("Canton.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Canton>) query.getResultList();
		return resultado;
	}
}
