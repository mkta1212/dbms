package backend.dbms.controllers.DTO;

import java.sql.Date;

public interface MyEventDTO {

    String getEventId();
    Date getEventDate();
    String getPeriodList();
    String getRoomName();
    String getCourseName();
    String getInstructorName();
    String getContent();
    int getTotalParticipation();
}
