package backend.dbms.models;

import java.util.Date;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "participant")
// @IdClass(ParticipantId.class)
// @NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Participant {
     
    

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
    @JoinColumn(name = "event_id")
    @NonNull
    private Event event;
    
    @NonNull
    private Date date;

    public Participant(User user, Event event, Date date) {
        this.user = user;
        this.event  = event;
        this.date = date;
    }
    public Event getEvent(){
        return event;
    }
}
