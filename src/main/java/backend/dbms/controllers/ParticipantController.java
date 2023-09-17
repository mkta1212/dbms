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

import backend.dbms.models.Event;
import backend.dbms.models.Participant;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.EventRepository;
import backend.dbms.repository.ParticipantRepository;
import backend.dbms.repository.UserRepository;
import backend.dbms.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ParticipantController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/joins")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Participant createEvent(@RequestHeader("Authorization") String token,@RequestBody Event event){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        // Event event = eventRepository.findById(id).get();
        Date date = new Date();
        Participant participant = new Participant(user,event,date);
        return participantRepository.save(participant);
    }

    @GetMapping("/joins")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Participant> findMyParticipation(@RequestHeader("Authorization") String token){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        return participantRepository.findByUser(user);
    }


}
