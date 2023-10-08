package backend.dbms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyGroup;
import backend.dbms.models.Status;
import backend.dbms.models.User;

import java.util.List;


@Repository
public interface StudyGroupDao extends JpaRepository<StudyGroup, Long> {
  List<StudyGroup> findByStatus(Status Status);
  List<StudyGroup> findByHolder(User holder);
}
