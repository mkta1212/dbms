package backend.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import backend.Service.ParticipationService;
import backend.dbms.models.Participantion;
import backend.dbms.models.StudyGroup;
import backend.dbms.models.User;
import backend.dbms.repository.ParticipantionDao;

public class ParticipationImpl implements ParticipationService {

    @Autowired
    private ParticipantionDao participantionDao;

    @Override
    public List<Participantion> getByUser(User user) {
        return participantionDao.findByUser(user);
    }

    @Override
    public void deleteByUserAndGroup(User user, StudyGroup group) {
        participantionDao.deleteByUserAndGroup(user, group);
    }

    @Override
    public long countByGroup(StudyGroup group) {
        return participantionDao.countByGroup(group);
    }
    
    @Override
    public void createParticipation(Participantion participantion){
        participantionDao.save(participantion);
    }
}
