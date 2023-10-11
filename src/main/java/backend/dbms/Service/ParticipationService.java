package backend.dbms.Service;

import java.util.List;

import backend.dbms.models.Participantion;
import backend.dbms.models.StudyEvent;
import backend.dbms.models.User;

public interface ParticipationService {

    List<Participantion> getByUser(User user);
    void delete(User user, StudyEvent event);
    long count(StudyEvent event);
    void createParticipation(Participantion participantion);
}
