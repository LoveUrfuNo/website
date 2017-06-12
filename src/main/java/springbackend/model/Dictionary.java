/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.model;

import java.io.Serializable;
import java.util.TreeSet;

/**
 * Simple JavaBean object that represent a dictionary for fuzzy search
 */
public class Dictionary implements Serializable{
    private TreeSet<String> dictionaryContent;

    public TreeSet<String> getDictionaryContent() {
        return dictionaryContent;
    }

    public void setDictionaryContent(TreeSet<String> dictionaryContent) {
        this.dictionaryContent = dictionaryContent;
    }
}
