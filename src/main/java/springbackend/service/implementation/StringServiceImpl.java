/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.service.implementation;

import org.springframework.stereotype.Service;

import springbackend.service.StringService;

import java.io.UnsupportedEncodingException;

/**
 * Implementation of {@link StringService} interface.
 */
@Service
public class StringServiceImpl implements StringService {
    @Override
    public String decoding(String sourceString) throws UnsupportedEncodingException {
        return new String(sourceString.getBytes("ISO8859-1"), "UTF-8");
    }

    @Override
    public String makePathForFile(String sourceString) {
        return sourceString.substring(sourceString.indexOf("resources") - 1, sourceString.length());
    }
}
