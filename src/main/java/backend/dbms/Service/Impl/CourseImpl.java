package backend.dbms.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Long createCourse(Course course) {
        return courseDao.save(course).getCourseId();
    }

    @Override
    public Optional<Course> getByCourseId(Long id) {
        return courseDao.findById(id);
    }

    @Override
    public Long count() {
        return courseDao.count();
    }

    @Override
    public List<Course> searchByCourseOrInstructor(String courseName, String instructorName) {
        return courseDao.findByCourseNameLikeOrInstructorNameLike(courseName, instructorName);
    }

    @Override
    public List<String> getAllCoursesName(){
        return courseDao.findAllCourseName();
    }

    @Override
    public Long updateCourse(Course course) {
        return courseDao.save(course).getCourseId();
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseDao.deleteById(courseId);
    }

    @Override
    public Page<Course> searchCourse(Long courseId, String courseName, String instuctorName, String departmentName, int page, int row){
        Pageable pageable = PageRequest.of(page,row, Sort.by("departmentName"));
        return courseDao.findByMultiCon(courseId, courseName, instuctorName, departmentName, pageable);
    }
    
    
}
