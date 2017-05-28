/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service;

import springbackend.model.*;
import springbackend.model.Dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Service for site search.
 */
public interface SearchService {
    /**
     *
     */
    SearchRequest getEditedSearchRequest(SearchRequest sourceSearchRequest) throws IOException;

    /**
     * Search for exact occurrences.
     *
     * @param
     */
    TreeSet<Service> getResultServiceSet(SearchRequest searchRequest);

    /**
     *
     */
    ArrayList<String> getStringsForAutoComplete(SearchRequest searchRequest) throws IOException;

    /**
     * @return
     */
    String getAlternativeSearchLine(Map<String, HashMap<String, Integer>> wordsWithDistance,
                                    SearchRequest searchRequest);

    /**
     *
     */
    Map<String, HashMap<String, Integer>> getWordsWithMinimumDistance(SearchRequest searchRequest) throws IOException;

    /**
     * @return
     */
    String getStringByOppositeKeyboardLayout(String sourceString);

    /**
     * @return
     */
    void initializeDictionary(Dictionary dictionary);

    /**
     *
     */
    void saveDictionary(Dictionary dictionary) throws IOException;

    /**
     *
     */
    Dictionary getDictionary() throws IOException;

    /**
     *
     *
     * @param testString
     * @return
     */
    boolean isStringSuitableForDictionary(String testString);
}