package br.com.lrsbackup.LRSPersistence.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static final EntityManagerFactory FACTORY = Persistence
			.createEntityManagerFactory("LRSBackup");

	public static EntityManager getEntityManager() { 
		return FACTORY.createEntityManager();
	}
	
}
