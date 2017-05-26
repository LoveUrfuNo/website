/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.transaction.annotation.Transactional;

import springbackend.model.Role;
import springbackend.model.User;
import springbackend.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link UserDetailsService} interface.
 */
public class UserDetailsServiceImpl extends JdbcDaoImpl {
    private static final Long ROLE_NOT_ACTIVATED_USER = 3L;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findByUsername(username);
        if (user == null) {
            user = this.userService.findByLogin(username);
        }

        if (!user.getRegistrationConfirmed() && !user.getRoles().stream()
                .findFirst().orElse(null).getId().equals(ROLE_NOT_ACTIVATED_USER)) {
            return null;
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
