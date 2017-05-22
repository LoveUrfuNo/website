/*
 * Copyright (C) 2010 The Android Open Source Project
 * //TODO: add
 */

package springbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbackend.model.PersistentUser;

/**
 * Dao for class {@link springbackend.model.PersistentUser}.
 */
public interface PersistentUserDao extends JpaRepository<PersistentUser, Long> {
    PersistentUser getByUsername(String username);
}
