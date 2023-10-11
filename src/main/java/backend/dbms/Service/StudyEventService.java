package backend.dbms.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import backend.dbms.models.Classroom;
import backend.dbms.models.Status;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.User;
import backend.dbms.repository.EventId;

public interface StudyEventService {
    List<StudyEvent> getByStatus(Status status);
    List<StudyEvent> getAllGroups();
    List<StudyEvent> getByHolder(User user);
    void createGroup(StudyEvent event);
    Optional<StudyEvent> getByGroupId(Long id);
    long count();
    List<StudyEvent> getByClassroomAndDate(Classroom classroom, Date date);
    List<EventId> getBookedPeriod(Classroom classroom, Date date);
}
