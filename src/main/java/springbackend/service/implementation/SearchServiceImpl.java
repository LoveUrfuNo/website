/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import springbackend.model.*;
import springbackend.model.Dictionary;
import springbackend.service.MetricService;
import springbackend.service.SearchService;
import springbackend.service.ServiceForService;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Implementation of {@link springbackend.service.SearchService} interface.
 */
@org.springframework.stereotype.Service
public class SearchServiceImpl implements SearchService {
    interface Distance {
        /**
         * Functional interface, which gets metric from {@link springbackend.service.implementation.MetricServiceImpl}.
         *
         * @param word1 in our case - dictionary word.
         * @param word2 - prefix.
         * @return metric(distance).
         */
        int getDistance(CharSequence word1, CharSequence word2);
    }

    interface Array {
        /**
         * Functional interface, which gets ArrayList with words by text.
         *
         * @param string - text.
         * @return array with words.
         */
        ArrayList<String> getArrayFromText(String string);
    }

    interface ExactOccurrencesInTree {
        /**
         * Functional interface, which checks word on including in any string in tree.
         *
         * @param tree - treeSet with words.
         * @param word - word to be check.
         * @return boolean result.
         */
        Boolean isWordIncludingInTree(TreeSet<String> tree, String word);
    }

    interface OppositeWord {
        /**
         * Functional interface, which translates sourceWord on opposite keyboard and
         * return if we need it, and, otherwise, returns the original word.
         *
         * @return result string.
         */
        String getWordFromOppositeKeyboardLayout(String sourceWord);
    }

    @Autowired
    private ServiceForService serviceForService;

    @Autowired
    private MetricService metricService;

    /**
     * Object with words from all services in the base.
     */
    private Dictionary dictionary = new Dictionary();

    private long numberOFAllServices;    //is filled in getResultServiceSet(...)

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    public static final String REGEX_FOR_SPLIT = "[[\\p{P}][\\t\\n\\r\\s]+=№]";

    public static final String REGEX_FOR_REPLACE = "[^а-я\\w-][\\s]{2,}";

    private static final String REGEX_FOR_VERIFYING_WORD_FOR_THE_DICTIONARY = "^[а-яa-z]{3,50}+$";

    private static final String CYRILLIC_LAYOUT = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";

    private static final String LATIN_LAYOUT = "qwertyuiop[]asdfghjkl;'zxcvbnm,./QWERTYUIOP[]ASDFGHJKL;'ZXCVBNM,./";

    private static final String DICTIONARY_FILE_NAME = "dictionary.dat";

    private static final String CATEGORY_TYPE_ALL = "all";

    private static final String ERROR_BY_DESERIALIZATION = "Error: %s, by deserialization";

    private static final int NUMBER_OF_SERVICES_CATEGORY = 3;

    private static final int MINIMUM_SIZE_OF_FOUND_SERVICES = 5;

    @Override
    public SearchRequest getEditedSearchRequest(SearchRequest sourceSearchRequest) {
        SearchRequest result = new SearchRequest();
        StringBuilder newSearchLine = new StringBuilder();
        final String[] wordsFromRequest = sourceSearchRequest.getSearchLine()
                .replaceAll(REGEX_FOR_REPLACE, "")
                .split(REGEX_FOR_SPLIT);

        if (dictionary.getDictionaryContent().isEmpty())
            this.dictionary = getDictionary();

        ExactOccurrencesInTree occurrences = TreeSet::contains;

        Arrays.stream(wordsFromRequest).forEach(requestWord -> {
            final TreeSet<String> dictionaryContent = this.dictionary.getDictionaryContent();
            if (occurrences.isWordIncludingInTree(dictionaryContent, requestWord)) {
                newSearchLine.append(requestWord);
            } else {
                final String oppositeWord = getStringByOppositeKeyboardLayout(requestWord);
                if (occurrences.isWordIncludingInTree(dictionaryContent, oppositeWord)) {
                    newSearchLine.append(oppositeWord);
                } else {
                    newSearchLine.append(requestWord);
                }
            }

            newSearchLine.append(' ');
        });

        newSearchLine.deleteCharAt(newSearchLine.length() - 1);  //deletes last space
        result.setSearchLine(newSearchLine.toString());
        result.setCategory(sourceSearchRequest.getCategory());

        return result;
    }

    @Override
    public TreeSet<Service> getResultServiceSet(SearchRequest searchRequest) {
        TreeSet<Service> searchResults
                = new TreeSet<>(Comparator.comparing(Service::getServiceCost));
        final Set<Service> allServiceSet = this.serviceForService.findAll();
        final String searchLine = searchRequest.getSearchLine().replaceAll(REGEX_FOR_REPLACE, "");

        Array arrayList = (string) -> {
            ArrayList<String> result = new ArrayList<>();
            result.addAll(Arrays.stream(string.split(REGEX_FOR_SPLIT))
                    .map(t -> t.replaceAll(REGEX_FOR_REPLACE, ""))
                    .filter(word -> !word.isEmpty()).distinct()
                    .collect(Collectors.toList()));

            return result;
        };

        final String[] searchLineWords = searchLine.split(REGEX_FOR_SPLIT);
        searchResults.addAll(allServiceSet.stream()
                .filter(service -> {
                    // is category of current service equals with search request category?
                    boolean isServiceCategorySuitable =
                            searchRequest.getCategory().equals(CATEGORY_TYPE_ALL)
                                    || service.getCategory().equals(searchRequest.getCategory());

                    return arrayList.getArrayFromText(service.getServiceName() + " " + service.getDescription())
                            .stream().anyMatch(word ->
                                    Arrays.stream(searchLineWords)
                                            .collect(Collectors.toList())
                                            .contains(word.toLowerCase()) && isServiceCategorySuitable);
                }).collect(Collectors.toSet()));

        this.numberOFAllServices = searchResults.size();   // for method isAlternativeSearchLineNeeded(...)

        return searchResults;
    }

    @Override
    public ArrayList<String> getStringsForAutoComplete(SearchRequest searchRequest) throws IOException {
        ArrayList<String> result = new ArrayList<>();

        final Map<String, HashMap<String, Integer>> wordsWithDistance
                = getWordsWithMinimumDistance(searchRequest);
        for (int i = 0; i < 5; i++) {
            final String alternativeSearchLine = getAlternativeSearchLine(
                    wordsWithDistance,
                    searchRequest);
            result.add(alternativeSearchLine);

            // Delete words from "wordsWithDistance.value" which are equal to some word from alternativeSearchLine
            Arrays.stream(alternativeSearchLine.split(" ")).forEach(alternativeWord -> {
                String wordsFromRequest[] = searchRequest.getSearchLine()
                        .replaceAll(REGEX_FOR_REPLACE, " ")
                        .split(REGEX_FOR_SPLIT);

                Arrays.stream(wordsFromRequest).forEach(requestWord ->
                        wordsWithDistance.get(requestWord)
                                .entrySet()
                                .removeIf(pair -> pair.getKey().equals(alternativeWord)));
            });
        }

        return result;
    }

    @Override
    public String getAlternativeSearchLine(Map<String, HashMap<String, Integer>> wordsWithDistance,
                                           SearchRequest searchRequest) {
        StringBuilder result = new StringBuilder();
        final String[] wordsFromRequest = searchRequest.getSearchLine().split(REGEX_FOR_SPLIT);

        Map<String, Integer> minDistanceMap = new HashMap<>();    //string - requestWord , Integer - distance
        Arrays.stream(wordsFromRequest).forEach(requestWord ->
                minDistanceMap.put(requestWord,
                        wordsWithDistance.get(requestWord).values()
                                .stream().min(Comparator.naturalOrder())
                                .orElse(-1)));

        Arrays.stream(wordsFromRequest).forEach(requestWord -> {
            result.append(wordsWithDistance
                    .get(requestWord).entrySet().stream()
                    .filter(pair -> pair.getValue()
                            .equals(minDistanceMap.get(requestWord)))
                    .findAny().get().getKey());

            result.append(" ");
        });

        result.deleteCharAt(result.length() - 1);    //delete last separating space

        return result.toString();
    }

    @Override
    public boolean isAlternativeSearchLineNeeded(long numberOfFoundServices, String category) {
        boolean result;
        if (numberOfFoundServices <= MINIMUM_SIZE_OF_FOUND_SERVICES) {
            result = true;
        } else if (category.equals(CATEGORY_TYPE_ALL)) {
            result = numberOfFoundServices <= this.numberOFAllServices / 50;
        } else {
            result = numberOfFoundServices <= this.numberOFAllServices / 50 * NUMBER_OF_SERVICES_CATEGORY;
        }

        return result;
    }

    @Override
    public Map<String, HashMap<String, Integer>> getWordsWithMinimumDistance(SearchRequest searchRequest) throws IOException {
        final String searchLine = searchRequest.getSearchLine();

        // Map with words from request and with pair consisting distance and word from dictionary.
        Map<String, HashMap<String, Integer>> resultMap = new TreeMap<>(Comparator.naturalOrder());

        Distance distance = (dictString, userString) ->
                this.metricService.getPrefixDistance(dictString, userString, 8);

        if (this.dictionary.getDictionaryContent() == null
                || this.dictionary.getDictionaryContent().size() != getDictionary().getDictionaryContent().size()) {
            this.dictionary = getDictionary();

            if (this.dictionary.getDictionaryContent() == null) {
                initializeDictionary(dictionary);
                saveDictionary(dictionary);
                this.dictionary = getDictionary();
            }
        }

        String[] wordsFromSearchQuery = searchLine.split(REGEX_FOR_SPLIT);
        Arrays.stream(wordsFromSearchQuery)
                .forEach(requestWord -> {
                    HashMap<String, Integer> wordsWithDistance = new HashMap<>();
                    this.dictionary.getDictionaryContent().forEach(dictWord ->
                            wordsWithDistance.put(
                                    dictWord,
                                    distance.getDistance(dictWord, requestWord)));

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
        final String[] arr = sourceString
                .replaceAll(REGEX_FOR_REPLACE, "")
                .split(REGEX_FOR_SPLIT);
        Arrays.stream(arr).forEach(word -> newSearchLine
                .append(oppositeWord.getWordFromOppositeKeyboardLayout(word))
                .append(" "));

        newSearchLine.deleteCharAt(newSearchLine.length() - 1);  //delete last space

        return newSearchLine.toString();
    }

    @Override
    public void initializeDictionary(Dictionary dictionary) {
        TreeSet<String> resultDictionaryContent = new TreeSet<>();

        final Set<Service> allServiceSet = this.serviceForService.findAll();
        allServiceSet.forEach(service -> {
            String[] texts = new String[]{service.getServiceName(), service.getDescription()};
            Arrays.stream(texts).forEach(text ->
                    resultDictionaryContent.addAll(Arrays.stream(
                            text.toLowerCase().split(REGEX_FOR_SPLIT))
                            .filter(this::isStringSuitableForDictionary)
                            .collect(Collectors.toSet())));
        });

        dictionary.setDictionaryContent(resultDictionaryContent);
    }

    @Override
    public void saveDictionary(Dictionary dictionary) throws IOException {
        ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream(DICTIONARY_FILE_NAME));
        objectOutput.writeObject(dictionary);
        objectOutput.flush();
        objectOutput.close();
    }

    @Override
    public Dictionary getDictionary() {
        Dictionary resultDictionary = null;
        try (ObjectInput objectInput = new ObjectInputStream(new FileInputStream(DICTIONARY_FILE_NAME))) {
            resultDictionary = (Dictionary) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.debug(String.format(ERROR_BY_DESERIALIZATION, e.getMessage()));
            e.printStackTrace();
        }

        return resultDictionary;
    }

    @Override
    public boolean isStringSuitableForDictionary(String testString) {
        Pattern p = Pattern.compile(REGEX_FOR_VERIFYING_WORD_FOR_THE_DICTIONARY);
        Matcher m = p.matcher(testString);

        return m.matches();
    }
}
