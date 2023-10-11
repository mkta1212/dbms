package backend.dbms.Service;

import java.util.Optional;

import backend.dbms.models.Classroom;

public interface ClassroomService {
    long count();
    Optional<Classroom> getByClassroomId(Long id);
    
}
