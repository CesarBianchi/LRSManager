package br.com.lrsbackup.LRSManager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lrsbackup.LRSManager.persistence.model.LRSCloudInvetoryRequest;
import br.com.lrsbackup.LRSManager.persistence.model.LRSParameter;

public interface LRSParameterRepository extends JpaRepository<LRSParameter, Long> {

	LRSParameter findByname(String pName);

	LRSParameter findByid(Long id);
}
 