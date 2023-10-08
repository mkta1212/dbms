package backend.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import backend.Service.RoleService;
import backend.dbms.models.ERole;
import backend.dbms.models.Role;
import backend.dbms.repository.RoleDao;

public class RoleImpl implements RoleService {
    
    @Autowired
    private RoleDao roleDao;

    @Override
    public Optional<Role> getByName(ERole name){
        return roleDao.findByName(name);
    }
}
