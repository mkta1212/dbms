package backend.dbms.Service.Impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.StudyEventService;
import backend.dbms.controllers.Request.StudyEventReq;
import backend.dbms.models.Classroom;
import backend.dbms.models.Course;
import backend.dbms.models.Status;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.StudyEventPeriod;
import backend.dbms.models.User;
import backend.dbms.repository.StudyEventDao;
import backend.dbms.repository.EventId;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class StudyEventImpl implements StudyEventService {
    @Autowired
    private StudyEventDao eventDao;

    @Autowired
    private CourseImpl courseImpl;


    @Autowired
    private StudyEventPeriodImpl eventPeriodImpl;

    @Autowired
    private ClassroomImpl classroomImpl;

    @Override
    public List<StudyEvent> getByStatus(Status status){
        return eventDao.findByStatus(status);
    }

    @Override
    public List<StudyEvent> getAvailableEvent(){
        Date date = new Date(new java.util.Date().getTime());
        return eventDao.findByEventIdJoinStudyEventPeriod(date,Status.Ongoing);

    }

    @Override
    public List<StudyEvent> getAllGroups(){
        return eventDao.findAll();
    }

    @Override
    public List<StudyEvent> getByHolder(User user){
        return eventDao.findByHolder(user);
    }

    @Override
    public void createEvent(StudyEvent event){
        eventDao.save(event);
    }

    @Override
    public void createEvent(StudyEventReq eventReq,User user){
        Course course = courseImpl.getByCourseId(eventReq.getCourseId()).get();
        StudyEvent event = new StudyEvent(user, course, eventReq.getUserMax(), eventReq.getContent(),Status.Ongoing);
        Classroom classroom = classroomImpl.getByClassroomName(eventReq.getRoomName()).get();
        eventDao.save(event);
        for(Integer period: eventReq.getPeriodList()){
            StudyEventPeriod eventPeriod = new StudyEventPeriod(event,classroom,eventReq.getEventDate(),period);
            eventPeriodImpl.createEventPeriod(eventPeriod);
        }
    }

    @Override
    public Optional<StudyEvent> getByEventId(Long id){
        return eventDao.findById(id);
    }
    @Override
    public long count(){
        return eventDao.count();
    }

    // @Override
    // public List<StudyEvent> getByClassroomAndDate(Classroom classroom, Date date) {
    //     return eventDao.findByClassroomAndEventDate(classroom, date);
    // }

    // @Override
    // public List<EventId> getBookedPeriod(Classroom classroom, Date date) {
    //     return eventDao.findAllByClassroomAndEventDate(classroom, date);
    // }

    // @Override
    // public List<StudyEvent> getByDateRange(Date starDate, Date endDate) {
    //     return eventDao.findByEventDateBetween(starDate, endDate);
    // }

    // @Override
    // public List<StudyEvent> getByDate(Date date) {
    //     return eventDao.findByEventDate(date);
    // }
}
