/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbackend.dao.UserFileDao;
import springbackend.model.UserFile;
import springbackend.service.UserFileService;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link springbackend.service.UserFileService} interface.
 */
@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    private UserFileDao userFileDao;

    @Override
    public void save(UserFile file) {
        this.userFileDao.save(file);
    }

    @Override
    public void saveAndFlush(UserFile file) {
        this.userFileDao.saveAndFlush(file);
    }

    @Override
    public void delete(UserFile file) {
        this.userFileDao.delete(file);
    }

    @Override
    public Set<UserFile> findAll() {
        return new HashSet<>(this.userFileDao.findAll());
    }

    @Override
    public Set<UserFile> findAllByUserId(Long id) {
        return this.userFileDao.findAllByUserId(id);
    }

    @Override
    public Set<UserFile> findAllByServiceName(String name) {
        return this.userFileDao.findAllByServiceName(name);
    }
}
