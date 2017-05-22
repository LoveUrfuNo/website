/*
 * Copyright (C) 2010 The Android Open Source Project
 * //TODO: add
 */

package springbackend.service;

import springbackend.model.SearchRequest;
import springbackend.model.Service;

import java.util.*;

/**
 * Service for site search.
 */
public interface SearchService {
    /**
     *
     */
    SearchRequest getEditedSearchRequest(SearchRequest sourceSearchRequest);

    /**
     * Search for exact occurrences.
     *
     * @param
     */
    TreeSet<Service> getResultServiceSet(SearchRequest searchRequest);

    /**
     *
     */
    ArrayList<String> getStringsForAutoComplete(SearchRequest searchRequest);

    /**
     * @return
     */
    String getAlternativeSearchLine(Map<String, HashMap<String, Integer>> wordsWithDistance,
                                    SearchRequest searchRequest);

    /**
     *
     */
    Map<String, HashMap<String, Integer>> getWordsWithMinimumDistance(SearchRequest searchRequest);

    /**
     * @return
     */
    String getStringByOppositeKeyboardLayout(String sourceString);

    /**
     * @return
     */
    TreeSet<String> crateDictionary();

    /**
     * 
     *
     * @param testString
     * @return
     */
    boolean isStringSuitableForDictionary(String testString);
}