package br.com.lrsbackup.LRSManager.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lrsbackup.LRSManager.persistence.model.LRSCloudInvetoryRequest;

public interface LRSCloudInventoryRequestRepository extends JpaRepository<LRSCloudInvetoryRequest, Long> {

	@Query("SELECT t FROM LRSCloudInvetoryRequest t WHERE t.cloudProvider = :cloudProvider AND t.status = :pStatus ORDER BY t.requestedDate DESC ")
	List<LRSCloudInvetoryRequest>  findByLastRequest(String cloudProvider, String pStatus);

	LRSCloudInvetoryRequest findByprotocolID(String pProtocol);

}