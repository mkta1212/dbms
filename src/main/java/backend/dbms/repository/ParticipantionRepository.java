package backend.dbms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyGroup;
import backend.dbms.models.Participantion;
import backend.dbms.models.User;

@Repository
public interface ParticipantionRepository extends JpaRepository<Participantion, Long> {
  List<Participantion> findByUser(User user);
  void deleteByUserAndGroup(User user, StudyGroup group);
  long countByGroup(StudyGroup group);
}
