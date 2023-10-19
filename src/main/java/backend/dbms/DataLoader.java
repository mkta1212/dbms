package backend.dbms;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import backend.dbms.Service.Pair;
import backend.dbms.Service.Impl.ClassroomImpl;
import backend.dbms.Service.Impl.CourseImpl;
import backend.dbms.Service.Impl.ParticipationImpl;
import backend.dbms.Service.Impl.RoleImpl;
import backend.dbms.Service.Impl.StudyEventImpl;
import backend.dbms.Service.Impl.StudyEventPeriodImpl;
import backend.dbms.Service.Impl.UserImpl;
import backend.dbms.models.Classroom;
import backend.dbms.models.Course;
import backend.dbms.models.ERole;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;
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
    private StudyEventPeriodImpl eventPeriodImpl;

    @Autowired
    private UserImpl userImpl;

    @Autowired
    private RoleImpl roleImpl;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ParticipationImpl ParticipantionImpl;

    @Autowired
    private ClassroomImpl classroomImpl;

    @Override
    public void run(String... args) throws Exception {
        if (roleImpl.count() == 0) {
            roleImpl.createRole(new Role(ERole.ROLE_USER));
            roleImpl.createRole(new Role(ERole.ROLE_MODERATOR));
            roleImpl.createRole(new Role(ERole.ROLE_ADMIN));
    }
        

        if (userImpl.count() == 0) {
            for(int i=1; i<=2; i++){
                User user1 = new User(Integer.toString(i*111111), Integer.toString(i*111111)+"gmail.com", encoder.encode(Integer.toString(i*111111)));
                Role modRole = roleImpl.getByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                Set<Role> role1 = new HashSet<>();
                role1.add(modRole);
                user1.setRoles(role1);
                userImpl.createUser(user1);

                
            }           

        }
        User user1 = userImpl.getByUsername("111111").get();
        // autoGenerate(user1);
        // findBookedTime();
        findBookedClassroom();
    }

    public void autoGenerate (User user) throws ParseException{
        long courseCount = courseImpl.count();
        long classroomCount = classroomImpl.count();
        Random r = new Random();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date(df.parse("2023/10/15").getTime());
        for(int j=0; j<10; j++){
                    long courseId = r.nextLong(courseCount);
                    System.err.println();
                    Course course = courseImpl.getByCourseId(courseId)
                            .orElseThrow(() -> new RuntimeException("Error: Course"+courseId+" is not found."));
                    // int day = r.nextInt(7)+1;
                    int day = r.nextInt(2)+1;
                    Date eventDate = new Date(today.getTime()+ 1000 * 60 * 60 * 24 *day);
                    List<Integer> periodList = new ArrayList<Integer>();
                    int periodLen = r.nextInt(3);
                    int period = r.nextInt(13)+8;
                    for(int p=0; p<=periodLen; p++){
                        periodList.add(period+p);
                    }
                    // long classroomId = r.nextLong(classroomCount)+1;
                    long classroomId = r.nextLong(1)+1;
                    Classroom classroom = classroomImpl.getByClassroomId(classroomId)
                            .orElseThrow(() -> new RuntimeException("Error: Classroom is not found."));
                    if(eventPeriodImpl.checkTimeAvailable(classroom,eventDate,periodList)){
                        StudyEvent studyEvent = new StudyEvent(user, course, classroom,eventDate, "", Status.Ongoing);
                        eventImpl.createEvent(studyEvent);
                        for(int p=0; p<=periodLen; p++){
                            StudyEventPeriod eventPeriod = new StudyEventPeriod(studyEvent, period+p);
                            eventPeriodImpl.createEventPeriod(eventPeriod);
                        }
                    }
                    
                }
    }
    public void findBookedTime() throws ParseException{
        Classroom classroom = classroomImpl.getByClassroomId(Long.valueOf(1))
                            .orElseThrow(() -> new RuntimeException("Error: Classroom is not found."));
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date(df.parse("2023/10/15").getTime());
        
        int[][] periodList = eventPeriodImpl.findBookedTime(classroom,today);
        for (int i =0;i<periodList.length;i++) {
            System.err.println("10/"+(15+i));
            for (int j = 0; j<periodList[i].length; j++){
                System.err.println(j+":"+ periodList[i][j]);
            }
        }
    }
    public void findBookedClassroom() throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date(df.parse("2023/10/17").getTime());
        List<Pair> pairs =  classroomImpl.findBookedClassroom(today);
        for(int i = 0; i<5;i++){
            System.out.println(pairs.get(i));
        }
    }
    

}
