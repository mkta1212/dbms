package backend.dbms;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import backend.dbms.models.ERole;
import backend.dbms.models.Event;
import backend.dbms.models.Participantion;
// import backend.dbms.models.ParticipantId;
import backend.dbms.models.Role;
import backend.dbms.models.Status;
import backend.dbms.models.User;
import backend.dbms.repository.StudyGroupDao;
import backend.dbms.repository.RoleDao;
import backend.dbms.repository.UserDao;
import backend.dbms.repository.ParticipantionDao;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudyGroupDao eventRepository;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ParticipantionDao ParticipantionRepository;

    @Override
    public void run(String... args) throws Exception {
    //     if (roleRepository.count() == 0) {
    //         roleRepository.save(new Role(ERole.ROLE_USER));
    //         roleRepository.save(new Role(ERole.ROLE_MODERATOR));
    //         roleRepository.save(new Role(ERole.ROLE_ADMIN));
    // }
        Set<Role> role1 = new HashSet<>();
        if (!userRepository.existsByUsername("123456")) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            User user1 = new User("123456", "123456@gmail.com", encoder.encode("123456"));
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role1.add(modRole);
            user1.setRoles(role1);
            userRepository.save(user1);
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2023");  
            Event event1 = new Event("打麻將", Status.In_Progress, "館二", "30/10",date1,100,user1);
            eventRepository.save(event1);
            Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("31/10/2023");  
            Event event2 = new Event("打桌遊", Status.In_Progress, "教研320", "農家樂",date2,100,user1);
            eventRepository.save(event2);


            User user2 = new User("123456789", "123456789@gmail.com", encoder.encode("123456789"));
            user2.setRoles(role1);
            userRepository.save(user2);
            Date date3 = new  SimpleDateFormat("dd/MM/yyyy").parse("31/8/2023");  
            Event event3 = new Event("打桌遊", Status.Finished, "教研320", "農家樂",date3,100,user2);
            eventRepository.save(event3);
            
            Date date = new Date();
            Participantion participant11 = new Participantion(user2, event1, date);
            ParticipantionRepository.save(participant11);
            Date date4 = new  SimpleDateFormat("dd/MM/yyyy").parse("31/7/2023");
            Participantion participant13 = new Participantion(user1, event3, date4);
            ParticipantionRepository.save(participant13);
            

        }
        
    }
    
}
