package backend.dbms.Service.Impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import backend.dbms.Service.Pair;
import backend.dbms.Service.StudyEventPeriodService;
import backend.dbms.models.Classroom;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;
import backend.dbms.repository.EventId;
import backend.dbms.repository.StudyEventPeriodDao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class StudyEventPeriodImpl implements StudyEventPeriodService {

    @Autowired
    private StudyEventImpl studyEventImpl;

    @Autowired
    private ClassroomImpl classroomImpl;

    @Autowired
    private StudyEventPeriodDao eventPeriodDao;

    public void createEventPeriod(StudyEventPeriod eventPeriod){
        eventPeriodDao.save(eventPeriod);
    }
    @Override
    public boolean checkTimeAvailable(Classroom classroom, Date date, List<Integer> periodList) {
        // List<EventId> eventList = studyEventImpl.getBookedPeriod(classroom, date);
        // List<StudyEvent> eventList = studyEventImpl.getByClassroomAndDate(classroom, date);
        // for (StudyEvent event : eventList) {
        //     System.err.println(event.toString());
        //     for(StudyEventPeriod eventPeriod:eventPeriodDao.findByEvent(event)){
        //         System.err.println(eventPeriod.toString());
        //         if (periodList.contains(eventPeriod.getEventPeriod())){
        //             return false;

        //         }
        //     }   
        // }
        List<StudyEventPeriod> bookedPeriodList = eventPeriodDao.findByClassroomAndEventDate(classroom, date);
        for(StudyEventPeriod bookedPeriod: bookedPeriodList){
            if(periodList.contains(bookedPeriod.getEventPeriod())){
                return false;
            }
        }
        return true;
        
    }

    @Override
    // 七天內指定教室被預約的時間
    public int[][] findBookedTime(Classroom classroom, Date date){
        int[][] periodList = new int[8][24];
        for(int i = 0 ;i<=7;i++){
            for(int j = 0;j<=23;j++){

                periodList[i][j]=0;
            }
        }
        List<StudyEventPeriod> eventPeriodList = eventPeriodDao.findByEventDateBetween(date,new Date(date.getTime()+ 1000 * 60 * 60 * 24 *7));
        for(StudyEventPeriod eventPeriod:eventPeriodList){
            int day = (int)((eventPeriod.getEventDate().getTime()-date.getTime())/86400000);
            periodList[day][eventPeriod.getEventPeriod()] = 1;
        }
        return periodList;
        // List<StudyEvent> eventList = studyEventImpl.getByDateRange(date,new Date(date.getTime()+ 1000 * 60 * 60 * 24 *7));
        // for(StudyEvent event: eventList){
        //     int day = (int)((event.getEventDate().getTime()-date.getTime())/86400000);
        //     for(StudyEventPeriod eventPeriod:eventPeriodDao.findByEvent(event)){
        //         periodList[day][eventPeriod.getEventPeriod()] = 1;
        //     }
        // }
    }

    
}
