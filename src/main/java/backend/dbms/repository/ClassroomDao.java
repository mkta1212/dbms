package backend.dbms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.dbms.models.Classroom;

public interface ClassroomDao extends JpaRepository<Classroom,Long> {
    
}
