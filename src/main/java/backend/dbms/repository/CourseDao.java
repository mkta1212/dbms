package backend.dbms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.dbms.models.Course;

@Repository
public interface CourseDao extends JpaRepository<Course,Long> {
    
}
