package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Supervisor;

public class SupervisorDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Supervisor> buscarPorPatron(String patron){
		List<Supervisor> resultado = new ArrayList<Supervisor>();
		Query query = getEntityManager().createNamedQuery("Supervisor.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Supervisor>) query.getResultList();
		return resultado;
	}
}
