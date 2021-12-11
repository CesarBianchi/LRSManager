package br.com.lrsbackup.LRSManager.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.lrsbackup.LRSManager.persistence.model.LRSQueueFile;


public interface LRSQueueFileRepository extends JpaRepository<LRSQueueFile, Long> {

	@Query("SELECT t FROM LRSQueueFile t WHERE t.status = :pStatus")
	List<LRSQueueFile> findByStatus(@Param("pStatus") String pStatus);

	@Query("SELECT t FROM LRSQueueFile t WHERE t.status = :pStatus")
	List<LRSQueueFile> queryFirst100findByStatus(@Param("pStatus") String pStatus);
	
	@Query("SELECT t FROM LRSQueueFile t WHERE t.cloudProvider = :pcloudProvider AND t.status = :pstatus AND insertedDate between :pinsStartDate AND :pinsEndDate ")
	List<LRSQueueFile> findByFilter(@Param("pcloudProvider") String pcloudProvider, @Param("pstatus") String pstatus, @Param("pinsStartDate") LocalDateTime pinsStartDate, @Param("pinsEndDate") LocalDateTime pinsEndDate);

	@Query("SELECT t FROM LRSQueueFile t WHERE t.cloudProvider = :pcloudProvider AND t.originalfullname = :originalfullname")
	LRSQueueFile findExists(@Param("pcloudProvider") String pcloudProvider, @Param("originalfullname") String originalfullname);
		
	List<LRSQueueFile> findByoriginalfullname(String fullfilename);

	List<LRSQueueFile> findByid(Long id);



}