package backend.dbms;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import backend.dbms.Service.Impl.ParticipationImpl;
import backend.dbms.Service.Impl.RoleImpl;
import backend.dbms.Service.Impl.StudyEventImpl;
import backend.dbms.Service.Impl.UserImpl;
import backend.dbms.models.ERole;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.Participantion;
// import backend.dbms.models.ParticipantId;
import backend.dbms.models.Role;
import backend.dbms.models.Status;
import backend.dbms.models.User;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudyEventImpl eventImpl;

    @Autowired
    private CourseImpl courseImpl;

    @Autowired
    private UserImpl userImpl;

    @Autowired
    private RoleImpl roleImpl;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ParticipationImpl ParticipantionImpl;

    @Override
    public void run(String... args) throws Exception {
        if (roleImpl.count() == 0) {
            roleImpl.createRole(new Role(ERole.ROLE_USER));
            roleImpl.createRole(new Role(ERole.ROLE_MODERATOR));
            roleImpl.createRole(new Role(ERole.ROLE_ADMIN));
    }
        long eventCount = eventImpl.count();
        Random r = new Random();
        if (userImpl.count() == 0) {
            for(int i=1; i<=10; i++){
                User user1 = new User(Integer.toString(i*111111), Integer.toString(i*111111)+"gmail.com", encoder.encode(Integer.toString(i*111111)));
                Role modRole = roleImpl.getByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                Set<Role> role1 = new HashSet<>();
                role1.add(modRole);
                user1.setRoles(role1);
                userImpl.createUser(user1);

                for(int j=0; j<10; j++){
                    long id = r.nextLong(eventCount);
                    StudyEvent event = eventImpl.getByGroupId(id)
                            .orElseThrow(() -> new RuntimeException("Error: Event is not found."));
                    
                }
            }           

        }
    }
    
}
