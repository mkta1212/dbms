package backend.dbms.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;
import backend.dbms.Service.Impl.StudyEventImpl;
import backend.dbms.controllers.DTO.StudyEventDTO;
import backend.dbms.controllers.Request.StudyEventReq;
import backend.dbms.controllers.Response.EventResponse;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.UserDao;
import backend.dbms.security.jwt.JwtUtils;
import jakarta.persistence.Tuple;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyEventController {

    @Autowired
    private StudyEventImpl eventImpl;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/allstudyEvents")
    public List<StudyEvent> studyEvents() {
        return eventImpl.getAllGroups();
    }
    @GetMapping("/studyEvents")
    // @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public EventResponse onGoingStudyEvents(@RequestParam int page, @RequestParam int row) {
        // return eventImpl.getAvailableEvent(0,10).getContent();
        Page<StudyEventDTO> eventDTO = eventImpl.getAvailableEvent(page,row);
        // Map <Integer,List<StudyEventDTO>> result = new HashMap<>();
        // result.put(eventDTO.getTotalPages(), eventDTO.getContent());
        
        return new EventResponse(eventDTO.getContent(),eventDTO.getTotalPages());
  
       
    }
    @PostMapping("/studyEvents")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void createStudyEvent(@RequestHeader("Authorization") String token,@RequestBody StudyEventReq event){
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        eventImpl.createEvent(event,user);
    }
    @GetMapping("/mystudyEvents")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<StudyEvent> mystudyEvents(@RequestHeader("Authorization") String token) {
        String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
        User user = userRepository.findByUsername(userName).get();
        return eventImpl.getByHolder(user);
    }

    
}
