/*
 * Copyright (C) 2017 The Open Source Project
 */

package springbackend.service;

import springbackend.model.*;
import springbackend.model.Dictionary;

import java.io.IOException;
import java.util.*;

/**
 * Service for site search by {@link springbackend.model.SearchRequest}.
 */
public interface SearchService {
    /**
     * Returns new search request. If word isn't found in dictionary then
     * we try to find in dictionary this word in opposite keyboard layout,
     * and if we successfully found new opposite word then we added its in new search request,
     * but if we not found opposite word in dictionary,
     * then we added in new request word from source request.
     *
     * @param sourceSearchRequest - original search request with search line and category of services.
     * @return new search request by corrections of original request.
     */
    SearchRequest getEditedSearchRequest(SearchRequest sourceSearchRequest);

    /**
     * Returns a service set which satisfies the search conditions and
     * at least one search line word is contained in the name or in the description of service.
     *
     * @param searchRequest - search request with search line and category of services.
     * @return set of services.
     */
    TreeSet<Service> getResultServiceSet(SearchRequest searchRequest);

    /**
     * Returns five strings for auto complete at entering search line on the basis getAlternativeSearchLine(...).
     *
     * @param searchRequest - search request with search line and category of services.
     * @return ArrayList with 5 result strings.
     * @throws IOException - from getWordsWithMinimumDistance().
     */
    ArrayList<String> getStringsForAutoComplete(SearchRequest searchRequest) throws IOException;

    /**
     * Returns alternative search line consisting of words with minimum distance(metric).
     *
     * @param wordsWithDistance - map with words from search line and words from dictionary with distance.
     *                          Key - word from search line, Value - map with words and distance.
     * @param searchRequest     - search request with search line and category of services.
     * @return string with alternative search line.
     */
    String getAlternativeSearchLine(Map<String, HashMap<String, Integer>> wordsWithDistance,
                                    SearchRequest searchRequest);

    /**
     * Checks that based on ratio numberOfFoundServices to number of all services in selected category.
     *
     * @param numberOfFoundServices - number of services satisfying the search conditions.
     * @param category              - selected category.
     * @return boolean result.
     */
    boolean isAlternativeSearchLineNeeded(long numberOfFoundServices, String category);

    /**
     * Returns map with words from request and with pair consisting distance and word from dictionary.
     *
     * @param searchRequest - search request with search line and category of services.
     * @return result map.
     * @throws IOException - from saveDictionary(...).
     */
    Map<String, HashMap<String, Integer>> getWordsWithMinimumDistance(SearchRequest searchRequest) throws IOException;

    /**
     * Checks language of each word from sourceString and added opposite word to result string.
     *
     * @param sourceString - string to be edit.
     * @return result string.
     */
    String getStringByOppositeKeyboardLayout(String sourceString);

    /**
     * Initializes dictionary by all services.
     *
     * @param dictionary - object Dictionary with words set.
     */
    void initializeDictionary(Dictionary dictionary);

    /**
     * Serializes dictionary.
     *
     * @param dictionary - object Dictionary with words set.
     * @throws IOException - FileNotFoundException and IOException
     * thrown down into the controller {@link springbackend.controller.SearchController}
     */
    void saveDictionary(Dictionary dictionary) throws IOException;

    /**
     * Deserializes dictionary and returns object with dictionary words set.
     *
     * @return object Dictionary. {@link springbackend.model.Dictionary}
     */
    Dictionary getDictionary();

    /**
     * Checks that string has normal length, and has only letters.
     *
     * @param testString - string to be check.
     * @return boolean result.
     */
    boolean isStringSuitableForDictionary(String testString);
}