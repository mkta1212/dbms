package backend.dbms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEvent;
import backend.dbms.models.Classroom;
import backend.dbms.models.Status;
import backend.dbms.models.User;

import java.sql.Date;
import java.util.List;


@Repository
public interface StudyEventDao extends JpaRepository<StudyEvent, Long> {
  List<StudyEvent> findByStatus(Status Status);
  List<StudyEvent> findByHolder(User holder);

  @Query("Select se from StudyEvent  se Join StudyEventPeriod  sep  where eventDate >= :date and se.status=:status ")
  List<StudyEvent> findByEventIdJoinStudyEventPeriod(@Param("date") Date date, @Param("status") Status status);

  // List<StudyEvent> findByClassroomAndEventDate(Classroom classroom, Date date);
  // List<EventId> findAllByClassroomAndEventDate(Classroom classroom, Date date);
  // List<StudyEvent> findByEventDateBetween(Date startDate, Date endDate);
  // List<StudyEvent> findByEventDate(Date date);
}
