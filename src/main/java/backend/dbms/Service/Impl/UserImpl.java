package backend.dbms.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dbms.Service.UserService;
import backend.dbms.models.User;
import backend.dbms.repository.UserDao;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class UserImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> getByUsername(String username){
        return userDao.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username){
        return userDao.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email){
        return userDao.existsByEmail(email);
    }

    @Override
    public void createUser(User user){
        userDao.save(user);
    }

    @Override
    public long count(){
        return userDao.count();
    }

    @Override
    public List<User> getAllUsers(){
        return userDao.findAll();
    }
}
