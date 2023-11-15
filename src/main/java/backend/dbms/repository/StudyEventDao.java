package backend.dbms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;
import backend.dbms.controllers.DTO.StudyEventDTO;
import backend.dbms.models.Classroom;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import jakarta.persistence.Tuple;

import java.sql.Date;
import java.util.List;


@Repository
public interface StudyEventDao extends JpaRepository<StudyEvent, Long> {
  List<StudyEvent> findByStatus(Status Status);
  List<StudyEvent> findByHolder(User holder);

  @Query(value = """
    Select event.eventId as eventId, co.courseName as courseName, co.instructorName as instructorName, cl.roomName as roomName, group_concat(sep.eventPeriod) as periodList, sep.eventDate as eventDate 
    From StudyEventPeriod as sep join sep.event as event join sep.classroom as cl join sep.event.course as co 
    where sep.eventDate between :startDate and :endDate and sep.event.status=:status group by sep.event""")
 List<StudyEventDTO> findByEventIdJoinStudyEventPeriod (@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("status") Status status);

  // List<StudyEvent> findByClassroomAndEventDate(Classroom classroom, Date date);
  // List<EventId> findAllByClassroomAndEventDate(Classroom classroom, Date date);
  // List<StudyEvent> findByEventDateBetween(Date startDate, Date endDate);
  // List<StudyEvent> findByEventDate(Date date);
}
