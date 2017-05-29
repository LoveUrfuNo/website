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
import org.springframework.web.bind.annotation.*;

import springbackend.model.SearchRequest;
import springbackend.model.Service;
import springbackend.model.User;
import springbackend.service.SearchService;
import springbackend.validator.SearchValidator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for {@link springbackend.model.SearchRequest}.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchValidator searchValidator;

    @Autowired
    private SearchService searchService;

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private static final String REGEX_FOR_REPLACE = "[^а-я\\w-][\\s]{2,}";   //TODO: try to import from SearchServiceImpl

    private static final String REGEX_FOR_SPLIT = "[[\\p{P}][\\t\\n\\r\\s]+=№]";

    private static final String ERROR_BY_AUTO_COMPLETE
            = "Error: %s. Can't get array of strings with auto complete variants from" +
            " this.searchService.getStringsForAutoComplete by request: \"%s\"";

    private static final String ERROR_BY_DECODING_STRING = "Error: %s. From \"%s\"";

    private static final String ERROR_BY_Get_WORDS_WITH_DISTANCE = "Error: %s." +
            " Can't get wordsWithDistance from this.searchService.getWordsWithMinimumDistance";

    private static final String DID_YOU_MEANT_IT = "did_you_meant_it";

    private static final String SEARCH_RESULTS = "search_results";

    private static final String SEARCH_INSTEAD_THIS = "search_instead_this";

    private static final String RESULTS_FOR = "results_of_the_request_are_shown";

    @ResponseBody
    @RequestMapping(value = "/auto_complete/{request}", method = RequestMethod.GET)
    public ArrayList<String> autoComplete(@PathVariable(value = "request") String currentRequest) {
        SearchRequest searchRequest = new SearchRequest();

        currentRequest = currentRequest.trim();
        searchRequest.setSearchLine(currentRequest);

        ArrayList<String> resultArray = null;
        try {
            resultArray = this.searchService
                    .getStringsForAutoComplete(searchRequest);
        } catch (IOException e) {
            logger.debug(String.format(
                    ERROR_BY_AUTO_COMPLETE, e.getMessage(), searchRequest.getSearchLine()));
            e.printStackTrace();
        }

        if (resultArray.isEmpty()) {
            SearchRequest editedSearchRequest;
            try {
                editedSearchRequest = this.searchService.getEditedSearchRequest(searchRequest);
                resultArray = this.searchService
                        .getStringsForAutoComplete(editedSearchRequest);
            } catch (IOException e) {
                logger.debug(String.format(
                        ERROR_BY_AUTO_COMPLETE, e.getMessage(), searchRequest.getSearchLine()));
                e.printStackTrace();
            }
        }

        return resultArray;
    }

    @RequestMapping(value = "/search_services", method = RequestMethod.POST)
    public String searchServices(@ModelAttribute(value = "searchRequest") SearchRequest searchRequest,
                                 Model model,
                                 BindingResult bindingResult) {
        searchValidator.validate(searchRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userForm", new User());
            return "redirect";
        }

        try {
            String sourceSearchLineWithoutMultipleSpaces
                    = searchRequest.getSearchLine().replaceAll(REGEX_FOR_REPLACE, " ");
            String decodedSearchLine = new String(
                    sourceSearchLineWithoutMultipleSpaces.getBytes("ISO-8859-1"),
                    "UTF-8");

            searchRequest.setSearchLine(decodedSearchLine.toLowerCase());
        } catch (UnsupportedEncodingException e) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            logger.debug(String.format(ERROR_BY_DECODING_STRING, e.getMessage(), auth.getName()));
            e.printStackTrace();

            model.addAttribute("error_in_site_search", "vvedite drugoe plz");         //TODO: add in jsp

            return "redirect";
        }

        SearchRequest editedSearchRequest;
        try {
            editedSearchRequest = this.searchService.getEditedSearchRequest(searchRequest);
        } catch (IOException e) {
            logger.debug(String.format(
                    ERROR_BY_AUTO_COMPLETE, e.getMessage(), searchRequest.getSearchLine()));
            e.printStackTrace();

            return "redirect";  //TODO: add message in jsp with information about error
        }

        TreeSet<Service> finalSearchResults
                = this.searchService.getResultServiceSet(editedSearchRequest);

        boolean isNewSearchRequestEqualOriginal =
                Arrays.equals(editedSearchRequest.getSearchLine().split(REGEX_FOR_SPLIT),
                        searchRequest.getSearchLine().split(REGEX_FOR_SPLIT));

        if (isNewSearchRequestEqualOriginal) {
            Map<String, HashMap<String, Integer>> wordsWithDistance;
            try {
                wordsWithDistance = this.searchService.getWordsWithMinimumDistance(editedSearchRequest);
            } catch (IOException e) {
                logger.debug(String.format(ERROR_BY_Get_WORDS_WITH_DISTANCE, e.getMessage()));
                e.printStackTrace();

                return "redirect";     //TODO: add message in jsp with information about error
            }

            String alternativeSearchLine = this.searchService.getAlternativeSearchLine(
                    wordsWithDistance, editedSearchRequest);

            boolean isAlternativeSearchLineNeeded
                    = alternativeSearchLine.equalsIgnoreCase(editedSearchRequest.getSearchLine())
                    || this.searchService.isAlternativeSearchLineNeeded(
                            finalSearchResults.size(), searchRequest.getCategory());

            if (isAlternativeSearchLineNeeded) {
                model.addAttribute(DID_YOU_MEANT_IT, alternativeSearchLine);
            } else {
                model.addAttribute(DID_YOU_MEANT_IT, null);
            }
        } else {
            model.addAttribute(RESULTS_FOR, editedSearchRequest.getSearchLine());
            model.addAttribute(SEARCH_INSTEAD_THIS, searchRequest.getSearchLine());
        }

        model.addAttribute(SEARCH_RESULTS, finalSearchResults);

        return "searching-results";
    }
}
