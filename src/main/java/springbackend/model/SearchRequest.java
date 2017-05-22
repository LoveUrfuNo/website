package springbackend.model;

/**
 * Simple JavaBean object that represent user's request for search by services.
 */
public class SearchRequest {
    private String searchLine;

    public String getSearchLine() {
        return searchLine;
    }

    public void setSearchLine(String searchLine) {
        this.searchLine = searchLine;
    }
}
