/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service;

import springbackend.model.User;

/**
 * Service class for {@link springbackend.model.User}
 */
public interface UserService {
    /**
     * Saves user for the first time.
     *
     * @param user   - user which would be save
     * @param roleId - id of role (ROLE_ANONYMOUS, ROLE_USER, ROLE_NOT_ACTIVATED_USER, ROLE_ADMIN)
     */
    void save(User user, Long roleId);

    /**
     * Saves user with replace.
     *
     * @param user   - user which would be save
     * @param roleId - id of role
     */
    void saveAndFlush(User user, Long roleId);

    /**
     * Deletes user.
     *
     * @param user - user which would be delete.
     */
    void delete(User user);

    /**
     * Finds user by his name.
     *
     * @param username - user name
     * @return found user.
     */
    User findByUsername(String username);

    /**
     * Finds user by id.
     *
     * @param id - user id
     * @return found user.
     */
    User findBuId(Long id);

    /**
     * Finds user by his login.
     *
     * @param login - user login
     * @return found user.
     */
    User findByLogin(String login);
}
