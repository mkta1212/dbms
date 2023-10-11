package backend.dbms.Service.Impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.StudyEventPeriodService;
import backend.dbms.models.Classroom;
import backend.dbms.models.StudyEvent;
import backend.dbms.repository.EventId;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class StudyEventPeriodImpl implements StudyEventPeriodService {

    @Autowired
    private StudyEventImpl studyEventImpl;
    @Override
    public boolean checkTimeAvailable(Classroom classroom, Date date, int period) {
        List<EventId> eventList = studyEventImpl.getBookedPeriod(classroom, date);
        return true;
        
    }
    
}
