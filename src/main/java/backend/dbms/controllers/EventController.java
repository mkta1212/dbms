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
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.EventRepository;
import backend.dbms.repository.UserRepository;
import backend.dbms.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/events")
    public List<Event> events() {
        return eventRepository.findAll();
    }
    @GetMapping("/events?status={status}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Event> inProgressEvents(@PathVariable String status) {
        if (status == "in_progress"){
            return eventRepository.findByEventStatus(Status.In_Progress);
        }
        else{
            return eventRepository.findByEventStatus(Status.Finished);
        }
       
    }
    @PostMapping("/events")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Event createEvent(@RequestHeader("Authorization") String token,@RequestBody Event event){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        event.setHolder(user);
        event.setEventStatus(Status.In_Progress);;
        return eventRepository.save(event);
    }
    @GetMapping("/myevents")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Event> myEvents(@RequestHeader("Authorization") String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        return eventRepository.findByHolder(user);
    }

    
}
