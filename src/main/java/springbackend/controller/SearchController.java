/*
 * Copyright (C) 2010 The Android Open Source Project
 * TODO: add
 */

package springbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import springbackend.model.SearchRequest;
import springbackend.model.Service;
import springbackend.model.User;
import springbackend.service.SearchService;
import springbackend.validator.SearchValidator;

import java.io.UnsupportedEncodingException;
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

    private static final String REGEX_FOR_REPLACE = "[^а-я\\w-][\\s]{2,}";   //TODO: try to import from SearchServiceImpl

    private static final String REGEX_FOR_SPLIT = "[[\\p{P}][\\t\\n\\r\\s]+=№]";

    @ResponseBody
    @RequestMapping(value = "/auto_complete/{request}", method = RequestMethod.GET)
    public ArrayList<String> autoComplete(@PathVariable(value = "request") String currentRequest) {
        SearchRequest searchRequest = new SearchRequest();

        currentRequest = currentRequest.trim();
        searchRequest.setSearchLine(currentRequest);

        ArrayList<String> resultArray = this.searchService
                .getStringsForAutoComplete(searchRequest);

        if (resultArray.isEmpty()) {
            SearchRequest editedSearchRequest
                    = this.searchService.getEditedSearchRequest(searchRequest);
            resultArray = this.searchService
                    .getStringsForAutoComplete(editedSearchRequest);
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
            String sourceSearchLineWithoutMultipleSpaces = searchRequest.getSearchLine().replaceAll(REGEX_FOR_REPLACE, " ");
            String decodedSearchLine = new String(
                    sourceSearchLineWithoutMultipleSpaces.getBytes("ISO-8859-1"), "UTF-8");

            searchRequest.setSearchLine(decodedSearchLine.toLowerCase());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            model.addAttribute("error_in_site_search", "vvedite drugoe plz");  //TODO: add in jsp
            return "redirect";
        }

        SearchRequest editedSearchRequest
                = this.searchService.getEditedSearchRequest(searchRequest);
        TreeSet<Service> finalSearchResults
                = this.searchService.getResultServiceSet(editedSearchRequest);

        boolean isNewSearchRequestEqualOriginal =
                Arrays.equals(
                        editedSearchRequest.getSearchLine().split(REGEX_FOR_SPLIT),
                        searchRequest.getSearchLine().split(REGEX_FOR_SPLIT));
        if (isNewSearchRequestEqualOriginal) {
            Map<String, HashMap<String, Integer>> wordsWithDistance
                    = this.searchService.getWordsWithMinimumDistance(editedSearchRequest);

            String alternativeSearchLine = this.searchService.getAlternativeSearchLine(
                    wordsWithDistance, editedSearchRequest);
            if (alternativeSearchLine.equalsIgnoreCase(editedSearchRequest.getSearchLine())) {
                model.addAttribute("did_you_meant_it", null);
            } else {
                model.addAttribute("did_you_meant_it", alternativeSearchLine);
            }
        } else {
            model.addAttribute("results_of_the_request_are_shown", editedSearchRequest.getSearchLine());
            model.addAttribute("search_instead_this", searchRequest.getSearchLine());
        }

        model.addAttribute("search_results", finalSearchResults);

        return "searching-results";
    }
}
