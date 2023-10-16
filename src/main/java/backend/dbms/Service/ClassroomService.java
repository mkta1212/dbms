package backend.dbms.Service;

import java.util.List;
import java.util.Optional;

import backend.dbms.models.Classroom;

public interface ClassroomService {
    long count();
    Optional<Classroom> getByClassroomId(Long id);
    List<Classroom> getAllClassroom();
}
