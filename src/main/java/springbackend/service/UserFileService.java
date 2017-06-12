/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.service;

import springbackend.model.UserFile;

import java.util.Set;

/**
 * Service class for {@link springbackend.model.UserFile}
 */
public interface UserFileService {
    /**
     * Saves file for the first time.
     *
     * @param file - file which should be saved.
     */
    void save(UserFile file);

    /**
     * Saves file with replace.
     *
     * @param file - service which would be saved.
     */
    void saveAndFlush(UserFile file);

    /**
     * Deletes file.
     *
     * @param file - file which would be delete.
     */
    void delete(UserFile file);

    /**
     * Finds all files.
     *
     * @return found set of files.
     */
    Set<UserFile> findAll();

    /**
     * Finds all files by owner id.
     *
     * @param id - owner id
     * @return set of files.
     */
    Set<UserFile> findAllByUserId(Long id);

    /**
     * Finds all files by service name.
     *
     * @param name - service name
     * @return set of files.
     */
    Set<UserFile> findAllByServiceName(String name);
}
