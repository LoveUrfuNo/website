/*
 * Copyright (C) 2010 The Android Open Source Project
 * //TODO: add
 */

package springbackend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import springbackend.model.SearchRequest;
import springbackend.model.Service;
import springbackend.service.MetricService;
import springbackend.service.SearchService;
import springbackend.service.ServiceForService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementation of {@link springbackend.service.SearchService} interface.
 */
@org.springframework.stereotype.Service
public class SearchServiceImpl implements SearchService {
    /**
     *
     */
    interface Distance {
        int getDistance(CharSequence word1, CharSequence word2);
    }

    /**
     *
     */
    interface Array {
        ArrayList<String> getArrayFromTexts(String string1, String string2);
    }

    /**
     *
     */
    interface ExactOccerencesInText {
        Boolean isWordIncludingInText(String text);            //TODO: rename
    }

    /**
     *
     */
    interface ExactOccerencesInTree {
        Boolean isWordIncludingInTree(TreeSet<String> tree, String word);
    }

    /**
     *
     */
    interface OppositeWord {
        String getWordFromOppositeKeybordLayout(String sourceWord);
    }

    @Autowired
    private ServiceForService serviceForService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private MetricService metricService;   //TODO: try with stream

    private static final String REGEX_FOR_SPLIT = "[[\\p{P}][\\t\\n\\r\\s]+=№]";

    private static final String REGEX_FOR_REPLACE = "[^а-я\\w-][\\s]{2,}";

    private static final String CYRILLIC_LAYOUT = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";

    private static final String LATIN_LAYOUT = "qwertyuiop[]asdfghjkl;'zxcvbnm,./QWERTYUIOP[]ASDFGHJKL;'ZXCVBNM,./";

    @Override
    public SearchRequest getEditedSearchRequest(SearchRequest sourceSearchRequest) {
        /* Set with words form all services in the base. */
        TreeSet<String> dictionary = searchService.crateDictionary();   //TODO: make the only one for the whole class

        SearchRequest result = new SearchRequest();
        StringBuilder newSearchLine = new StringBuilder();
        String[] wordsFromRequest = sourceSearchRequest.getSearchLine()
                .replaceAll(REGEX_FOR_REPLACE, "")
                .split(REGEX_FOR_SPLIT);

        ExactOccerencesInTree occerences = TreeSet::contains;
        Arrays.stream(wordsFromRequest).forEach(requestWord -> {
            if (occerences.isWordIncludingInTree(dictionary, requestWord)) {
                newSearchLine.append(requestWord);
            } else {
                String oppositeWord
                        = this.searchService.getStringByOppositeKeyboardLayout(requestWord);
                if (occerences.isWordIncludingInTree(dictionary, oppositeWord)) {
                    newSearchLine.append(oppositeWord);
                } else {
                    newSearchLine.append(requestWord);
                }
            }

            newSearchLine.append(' ');
        });

        newSearchLine.deleteCharAt(newSearchLine.length() - 1);  //delete last space
        result.setSearchLine(newSearchLine.toString());

        return result;
    }

    @Override
    public TreeSet<Service> getResultServiceSet(SearchRequest searchRequest) {
        TreeSet<Service> searchResults = new TreeSet<>(Comparator.comparing(Service::getServiceCost));
        Set<Service> allServiceSet = this.serviceForService.findAll();

        String searchLine = searchRequest.getSearchLine().replaceAll(REGEX_FOR_REPLACE, "");

        Array arrayList = (string1, string2) -> {
            ArrayList<String> result = new ArrayList<>();

            result.addAll(Arrays.stream(string1.split(REGEX_FOR_SPLIT))
                    .map(t -> t.replaceAll(REGEX_FOR_REPLACE, ""))
                    .filter(word -> !word.isEmpty()).distinct()
                    .collect(Collectors.toList()));
            result.addAll(Arrays.stream(string2.split(REGEX_FOR_SPLIT))
                    .map(t -> t.replaceAll(REGEX_FOR_REPLACE, ""))
                    .filter(word -> !word.isEmpty()).distinct()
                    .collect(Collectors.toList()));

            return result;
        };

        String[] searchLineWords = searchLine.split(REGEX_FOR_SPLIT);
        ExactOccerencesInText checking = (string) -> Arrays.stream(searchLineWords)
                .allMatch(searchWord -> arrayList.getArrayFromTexts(string, "")
                        .contains(searchWord));

        searchResults.addAll(allServiceSet.stream()
                .filter(service ->
                        checking.isWordIncludingInText(service.getNameOfService())
                                ||
                                checking.isWordIncludingInText(service.getDescription()))
                .collect(Collectors.toSet()));

        //if()      //TODO: add if() in "did_you_meant_it" too so that dont show it if searchResultsSet is sufficiently filled

        searchResults.addAll(allServiceSet.stream()
                .filter(service ->
                        arrayList.getArrayFromTexts(service.getNameOfService(), service.getDescription())
                                .stream().anyMatch(word ->
                                Arrays.stream(searchLineWords)
                                        .collect(Collectors.toList()).contains(word.toLowerCase())))
                .collect(Collectors.toSet()));

        return searchResults;
    }

    @Override
    public ArrayList<String> getStringsForAutoComplete(SearchRequest searchRequest) {
        ArrayList<String> result = new ArrayList<>();

        Map<String, HashMap<String, Integer>> wordsWithDistance
                = this.searchService.getWordsWithMinimumDistance(searchRequest);

        for (int i = 0; i < 5; i++) {
            String alternativeSearchLine = this.searchService.getAlternativeSearchLine(        //TODO: add check language of alternativeSearchLine and searchLine
                    wordsWithDistance,
                    searchRequest);

            result.add(alternativeSearchLine);

            /* Delete words from "wordsWithDistance.value" which are equal to some word from alternativeSearchLine */
            Arrays.stream(alternativeSearchLine.split(" ")).forEach(alternativeWord -> {
                String wordsFromRequest[] = searchRequest.getSearchLine()
                        .replaceAll(REGEX_FOR_REPLACE, " ")
                        .split(REGEX_FOR_SPLIT);

                Arrays.stream(wordsFromRequest).forEach(requestWord ->
                        wordsWithDistance.get(requestWord)
                                .entrySet().removeIf(pair -> pair.getKey().equals(alternativeWord)));
            });
        }

        return result;
    }

    @Override
    public String getAlternativeSearchLine(Map<String, HashMap<String, Integer>> wordsWithDistance,
                                           SearchRequest searchRequest) {
        StringBuilder result = new StringBuilder();
        String[] wordsFromRequest = searchRequest.getSearchLine().split(REGEX_FOR_SPLIT);

        Map<String, Integer> minDistanceMap = new HashMap<>();    //string - requestWord , Integer - distance

        Arrays.stream(wordsFromRequest).forEach(requestWord ->
                minDistanceMap.put(
                        requestWord,
                        wordsWithDistance.get(requestWord).values()
                                .stream().min(Comparator.naturalOrder())
                                .orElse(-1)));

        Arrays.stream(wordsFromRequest).forEach(requestWord -> {
            result.append(
                    wordsWithDistance.get(requestWord).entrySet().stream()
                            .filter(pair ->
                                    pair.getValue()
                                            .equals(minDistanceMap.get(requestWord)))
                            .findAny().get().getKey());

            result.append(" ");     //TODO: check "hasnext" and delete space if hasnt
        });

        result.deleteCharAt(result.length() - 1);    //delete last separating space

        return result.toString();
    }

    @Override
    public Map<String, HashMap<String, Integer>> getWordsWithMinimumDistance(SearchRequest searchRequest) {
        String searchLine = searchRequest.getSearchLine();

        /* Set with words form all services in the base. */
        TreeSet<String> dictionary = searchService.crateDictionary();   //TODO: make the only one for the whole class

        /* Map with words from request and with pair consisting distance & word from dictionary. */
        Map<String, HashMap<String, Integer>> resultMap = new TreeMap<>(Comparator.naturalOrder());

        Distance distance = (dictString, userString) ->
                this.metricService.getPrefixDistance(dictString, userString, 8);

        String[] wordsFromSearchQuery = searchLine.split(REGEX_FOR_SPLIT);
        Arrays.stream(wordsFromSearchQuery)
                .forEach(requestWord -> {
                    HashMap<String, Integer> wordsWithDistance = new HashMap<>();
                    dictionary.forEach(dictWord ->
                            wordsWithDistance.put(
                                    dictWord,
                                    distance.getDistance(dictWord, requestWord))
                    );

                    resultMap.put(requestWord, wordsWithDistance);
                });

        return resultMap;
    }

    @Override
    public String getStringByOppositeKeyboardLayout(String sourceString) {
        OppositeWord oppositeWord = (sourceWord -> {
            int i = 0;
            StringBuilder newWord = new StringBuilder();
            for (char c : sourceWord.toCharArray()) {
                if (CYRILLIC_LAYOUT.indexOf(sourceWord.charAt(i++)) == -1) {
                    newWord.append(CYRILLIC_LAYOUT.charAt(LATIN_LAYOUT.indexOf(c)));
                } else {
                    newWord.append(LATIN_LAYOUT.charAt(CYRILLIC_LAYOUT.indexOf(c)));
                }
            }

            return newWord.toString();
        });

        StringBuilder newSearchLine = new StringBuilder();
        String[] arr = sourceString
                .replaceAll(REGEX_FOR_REPLACE, "")
                .split(REGEX_FOR_SPLIT);
        Arrays.stream(arr).forEach(word -> newSearchLine
                .append(oppositeWord.getWordFromOppositeKeybordLayout(word))
                .append(" "));

        newSearchLine.deleteCharAt(newSearchLine.length() - 1);  //delete last space

        return newSearchLine.toString();
    }

    @Override
    public TreeSet<String> crateDictionary() {
        TreeSet<String> result = new TreeSet<>(String::compareToIgnoreCase);

        Set<Service> allServiceSet = this.serviceForService.findAll();
        allServiceSet.forEach(service -> {
            String[] texts = new String[]{
                    service.getNameOfService(), service.getDescription()};
            Arrays.stream(texts).forEach(text ->
                    result.addAll(Arrays.stream(text.toLowerCase().split(REGEX_FOR_SPLIT))
                            .filter(word ->
                                    this.searchService.isStringSuitableForDictionary(word))
                            .collect(Collectors.toSet())));
        });

        return result;
    }                  //TODO: add save and load dictionary and fixed multiple creation with autocomplete (maybe make 2 dictionaries)

    @Override
    public boolean isStringSuitableForDictionary(String testString) {
        Pattern p = Pattern.compile("^[а-яa-z]{3,}+$");
        Matcher m = p.matcher(testString);
        return m.matches();
    }
}
