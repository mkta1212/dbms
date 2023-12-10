package backend.dbms.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dbms.Service.Impl.StudyEventImpl;
import backend.dbms.Service.Impl.UserImpl;
import backend.dbms.controllers.Request.StudyEventReq;
import backend.dbms.models.User;
import backend.dbms.repository.UserDao;
import backend.dbms.security.jwt.JwtUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @Autowired
  private UserDao userRepository;
  @Autowired
  private JwtUtils jwtUtils;
  @Autowired
  private StudyEventImpl eventImpl;
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
  @PostMapping("/concurrency")
  public void testConcurrency(@RequestHeader("Authorization") String token,@RequestBody StudyEventReq event) {
      String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
      User user = userRepository.findByUsername(userName).get();
      ExecutorService executor = Executors.newFixedThreadPool(2);
      executor.execute(()->eventImpl.createEvent(event,user));
      event.setCourseId(event.getCourseId()+1);
      executor.execute(()->eventImpl.createEvent(event,user));
      executor.shutdown();
  }
  
}
