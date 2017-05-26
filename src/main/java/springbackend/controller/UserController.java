/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springbackend.model.SearchRequest;
import springbackend.model.UserFormForTechnicalSupport;
import springbackend.service.StringService;
import springbackend.service.EmailService;
import springbackend.model.User;
import springbackend.service.SecurityService;
import springbackend.service.UserService;
import springbackend.validator.UserFormForTechnicalSupportValidator;
import springbackend.validator.UserOptionsValidator;
import springbackend.validator.UserValidator;

import java.io.*;
import java.util.*;

/**
 * Controller for {@link springbackend.model.User}.
 */
@Controller
public class UserController {
    private static final Integer SIZE_OF_GENERATED_STRING = 32;

    private static final Long ROLE_USER = 1L;

    private static final Long ROLE_NOT_ACTIVATED_USER = 3L;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String MESSAGE_TO_CONFIRM_REGISTRATION = "message-registration-confirm-email.vm";

    private static final String MESSAGE_FOR_TECHNICAL_SUPPORT = "message-for-technical-support.vm";

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserOptionsValidator optionsValidator;

    @Autowired
    private UserFormForTechnicalSupportValidator userForSupportValidator;

    @Autowired
    private StringService stringService;

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect() {
        return "redirect";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model, String error) {
        model.addAttribute("searchRequest", new SearchRequest());

        model.addAttribute("userForm", new User());
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        return "main";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(auth.getName());
        if (user.getFirstName() != null) {
            model.addAttribute("name", user.getFirstName());
        } else {
            model.addAttribute("name", user.getLogin());
        }

        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userForm", new User());
        model.addAttribute("status", "login");
        model.addAttribute("currentUser", user);

        return "main";
    }

    @RequestMapping(value = "/profile/{status}", method = RequestMethod.GET)
    public String profile(@PathVariable("status") String status, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(auth.getName());

        model.addAttribute("searchRequest", new SearchRequest());
        model.addAttribute("name", user.getLogin());
        model.addAttribute("userForm", new User());
        model.addAttribute("status", status);

        String email = user.getUsername(), resultEmailService;
        switch (email.substring(email.indexOf('@') + 1, email.length())) {       //TODO: add more services
            case "gmail.com":
                resultEmailService = "mail.google.com";
                break;
            case "ya.ru":
                resultEmailService = "mail.yandex.ru";
                break;
            case "mail.ru":
                resultEmailService = "e.mail.ru";
                break;
            case "icloud.com":
                resultEmailService = "icloud.com";
                break;
            case "yandex.ru":
                resultEmailService = "yandex.ru";
                break;
            case "mail.com":
                resultEmailService = "mail.com";
                break;
            default:
                resultEmailService = "dealmarketplace.ru";
        }

        model.addAttribute("email_url", resultEmailService);
        return "main";
    }

    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public String options(Model model) {
        model.addAttribute("userOptionForm", new User());

        return "advanced-options";
    }

    @RequestMapping(value = "/options", method = RequestMethod.POST)
    public String options(@ModelAttribute("userOptionForm") User user,
                          BindingResult bindingResult) throws UnsupportedEncodingException {
        this.optionsValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "advanced-options";
        }

        User resultUser;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_USER")) {
            resultUser = this.userService.findByUsername(auth.getName());
        } else if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_NOT_ACTIVATED_USER")) {
            return "redirect:/profile/registration";
        } else if (auth.getAuthorities().stream().findFirst().orElse(null).getAuthority().equals("ROLE_ANONYMOUS")) {
            return "main";
        } else {
            return "service-entrance";
        }

        resultUser.setDateOfBirth(user.getDateOfBirth());
        resultUser.setFirstName(this.stringService.decoding(user.getFirstName()));
        resultUser.setGender(user.getGender());
        resultUser.setCountry(user.getCountry());

        this.userService.saveAndFlush(resultUser, ROLE_USER);

        return "redirect:/redirect";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User user,
                               BindingResult bindingResult,
                               Model model) {
        this.userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("searchRequest", new SearchRequest());
            return "main";
        }

        model.addAttribute("status", "registration");

        user.setKeyForRegistrationConfirmUrl(this.emailService.generateString(SIZE_OF_GENERATED_STRING));
        user.setRegistrationConfirmed(false);    //user didn't confirm acc by email message yet
        this.userService.save(user, ROLE_NOT_ACTIVATED_USER);
        this.securityService.autoLogin(user.getUsername(), user.getConfirmPassword());

        Map map = new HashMap();
        map.put("from", "DEAL");
        map.put("subject", "Hello from " + user.getLogin() + "!");
        map.put("to", user.getUsername());
        map.put("key_for_registration_confirm_url", user.getKeyForRegistrationConfirmUrl());
        map.put("id", user.getId());

        if (this.emailService.sendEmail(MESSAGE_TO_CONFIRM_REGISTRATION, map)) {      //TODO: add output in logger
            System.out.println("Message was sent");
        } else {
            logger.debug(String.format("Error: message wasn't sent to \"%s\"", map.get("to")));
            System.out.println("");
        }

        return "redirect:/profile/registration";
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    public String authentication(Model model) {
        model.addAttribute("userForm", new User());

        return "redirect:/profile";
    }

    @RequestMapping(value = "/confirm-acc/{key}/{id}", method = RequestMethod.GET)
    public String confirmAcc(@PathVariable("key") String key,
                             @PathVariable("id") Long id) {
        User user = this.userService.findBuId(id);
        if (!user.getKeyForRegistrationConfirmUrl().equals(key)) {
            return "error-page";
        }

        user.setRegistrationConfirmed(true);
        user.setKeyForRegistrationConfirmUrl(null);

        this.userService.saveAndFlush(user, ROLE_USER);        //TODO: add output in logger

        return "registration-confirm";
    }

    @RequestMapping(value = {"/support"}, method = RequestMethod.GET)
    public String support(Model model) {
        model.addAttribute("userFormForTechnicalSupport", new UserFormForTechnicalSupport());

        return "support";
    }

    @RequestMapping(value = "/support", method = RequestMethod.POST)
    public String support(@ModelAttribute("userFormForTechnicalSupport") UserFormForTechnicalSupport userForSupport,
                          BindingResult bindingResult) throws UnsupportedEncodingException {
        this.userForSupportValidator.validate(userForSupport, bindingResult);
        if (bindingResult.hasErrors()) {
            return "support";
        }

        userForSupport.setName(this.stringService.decoding(userForSupport.getName()));
        userForSupport.setDescription(this.stringService.decoding(userForSupport.getDescription()));
        userForSupport.setEmail(this.stringService.decoding(userForSupport.getEmail()));
        userForSupport.setSubject(this.stringService.decoding(userForSupport.getSubject()));

        Map map = new HashMap();
        map.put("from", "DEAL");
        map.put("subject", "Hello from " + userForSupport.getName() + "!");
        map.put("to", "deal.agentservice@gmail.com");      //TODO: rename field "username" to "email"
        map.put("message", userForSupport.getDescription());
        map.put("name", userForSupport.getName());
        map.put("subject", userForSupport.getSubject());

        if (this.emailService.sendEmail(MESSAGE_FOR_TECHNICAL_SUPPORT, map)) {      //TODO: add output in logger
            System.out.println("Message was sent");
        } else {
            System.out.println("Error: message wasn't sent");
        }

        return "redirect:/redirect";
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin() {
        return "admin";
    }
}