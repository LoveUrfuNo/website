/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.model;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a User.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_registration_confirmed")
    private Boolean isRegistrationConfirmed;

    @Column(name = "key_for_registration_confirm")
    private String keyForRegistrationConfirmUrl;

    @Column(name = "login")
    private String login;

    @Column(name = "date_of_registration")
    private Date dateOfRegistration;

    @Transient
    private String confirmPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "country")
    private String country;

    @Column(name = "path_to_avatar")
    private String avatar;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Service> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRegistrationConfirmed() {
        return isRegistrationConfirmed;
    }

    public void setRegistrationConfirmed(Boolean registrationConfirmed) {
        isRegistrationConfirmed = registrationConfirmed;
    }

    public String getKeyForRegistrationConfirmUrl() {
        return keyForRegistrationConfirmUrl;
    }

    public void setKeyForRegistrationConfirmUrl(String keyForRegistrationConfirmUrl) {
        this.keyForRegistrationConfirmUrl = keyForRegistrationConfirmUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isRegistrationConfirmed=" + isRegistrationConfirmed +
                ", keyForRegistrationConfirmUrl='" + keyForRegistrationConfirmUrl + '\'' +
                ", login='" + login + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", country='" + country + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                ", services=" + services +
                '}';
    }
}
