package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.SupervisorPrincipal;

public class SupervisorPrincipalDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<SupervisorPrincipal> buscarPorPatron(String patron){
		List<SupervisorPrincipal> resultado = new ArrayList<SupervisorPrincipal>();
		Query query = getEntityManager().createNamedQuery("SupervisorPrincipal.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<SupervisorPrincipal>) query.getResultList();
		return resultado;
	}
}
