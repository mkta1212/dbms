package backend.dbms.Service;

import java.util.List;
import java.util.Optional;

import backend.dbms.models.Course;

public interface CourseService {
    void createCourse(Course course);
    Optional<Course> getByCourseId(Long id);
    Long count();
    List<Course> searchByCourseOrInstructor(String courseName, String instructorName);
}
