package backend.dbms.controllers;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dbms.models.StudyGroup;
import backend.Service.Impl.ParticipationImpl;
import backend.Service.Impl.StudyGroupImpl;
import backend.Service.Impl.UserImpl;
import backend.dbms.models.Participantion;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.StudyGroupDao;
import backend.dbms.repository.ParticipantionDao;
import backend.dbms.repository.UserDao;
import backend.dbms.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParticipantController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserImpl userImpl;

    @Autowired
    private StudyGroupImpl groupImpl;

    @Autowired
    private ParticipationImpl participationImpl;

    @PostMapping("/joins")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createParticipant(@RequestHeader("Authorization") String token,@RequestBody StudyGroup group){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userImpl.getByUsername(userName).get();
        group = groupImpl.getByGroupId(group.getGroupId()).get();
        Date date = new Date();
        Participantion participantion = new Participantion(user,group,date);
        participationImpl.createParticipation(participantion);
    }

    @GetMapping("/joins")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Participantion> findMyParticipation(@RequestHeader("Authorization") String token){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userImpl.getByUsername(userName).get();
        return participationImpl.getByUser(user);
    }

    @DeleteMapping("/joins")
    @Transactional
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleParticipant(@RequestHeader("Authorization") String token,@RequestBody StudyGroup group){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userImpl.getByUsername(userName).get();
        group = groupImpl.getByGroupId(group.getGroupId()).get();
        System.err.println(group.toString());
        participationImpl.deleteByUserAndGroup(user, group);
    }

    @PostMapping("/participantAmounts")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Long getParticipantAmount(@RequestBody StudyGroup group){
        group = groupImpl.getByGroupId(group.getGroupId()).get();
        System.err.println(participationImpl.countByGroup(group));
        return participationImpl.countByGroup(group);
    }


}
