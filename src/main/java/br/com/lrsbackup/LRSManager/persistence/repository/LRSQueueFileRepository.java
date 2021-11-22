package br.com.lrsbackup.LRSManager.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;


public interface LRSQueueFileRepository extends JpaRepository<LRSQueueFile, Long> {

	@Query("SELECT t FROM LRSQueueFile t WHERE t.status = :pStatus")
	List<LRSQueueFile> findByStatus(String pStatus);

	@Query("SELECT t FROM LRSQueueFile t WHERE t.cloudProvider = :pcloudProvider AND t.status = :pstatus AND insertedDate between :pinsStartDate AND :pinsEndDate ")
	List<LRSQueueFile> findByFilter(String pcloudProvider, String pstatus, LocalDateTime pinsStartDate, LocalDateTime pinsEndDate);

	@Query("SELECT t FROM LRSQueueFile t WHERE t.cloudProvider = :pcloudProvider AND t.originalfullname = :originalfullname")
	LRSQueueFile findExists(String pcloudProvider, String originalfullname);

		
	List<LRSQueueFile> findByoriginalfullname(String fullfilename);

	List<LRSQueueFile> findByid(Long id);

	

}