package br.com.lrsbackup.LRSPersistence.controller;
import java.util.List;
import br.com.lrsbackup.LRSPersistence.model.LRSParameter;
import br.com.lrsbackup.LRSPersistence.model.dao.LRSParameterDAO;

public class LRSParameterManager {
	
	private LRSParameterDAO pDAO = new LRSParameterDAO();
	
	
	public void newParameter(LRSParameter pParam){
		pDAO.newParameter(pParam);
	}

	
	public void updateParameter(LRSParameter pParam){
		pDAO.updateParameter(pParam);
	}

	
	public void removeParameter(LRSParameter pParam){
		pDAO.removeParameter(pParam);
	}
	
	public LRSParameter searchById(Long id) {
		return pDAO.searchById(id);
	}
	
	public List<LRSParameter> searchByName(String pName){
		return pDAO.searchByName(pName);
	}
	
}
