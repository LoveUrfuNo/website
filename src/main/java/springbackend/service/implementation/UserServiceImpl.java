/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import springbackend.dao.RoleDao;
import springbackend.dao.UserDao;
import springbackend.model.Role;
import springbackend.model.User;
import springbackend.service.UserService;

import java.util.*;

/**
 * Implementation of {@link springbackend.service.UserService} interface.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user, Long roleId) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleDao.getOne(roleId));
        user.setRoles(roles);

        this.userDao.save(user);
    }

    @Override
    public void saveAndFlush(User user, Long roleId) {
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleDao.getOne(roleId));
        user.setRoles(roles);

        this.userDao.saveAndFlush(user);
    }

    @Override
    public void delete(User user) {
        this.userDao.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userDao.findByUsername(username);
    }

    @Override
    public User findByLogin(String login) {
        return this.userDao.findAll().stream()
                .filter(temp -> login.equals(temp.getLogin())).findAny().orElse(null);
    }

    @Override
    public User findBuId(Long id) {
        return this.userDao.findById(id);
    }
}
