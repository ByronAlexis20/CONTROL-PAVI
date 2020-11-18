package com.control.pavi.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.control.pavi.model.Empresa;
import javax.persistence.Query;

public class EmpresaDAO extends ClaseDAO {
	@SuppressWarnings("unchecked")
	public List<Empresa> getListaEmpresa(){
		List<Empresa> resultado = new ArrayList<Empresa>();
		Query query = getEntityManager().createNamedQuery("Empresa.findAll");
		query.setHint("javax.persistence.cache.storeMode", "REFRESH");
		resultado = (List<Empresa>) query.getResultList();
		return resultado;
	}
}
