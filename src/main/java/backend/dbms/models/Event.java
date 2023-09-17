package backend.dbms.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @NonNull
    private String eventName;

    @NotBlank
    @NonNull
    private Status eventStatus;

    @NotBlank
    @Size(max = 50)
    @NonNull
    private String location;

    @NotBlank
    @Size(max = 100)
    @NonNull
    private String content;

    @NonNull
    private Date date;

    
    @NonNull
    private int max;

    // @OneToMany
    // private Set<Participant> participantList = new HashSet<>();

    @NonNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id")
    private User holder;

    public String getUserName(){
        return holder.getUsername();
    }
    // public void addParticipant(Participant participant){
    //     participantList.add(participant);
    // }
    public Event(String eventName,Date date, String location, int max, String content){
        this.eventName = eventName;
        this.date = date;
        this.location = location;
        this.max = max;
        this.content = content;
        
    }
}
