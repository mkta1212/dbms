package backend.dbms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.Participant;
import backend.dbms.models.User;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
  List<Participant> findByUser(User user);
}
