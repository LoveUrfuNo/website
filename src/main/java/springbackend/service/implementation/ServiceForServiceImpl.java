/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import springbackend.dao.ServiceDao;
import springbackend.model.Service;
import springbackend.service.ServiceForService;

import java.util.*;

/**
 * Implementation of {@link springbackend.service.ServiceForService} interface.
 */
@org.springframework.stereotype.Service
public class ServiceForServiceImpl implements ServiceForService {
    @Autowired
    private ServiceDao serviceDao;

    @Override
    public void delete(Service service) {
        this.serviceDao.delete(service);
    }

    @Override
    public void save(Service service) {
        this.serviceDao.save(service);
    }

    @Override
    public void saveAndFlush(Service service) {
        this.serviceDao.saveAndFlush(service);
    }

    @Override
    public Service findByUserId(Long userId) {
        return this.serviceDao.findAll().stream()
                .filter(temp -> temp.getUserId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public Service findById(Long id) {
        return this.serviceDao.findById(id);
    }

    @Override
    public Set<Service> findAll() {
        return new HashSet<>(this.serviceDao.findAll());
    }

    @Override
    public Set<Service> findAllByCategory(String category) {
        return this.serviceDao.findAllByCategory(category);
    }

    @Override
    public Set<Service> findAllByUserId(Long userId) {
        return this.serviceDao.findAllByUserId(userId);
    }
}
