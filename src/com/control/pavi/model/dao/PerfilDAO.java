package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.Perfil;

public class PerfilDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<Perfil> buscarPorPatron(String patron){
		List<Perfil> resultado = new ArrayList<Perfil>();
		Query query = getEntityManager().createNamedQuery("Perfil.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<Perfil>) query.getResultList();
		return resultado;
	}
}
