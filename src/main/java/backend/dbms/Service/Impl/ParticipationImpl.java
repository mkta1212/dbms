package backend.dbms.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.ParticipationService;
import backend.dbms.models.Participation;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.User;
import backend.dbms.repository.ParticipationDao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class ParticipationImpl implements ParticipationService {

    @Autowired
    private ParticipationDao participantionDao;

    @Override
    public List<Participation> getByUser(User user) {
        return participantionDao.findByUser(user);
    }

    @Override
    public void delete(User user, StudyEvent event) {
        participantionDao.deleteByUserAndEvent(user, event);
    }

    @Override
    public long count(StudyEvent event) {
        return participantionDao.countByEvent(event);
    }
    
    @Override
    public void createParticipation(Participation participantion){
        participantionDao.save(participantion);
    }
}
