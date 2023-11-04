package backend.dbms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.Classroom;

@Repository
public interface ClassroomDao extends JpaRepository<Classroom,Long> {
    
}
