package com.slit.nba.beans;

import java.util.List;
import java.util.Map;

public class ListOfLikelihoodTables {
    Map<String, LikelihoodTable> listOfLikelihoodTables;

    public ListOfLikelihoodTables() {
    }

    public ListOfLikelihoodTables(Map<String, LikelihoodTable> listOfLikelihoodTables) {
        this.listOfLikelihoodTables = listOfLikelihoodTables;
    }

    public Map<String, LikelihoodTable> getListOfLikelihoodTables() {
        return listOfLikelihoodTables;
    }

    public void setListOfLikelihoodTables(Map<String, LikelihoodTable> listOfLikelihoodTables) {
        this.listOfLikelihoodTables = listOfLikelihoodTables;
    }

    @Override
    public String toString() {
        return "ListOfLikelihoodTables{" +
                "listOfLikelihoodTables=" + listOfLikelihoodTables +
                '}';
    }
}
