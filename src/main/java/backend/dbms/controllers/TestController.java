package backend.dbms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
  // 測試併行控制是否有成功上鎖
  @PreAuthorize("hasRole('PROVIDER')")
  @PostMapping("/concurrency")
  public String testConcurrency(@RequestHeader("Authorization") String token,@RequestBody List<StudyEventReq> event) throws InterruptedException, ExecutionException {
      String userName = jwtUtils.getUserNameFromJwtToken(token.substring(7, token.length()));
      User user = userRepository.findByUsername(userName).get();
      // 開啟兩個執行緒同步新增讀書活動
      ExecutorService executor = Executors.newFixedThreadPool(2);
      // executor.execute(()->eventImpl.createEvent(event.get(0),user));
      // executor.execute(()->eventImpl.createEvent(event.get(1),user));
      String result1 = executor.submit(()->eventImpl.createEvent(event.get(0),user)).get();
      String result2 = executor.submit(()->eventImpl.createEvent(event.get(1),user)).get();
      executor.shutdown();
      System.err.println(result2);
      // 等待三秒後再新增一筆讀書活動，確認 study_event_period 是否有解鎖
      String result3 = eventImpl.createEvent(event.get(2),user);
      return result1+"\n"+result2+"\n"+result3;
  }
}
