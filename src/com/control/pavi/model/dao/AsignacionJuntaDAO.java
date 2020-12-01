package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.AsignacionJunta;


public class AsignacionJuntaDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<AsignacionJunta> buscarPordelegadoJunta(Integer idRepresentante) {
		List<AsignacionJunta> resultado = new ArrayList<AsignacionJunta>();
		Query query = getEntityManager().createNamedQuery("AsignacionJunta.buscarDelegadoJunta");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idRep", idRepresentante);
		resultado = (List<AsignacionJunta>) query.getResultList();
		return resultado;
	}
}
