package backend.dbms.controllers;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import backend.dbms.models.Course;
import backend.dbms.repository.CourseDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseDao courseDao;


    @Test
    @WithMockUser(roles = "USER")
    public void getCourseById() throws Exception{
        Course course = new Course(1L, "123", "123", "233", "123");
            Mockito.when(courseDao.findById(1L)).thenReturn(Optional.of(course));
        MockHttpServletRequestBuilder requestbBuilder = MockMvcRequestBuilders.get("/api/course").param("courseId","1");
        mockMvc.perform(requestbBuilder)
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(MockMvcResultMatchers.status().isOk())
                            .andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(course.getCourseId()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value(course.getCourseName()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.instructorName").value(course.getInstructorName()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.departmentName").value(course.getDepartmentName()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.lectureTime").value(course.getLectureTime()));
                            
        
        
    }
}
