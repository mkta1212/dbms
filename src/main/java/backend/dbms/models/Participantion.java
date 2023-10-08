package backend.dbms.models;

import java.util.Date;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "participantion")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Participantion {
     
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    

    // @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;
    
    // @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @ManyToOne
    @JoinColumn(name = "group_id")
    @NonNull
    private StudyGroup group;
    
    @NonNull
    private Date date;

    // private String feedback;

    public Participantion(User user, StudyGroup group, Date date) {
        this.user = user;
        this.group  = group;
        this.date = date;
    }
    public StudyGroup getGroup(){
        return group;
    }
}
