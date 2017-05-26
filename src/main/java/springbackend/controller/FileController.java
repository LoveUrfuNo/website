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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import springbackend.model.User;
import springbackend.model.UserFile;
import springbackend.service.StringService;
import springbackend.service.UserFileService;
import springbackend.service.UserService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Controller for operations with download files
 */
@Controller
public class FileController {
    @Autowired
    private UserFileService userFileService;

    @Autowired
    private UserService userService;

    @Autowired
    private StringService stringService;

    private static final String rootPath =
            "C:\\Users\\kosty\\IntellijProjects\\Deal\\backend\\src\\main\\webapp\\resources\\user's\\";

    private static final Long ROLE_USER = 1L;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value = "/uploadFile/{dataAboutRequest}", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @PathVariable("dataAboutRequest") String operation) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = this.userService.findByUsername(auth.getName());

        File uploadedFile = null;
        String name = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File dir = null;
                if (operation.contains("loadAvatar")) {
                    name = currentUser.getLogin() + "'s_avatar_" + file.getOriginalFilename();
                    dir = new File(rootPath + "/images/avatars" + File.separator);
                } else if (operation.contains("loadServicePhoto")) {
                    name = currentUser.getLogin() + "'s_service_photo_"
                            + operation.substring(operation.indexOf('+') + 1) + "_" + file.getOriginalFilename();
                    dir = new File(rootPath + "/images/for_services" + File.separator);
                }

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile))) {
                    stream.write(bytes);
                    stream.flush();
                }

                logger.info("uploaded: " + uploadedFile.getAbsolutePath());
                logger.info("You successfully uploaded file=" + name);
            } catch (Throwable e) {
                logger.info("You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            logger.info("You failed to upload " + name + " because the file was empty.");
        }

        assert uploadedFile != null;
        String path = uploadedFile.getAbsolutePath();
        if (operation.contains("loadAvatar")) {
            currentUser.setAvatar(this.stringService.makePathForFile(path));

            this.userService.saveAndFlush(currentUser, ROLE_USER);
        } else if (operation.contains("loadServicePhoto")) {
            UserFile userFile = new UserFile();
            userFile.setTypeOfFile("photo");
            userFile.setPathToFile(this.stringService.makePathForFile(path));
            userFile.setServiceName(operation.substring(operation.indexOf('+') + 1));
            userFile.setUserId(currentUser.getId());

            this.userFileService.save(userFile);
        }

        return "redirect";
    }
}
