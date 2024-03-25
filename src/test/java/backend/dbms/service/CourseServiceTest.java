package backend.dbms.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import backend.dbms.Service.Impl.CourseImpl;
import backend.dbms.models.Course;
import backend.dbms.repository.CourseDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {
        @Autowired
        private CourseImpl courseImpl;

        @MockBean
        private CourseDao courseDao;

        @Test
        public void getByCourseId() {
            Course course = new Course(1L, "123", "123", "233", "123");
            Mockito.when(courseDao.findById(1L)).thenReturn(Optional.of(course));
            Course newCourse = courseImpl.getByCourseId(1L).get();
            Assertions.assertNotNull(newCourse);
            Assertions.assertEquals(course, newCourse);
    }
}
