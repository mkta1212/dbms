package backend.dbms.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;

@Repository
public interface StudyEventPeriodDao extends JpaRepository<StudyEventPeriod,Long> {
    List<StudyEventPeriod> findByEvent(StudyEvent studyEvent);
}
