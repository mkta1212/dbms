// package backend.dbms.models;

// import java.io.Serializable;

// import jakarta.persistence.Column;
// import jakarta.persistence.Embeddable;
// import jakarta.persistence.ManyToOne;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Embeddable
// public class ParticipantId implements Serializable {
//     // @Column(name = "user_id")
//     // private Long userId;
 
//     // @Column(name = "event_id")
//     // private Long eventId;
//     private User user;
//     private Event event;
//     @ManyToOne
//     public User getUser() {
//         return user;
//     }
//     public void setser(User user) {
//         this.user = user;
//     }

//     @ManyToOne
//     public Event getEvent() {
//         return event;
//     }
//     public void setEvent(Event event) {
//         this.event = event;
//     }
// }
