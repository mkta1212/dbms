package backend.dbms.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;

import backend.dbms.Service.Pair;
import backend.dbms.Service.Impl.CourseImpl;
import backend.dbms.controllers.Response.ResDate;
import backend.dbms.models.Course;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseImpl courseImpl;

    @GetMapping("/classroom/search")
    public List<Course> searchByCourseOrInstructor(@RequestParam String keyword) throws UnsupportedEncodingException{
        keyword = URLDecoder.decode(keyword, "UTF-8");
        System.err.println(keyword);
        return courseImpl.searchByCourseOrInstructor("%"+keyword+"%", "%"+keyword+"%");
    }

    
}
