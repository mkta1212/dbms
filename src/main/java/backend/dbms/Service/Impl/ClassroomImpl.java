package backend.dbms.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.ClassroomService;
import backend.dbms.models.Classroom;
import backend.dbms.repository.ClassroomDao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class ClassroomImpl implements ClassroomService {

    @Autowired
    private ClassroomDao classroomDao;

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

}
