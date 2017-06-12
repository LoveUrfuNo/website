/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.service;

import java.io.UnsupportedEncodingException;

/**
 * Service for decoding string from jsp.
 */
public interface StringService {
    /**
     * Changes encoding of string from "ISO8859-1" to "UTF-8".
     *
     * @param sourceString - string which will be decoding
     * @return string which has been decode.
     * @throws UnsupportedEncodingException - exception if impossible to decode a string
     */
    String decoding(String sourceString) throws UnsupportedEncodingException;

    /**
     * Returns short file path from "/resources" to the end for saving in database.
     *
     * @param sourceString - full file path
     * @return result.
     */
    String makePathForFile(String sourceString);
}
