/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import springbackend.model.*;
import springbackend.model.Dictionary;
import springbackend.service.*;
import springbackend.validator.ServiceValidator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Controller for {@link springbackend.model.Service}.
 */
@Controller
public class ServiceController {
    @Autowired
    private StringService stringService;

    @Autowired
    private ServiceForService serviceForService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceValidator serviceValidator;

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/add_service", method = RequestMethod.GET)
    public String addService(Model model) {
        model.addAttribute("serviceForm", new Service());

        return "add-service";
    }

    @RequestMapping(value = "/add_service", method = RequestMethod.POST)
    public String addService(@ModelAttribute(value = "serviceForm") Service service,
                             BindingResult bindingResult) {
        this.serviceValidator.validate(service, bindingResult);
        if (bindingResult.hasErrors())
            return "add-service";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(auth.getName());

        service.setUser(user);
        service.setUserId(service.getUser().getId());
        try {
            service.setServiceName(stringService.decoding(service.getServiceName()));
            service.setDescription(stringService.decoding(service.getDescription()));
            this.serviceForService.save(service);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error-page";
        }

        try {
            Dictionary dictionary = new Dictionary();
            this.searchService.initializeDictionary(dictionary);
            this.searchService.saveDictionary(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: add
        }

        return "redirect";
    }

    @RequestMapping(value = "/show_all_services/{category}", method = RequestMethod.GET)
    public String showAllServicesInGivenCategory(@PathVariable(value = "category") String category,
                                                 Model model) {
        Set<Service> services = this.serviceForService.findAllByCategory(category);

        model.addAttribute("services", services);
        model.addAttribute("category", category);

        return "show-given-services";
    }

    @RequestMapping(value = "/show_your_services", method = RequestMethod.GET)
    public String showUsersServices(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(auth.getName());
        user.getServices().sort(
                (s1, s2) -> s1.getServiceName().compareToIgnoreCase(s2.getServiceName()));

        Map<String, Set<UserFile>> fileMap = new HashMap<>();

        Set<UserFile> allSet = this.userFileService.findAllByUserId(user.getId());
        allSet.forEach(f -> fileMap.put(f.getServiceName(),
                new HashSet<>(this.userFileService.findAllByServiceName(f.getServiceName()))));

        model.addAttribute("files", fileMap);
        model.addAttribute("user", user);

        return "user's-services";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteService(@PathVariable(value = "id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(auth.getName());

        Service service = this.serviceForService.findById(id);
        if (service.getUserId().equals(user.getId())) {
            this.serviceForService.delete(service);

            this.userFileService.findAllByUserId(user.getId()).stream()
                    .filter(f -> f.getServiceName().equals(service.getServiceName()))
                    .forEach(temp -> this.userFileService.delete(temp));
        } else {
            return "error-page";
        }

        return "redirect:/show_your_services";
    }
}
