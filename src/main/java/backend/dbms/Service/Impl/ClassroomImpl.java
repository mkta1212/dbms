package backend.dbms.Service.Impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.ClassroomService;
import backend.dbms.Service.Pair;
import backend.dbms.models.Classroom;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;
import backend.dbms.repository.ClassroomDao;
import backend.dbms.repository.StudyEventPeriodDao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class ClassroomImpl implements ClassroomService {

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private StudyEventImpl studyEventImpl;

    @Autowired
    private StudyEventPeriodDao eventPeriodDao;

    @Override
    public long count() {
        return classroomDao.count();
    }
    @Override
    public Optional<Classroom> getByClassroomId(Long id) {
        return classroomDao.findById(id);
    }

    @Override
    public List<Classroom> getAllClassroom() {
        return classroomDao.findAll();
    }
    @Override
    public List<Pair> findBookedClassroom(Date date){
        List<Classroom> classroomList = getAllClassroom();
        HashMap<Long, int[]> classroomMap = new HashMap<Long, int[]>();
        for(Classroom classroom: classroomList){
            int[] periodList = new int[24];
            classroomMap.put(classroom.getClassroomId(),periodList);
        }
        List<StudyEventPeriod> eventPeriodList = eventPeriodDao.findByEventDate(date);
        for(StudyEventPeriod eventPeriod: eventPeriodList){
            int[] periodList = classroomMap.get(eventPeriod.getClassroom().getClassroomId());
            periodList[eventPeriod.getEventPeriod()] = 1;
            classroomMap.put(eventPeriod.getEvent().getEventId(),periodList);
        }
        List<Pair> bookedList = new ArrayList<Pair>();
        for(Classroom classroom: classroomList){
                int[] periodList = classroomMap.get(classroom.getClassroomId());
                Pair pair = new Pair(classroom,periodList);
                bookedList.add(pair);
            }
        return bookedList;
    }
    //     List<Classroom> classroomList = getAllClassroom();
    //     HashMap<Long, int[]> eventMap = new HashMap<Long, int[]>();
    //     for(Classroom classroom: classroomList){
    //         int[] periodList = new int[24];
    //         eventMap.put(classroom.getClassroomId(),periodList);
    //     }
    //     List<Pair> bookedList = new ArrayList<Pair>();
    //     List<StudyEvent> bookedEventList = studyEventImpl.getByDate(date);
    //     for (StudyEvent event: bookedEventList){
    //         List<StudyEventPeriod> eventPeriodList = eventPeriodDao.findByEvent(event);
    //         for(StudyEventPeriod eventPeriod: eventPeriodList){
    //             int[] periodList = eventMap.get(event.getClassroom().getClassroomId());
    //             periodList[eventPeriod.getEventPeriod()] = 1;
    //             eventMap.put(eventPeriod.getEvent().getClassroom().getClassroomId(),periodList);
    //         }
            
    //     }
    //     for(Classroom classroom: classroomList){
    //             int[] periodList = eventMap.get(classroom.getClassroomId());
    //             Pair pair = new Pair(classroom,periodList);
    //             bookedList.add(pair);
    //         }
    //     return bookedList;
        
}
