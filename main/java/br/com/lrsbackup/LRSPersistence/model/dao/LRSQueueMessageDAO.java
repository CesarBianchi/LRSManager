package br.com.lrsbackup.LRSPersistence.model.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import br.com.lrsbackup.LRSPersistence.Util.JPAUtil;
import br.com.lrsbackup.LRSPersistence.model.LRSQueueMessage;


public class LRSQueueMessageDAO {
	private EntityManager em = JPAUtil.getEntityManager();

	public void newQueue(LRSQueueMessage pQueue) {
		em.getTransaction().begin();
		em.persist(pQueue);
		em.getTransaction().commit();
		em.close();
	}
	
	
	public void updateQueue(LRSQueueMessage pQueue) {
		em.getTransaction().begin();
		em.merge(pQueue);
		em.getTransaction().commit();
		em.close();
	}


	public LRSQueueMessage searchById(Long id) {
		LRSQueueMessage parameter = new LRSQueueMessage();
		
		em.getTransaction().begin();
		parameter = em.find(LRSQueueMessage.class, id); 
		em.close();	
		
		return parameter;
	}
	
	public List<LRSQueueMessage> searchFile(String pFileName) {
		
		List<LRSQueueMessage> listQueue = new ArrayList<>();
		String jpql = "SELECT p FROM LRSQueueMessage p WHERE p.originalFileName = :pFileName";
		
		em.getTransaction().begin();
		listQueue = em.createQuery(jpql, LRSQueueMessage.class).setParameter("pFileName", pFileName).getResultList();
		em.close();	
		
		return listQueue;
	}
	
	public List<LRSQueueMessage> getAllPendings(){
		
		String jpql = "SELECT p FROM LRSQueueMessage p WHERE p.status <> :pStatus ";
		return em.createQuery(jpql, LRSQueueMessage.class)
				.setParameter("pStatus", new LRSQueueMessage().UPLOADED)
				.getResultList();
	}

	
	
	public List<LRSQueueMessage> getAllPendingsOfAWS(){
		return this.getAllPendingsOfCloudType(new LRSQueueMessage().AWS);

	}			
				
	public List<LRSQueueMessage> getAllPendingsOfAzure(){
		return this.getAllPendingsOfCloudType(new LRSQueueMessage().AZURE);
	}
	
	public List<LRSQueueMessage> getAllPendingsOfOracleCloud(){
		return this.getAllPendingsOfCloudType(new LRSQueueMessage().ORACLE_CLOUD);
	}
	
	
	private List<LRSQueueMessage> getAllPendingsOfCloudType(String pCloudType){
		
		String jpql = "SELECT p FROM LRSQueueMessage p WHERE p.status <> :pStatus AND p.cloudProvider = :pCloud";
		return em.createQuery(jpql, LRSQueueMessage.class)
				.setParameter("pStatus", new LRSQueueMessage().UPLOADED)
				.setParameter("pStatus", pCloudType)
				.getResultList();
	}
	
	
	
}
