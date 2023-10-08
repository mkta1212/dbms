package backend.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import backend.Service.StudyGroupService;
import backend.dbms.models.Status;
import backend.dbms.models.StudyGroup;
import backend.dbms.models.User;
import backend.dbms.repository.StudyGroupDao;

public class StudyGroupImpl implements StudyGroupService {
    @Autowired
    private StudyGroupDao groupDao;

    @Override
    public List<StudyGroup> getByStatus(Status status){
        return groupDao.findByStatus(status);
    }

    @Override
    public List<StudyGroup> getAllGroups(){
        return groupDao.findAll();
    }

    @Override
    public List<StudyGroup> getByHolder(User user){
        return groupDao.findByHolder(user);
    }

    @Override
    public void createGroup(StudyGroup group){
        groupDao.save(group);
    }

    @Override
    public StudyGroup getByGroupId(Long id){
        return groupDao.findById(id).get();
    }
}
