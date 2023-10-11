package backend.dbms.models;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @NonNull
    @Column(name = "course_name", length = 20)
    private String courseName;

    @NonNull
    @Column(name = "instructor_name", length = 15)
    private String instructorName;


    @Column(name = "department_name", length = 20)
    private String departmentName;
    
    @Column(name = "lecture_time", length = 20)
    private String lectureTime;
}
