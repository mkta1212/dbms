package backend.Service;

import java.util.List;

import backend.dbms.models.Status;
import backend.dbms.models.StudyGroup;
import backend.dbms.models.User;

public interface StudyGroupService {
    List<StudyGroup> getByStatus(Status status);
    List<StudyGroup> getAllGroups();
    List<StudyGroup> getByHolder(User user);
    void createGroup(StudyGroup group);
    StudyGroup getByGroupId(Long id);
}
