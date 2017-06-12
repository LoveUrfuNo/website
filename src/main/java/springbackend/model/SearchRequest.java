/*
 * Copyright (C) 2017 The Open Source Project
 */
package springbackend.model;

/**
 * Simple JavaBean object that represent user's request for search by services.
 */
public class SearchRequest {
    private String searchLine;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchLine() {
        return searchLine;
    }

    public void setSearchLine(String searchLine) {
        this.searchLine = searchLine;
    }
}
