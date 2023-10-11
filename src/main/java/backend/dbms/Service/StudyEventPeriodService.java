package backend.dbms.Service;

import java.sql.Date;

import backend.dbms.models.Classroom;

public interface StudyEventPeriodService {
    boolean checkTimeAvailable(Classroom classroom, Date date, int period);
}
