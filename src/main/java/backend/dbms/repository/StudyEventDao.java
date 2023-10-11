package backend.dbms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEvent;
import backend.dbms.models.Status;
import backend.dbms.models.User;

import java.util.List;


@Repository
public interface StudyEventDao extends JpaRepository<StudyEvent, Long> {
  List<StudyEvent> findByStatus(Status Status);
  List<StudyEvent> findByHolder(User holder);
}
