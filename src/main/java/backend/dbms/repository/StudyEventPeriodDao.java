package backend.dbms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.dbms.models.StudyEventPeriod;

public interface StudyEventPeriodDao extends JpaRepository<StudyEventPeriod,Long> {
    
}
