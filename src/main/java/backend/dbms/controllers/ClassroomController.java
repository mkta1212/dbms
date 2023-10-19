package backend.dbms.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RestController;

import backend.dbms.Service.Pair;
import backend.dbms.Service.Impl.ClassroomImpl;
import backend.dbms.Service.Impl.StudyEventPeriodImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ClassroomController {

    @Autowired
    private ClassroomImpl classroomImpl;

    @GetMapping("/classroom/booked")
    public List<Pair> bookedClassroom() throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date(df.parse("2023/10/17").getTime());
        List<Pair> classroomList  = classroomImpl.findBookedClassroom(today);
        return classroomList;
    }

    
}
