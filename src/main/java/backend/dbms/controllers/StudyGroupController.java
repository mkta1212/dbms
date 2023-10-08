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

import backend.dbms.models.StudyGroup;
import backend.Service.Impl.StudyGroupImpl;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.StudyGroupDao;
import backend.dbms.repository.UserDao;
import backend.dbms.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyGroupController {

    @Autowired
    private StudyGroupImpl groupImpl;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/studyGroups")
    public List<StudyGroup> studyGroups() {
        return groupImpl.getAllGroups();
    }
    @GetMapping("/studyGroups?status={status}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<StudyGroup> inProgressStudyGroups(@PathVariable String status) {
        if (status == "in_progress"){
            return groupImpl.getByStatus(Status.In_Progress);
        }
        else{
            return groupImpl.getByStatus(Status.Finished);
        }
       
    }
    @PostMapping("/studyGroups")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createStudyGroup(@RequestHeader("Authorization") String token,@RequestBody StudyGroup group){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        group.setHolder(user);
        group.setStatus(Status.In_Progress);;
        groupImpl.createGroup(group);
    }
    @GetMapping("/mystudyGroups")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<StudyGroup> mystudyGroups(@RequestHeader("Authorization") String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        return groupImpl.getByHolder(user);
    }

    
}
