package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Parroquia;

public class ParroquiaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Parroquia> buscarPorPatron(String patron){
		List<Parroquia> resultado = new ArrayList<Parroquia>();
		Query query = getEntityManager().createNamedQuery("Parroquia.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Parroquia>) query.getResultList();
		return resultado;
	}
}
