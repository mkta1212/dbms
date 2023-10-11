package backend.dbms.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.CourseService;
import backend.dbms.models.Course;
import backend.dbms.repository.CourseDao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class CourseImpl implements CourseService {
    
    @Autowired
    private CourseDao courseDao;

    @Override
    public void createCourse(Course course) {
        courseDao.save(course);
    }

    @Override
    public Optional<Course> getByCourseId(Long id) {
        return courseDao.findById(id);
    }

    @Override
    public Long count() {
        return courseDao.count();
    }
    
}
