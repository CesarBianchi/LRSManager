package br.com.lrsbackup.LRSPersistence.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import br.com.lrsbackup.LRSPersistence.Util.JPAUtil;
import br.com.lrsbackup.LRSPersistence.model.LRSParameter;

public class LRSParameterDAO extends LRSParameter{

	private EntityManager em = JPAUtil.getEntityManager();

	public void newParameter(LRSParameter pParameter) {
		em.getTransaction().begin();
		em.persist(pParameter);
		em.getTransaction().commit();
		em.close();
	}
	
	
	public void updateParameter(LRSParameter pParameter) {
		em.getTransaction().begin();
		em.merge(pParameter);
		em.getTransaction().commit();
		em.close();
	}
	
	public void removeParameter(LRSParameter pParameter) {
		em.getTransaction().begin();
		em.remove(pParameter);
		em.getTransaction().commit();
		em.close();		
	}

	public LRSParameter searchById(Long id) {
		LRSParameter parameter = new LRSParameter();
		
		em.getTransaction().begin();
		parameter = em.find(LRSParameter.class, id); 
		em.close();	
		
		return parameter;
	}
	
	public List<LRSParameter> searchByName(String name) {
		
		List<LRSParameter> listParameter = new ArrayList<>();
		String jpql = "SELECT p FROM LRSParameter p WHERE p.name = :name";
		
		em.getTransaction().begin();
		listParameter = em.createQuery(jpql, LRSParameter.class).setParameter("name", name).getResultList();
		em.close();	
		
		return listParameter;
	}
	
	public List<LRSParameter> getAll(){
		String jpql = "SELECT p FROM LRSParameter p";
		return em.createQuery(jpql, LRSParameter.class)
				.getResultList();
	}
	
}
