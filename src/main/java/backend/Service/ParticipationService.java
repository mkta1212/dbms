package backend.Service;

import java.util.List;

import backend.dbms.models.Participantion;
import backend.dbms.models.StudyGroup;
import backend.dbms.models.User;

public interface ParticipationService {

    List<Participantion> getByUser(User user);
    void deleteByUserAndGroup(User user, StudyGroup group);
    long countByGroup(StudyGroup group);
    void createParticipation(Participantion participantion);
}
