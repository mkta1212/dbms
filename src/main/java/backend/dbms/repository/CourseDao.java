package backend.dbms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import backend.dbms.models.Course;

@Repository
public interface CourseDao extends JpaRepository<Course,Long> {
    List<Course> findByCourseNameLikeOrInstructorNameLike(String courseName,String instructorName);
}       
