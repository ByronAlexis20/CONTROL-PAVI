package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Recinto;

public class RecintoDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Recinto> buscarPorPatron(String patron){
		List<Recinto> resultado = new ArrayList<Recinto>();
		Query query = getEntityManager().createNamedQuery("Recinto.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Recinto>) query.getResultList();
		return resultado;
	}
	@SuppressWarnings("unchecked")
	public List<Recinto> buscarPorIdParroquia(Integer id){
		List<Recinto> resultado = new ArrayList<Recinto>();
		Query query = getEntityManager().createNamedQuery("Recinto.bucarIdParroquia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", id);
		resultado = (List<Recinto>) query.getResultList();
		return resultado;
	}
}
