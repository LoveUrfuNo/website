/*
 * Copyright (C) 2017 The Open Source Project
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
    void initializeDictionary();

    /**
     *
     * @param dictionary
     */
    void saveDictionary(TreeSet<String> dictionary);

    /**
     *
     */
    TreeSet<String> getDictionary();

    /**
     *
     *
     * @param testString
     * @return
     */
    boolean isStringSuitableForDictionary(String testString);
}