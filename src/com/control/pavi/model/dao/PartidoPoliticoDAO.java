package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.PartidoPolitico;

public class PartidoPoliticoDAO extends ClaseDAO {
	@SuppressWarnings("unchecked")
	public List<PartidoPolitico> buscarPorPatron(String patron){
		List<PartidoPolitico> resultado = new ArrayList<PartidoPolitico>();
		Query query = getEntityManager().createNamedQuery("PartidoPolitico.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<PartidoPolitico>) query.getResultList();
		return resultado;
	}
}
