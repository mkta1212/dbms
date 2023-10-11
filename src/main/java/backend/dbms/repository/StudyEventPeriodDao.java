package backend.dbms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEventPeriod;

@Repository
public interface StudyEventPeriodDao extends JpaRepository<StudyEventPeriod,Long> {
    
}
