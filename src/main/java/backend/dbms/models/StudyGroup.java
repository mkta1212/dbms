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
@Table(name = "study_group")
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;


    @NotBlank
    @Size(max = 100)
    @NonNull
    private String content;

    @NonNull
    private Date date;

    
    @NonNull
    @Column(name = "user_max")
    private int userMax;

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
    public StudyGroup(Date date,  int userMax, String content){
        this.date = date;
        this.userMax = userMax;
        this.content = content;
        
    }
}
