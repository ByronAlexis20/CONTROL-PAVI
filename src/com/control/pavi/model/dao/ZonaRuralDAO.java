package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.control.pavi.model.ZonaRural;

public class ZonaRuralDAO extends ClaseDAO{
	@SuppressWarnings("unchecked")
	public List<ZonaRural> buscarPorPatron(String patron){
		List<ZonaRural> resultado = new ArrayList<ZonaRural>();
		Query query = getEntityManager().createNamedQuery("ZonaRural.bucarPatron");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("patron", "%" + patron + "%");
		resultado = (List<ZonaRural>) query.getResultList();
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<ZonaRural> buscarPorIdParroquia(Integer idParroquia){
		List<ZonaRural> resultado = new ArrayList<ZonaRural>();
		Query query = getEntityManager().createNamedQuery("ZonaRural.bucarPorIdParroquia");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		query.setParameter("idParroquia", idParroquia );
		resultado = (List<ZonaRural>) query.getResultList();
		return resultado;
	}
}
