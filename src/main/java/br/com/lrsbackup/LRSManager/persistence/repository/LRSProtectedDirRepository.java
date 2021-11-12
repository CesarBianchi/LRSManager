package br.com.lrsbackup.LRSManager.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.lrsbackup.LRSManager.persistence.model.LRSProtectedDir;

public interface LRSProtectedDirRepository extends JpaRepository<LRSProtectedDir, Long> {

}
