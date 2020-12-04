package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.AsignacionSupervisorPrincipal;

public class AsignacionSupervisorPrincipalDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<AsignacionSupervisorPrincipal> buscarAsignaciones() {
		List<AsignacionSupervisorPrincipal> resultado = new ArrayList<AsignacionSupervisorPrincipal>();
		Query query = getEntityManager().createNamedQuery("AsignacionSupervisorPrincipal.buscarAsignados");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<AsignacionSupervisorPrincipal>) query.getResultList();
		return resultado;
	}
}
