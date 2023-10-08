package backend.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import backend.Service.UserService;
import backend.dbms.models.User;
import backend.dbms.repository.UserDao;

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
}
