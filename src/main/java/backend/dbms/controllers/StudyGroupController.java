package backend.dbms.controllers;

import java.util.Date;
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

import backend.dbms.models.StudyEvent;
import backend.dbms.Service.Impl.StudyEventImpl;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.UserDao;
import backend.dbms.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyGroupController {

    @Autowired
    private StudyEventImpl eventImpl;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/studyGroups")
    public List<StudyEvent> studyGroups() {
        return eventImpl.getAllGroups();
    }
    @GetMapping("/studyGroups?status={status}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<StudyEvent> inProgressStudyGroups(@PathVariable String status) {
        if (status == "in_progress"){
            return eventImpl.getByStatus(Status.Ongoing);
        }
        else{
            return eventImpl.getByStatus(Status.Finished);
        }
       
    }
    @PostMapping("/studyGroups")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createStudyGroup(@RequestHeader("Authorization") String token,@RequestBody StudyEvent event){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        event.setHolder(user);
        event.setStatus(Status.Ongoing);;
        eventImpl.createGroup(event);
    }
    @GetMapping("/mystudyGroups")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<StudyEvent> mystudyGroups(@RequestHeader("Authorization") String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        return eventImpl.getByHolder(user);
    }

    
}
