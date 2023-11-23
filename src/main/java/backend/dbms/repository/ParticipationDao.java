package backend.dbms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEvent;
import backend.dbms.models.Participation;
import backend.dbms.models.ParticipationId;
import backend.dbms.models.User;

@Repository
public interface ParticipationDao extends JpaRepository<Participation, StudyEvent> {
  List<Participation> findByUser(User user);
  void deleteByUserAndEvent(User user, StudyEvent event);
  long countByEvent(StudyEvent event);
  @Query(value="Select p.event.eventId From Participation as p where p.user = :user")
  List<Long> findEventIdByUser(User user);
  
}
