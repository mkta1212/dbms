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

import backend.dbms.models.StudyEvent;
import backend.dbms.Service.Impl.ParticipationImpl;
import backend.dbms.Service.Impl.StudyEventImpl;
import backend.dbms.Service.Impl.UserImpl;
import backend.dbms.models.Participantion;
import backend.dbms.models.Status;
import backend.dbms.models.User;
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
    private StudyEventImpl eventImpl;

    @Autowired
    private ParticipationImpl participationImpl;

    @PostMapping("/joins")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createParticipant(@RequestHeader("Authorization") String token,@RequestBody StudyEvent event){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userImpl.getByUsername(userName).get();
        event = eventImpl.getByEventId(event.getEventId()).get();
        Date date = new Date();
        Participantion participantion = new Participantion(user,event,date);
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
    public void deleParticipant(@RequestHeader("Authorization") String token,@RequestBody StudyEvent event){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userImpl.getByUsername(userName).get();
        event = eventImpl.getByEventId(event.getEventId()).get();
        System.err.println(event.toString());
        participationImpl.delete(user, event);
    }

    @PostMapping("/participantAmounts")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Long getParticipantAmount(@RequestBody StudyEvent event){
        event = eventImpl.getByEventId(event.getEventId()).get();
        System.err.println(participationImpl.count(event));
        return participationImpl.count(event);
    }


}
