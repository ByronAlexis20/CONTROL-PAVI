package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.AsignacionSupervisor;

public class AsignacionSupervisorDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<AsignacionSupervisor> buscarAsignaciones() {
		List<AsignacionSupervisor> resultado = new ArrayList<AsignacionSupervisor>();
		Query query = getEntityManager().createNamedQuery("AsignacionSupervisor.buscarAsignados");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<AsignacionSupervisor>) query.getResultList();
		return resultado;
	}
}
