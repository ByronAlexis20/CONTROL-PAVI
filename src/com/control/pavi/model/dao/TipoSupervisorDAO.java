package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.TipoSupervisor;

public class TipoSupervisorDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<TipoSupervisor> buscarPorId(Integer id){
		List<TipoSupervisor> resultado = new ArrayList<TipoSupervisor>();
		Query query = getEntityManager().createNamedQuery("TipoSupervisor.buscarPorId");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("id", id);
		resultado = (List<TipoSupervisor>) query.getResultList();
		return resultado;
	}
}
