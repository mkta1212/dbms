package backend.dbms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEvent;
import backend.dbms.models.Participantion;
import backend.dbms.models.User;

@Repository
public interface ParticipantionDao extends JpaRepository<Participantion, Long> {
  List<Participantion> findByUser(User user);
  void deleteByUserAndEvent(User user, StudyEvent event);
  long countByEvent(StudyEvent event);
  
}
