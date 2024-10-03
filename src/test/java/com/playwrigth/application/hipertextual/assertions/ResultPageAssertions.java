package com.playwrigth.application.hipertextual.assertions;

import com.playwrigth.application.commons.assertion.AbstractStepAssertions;
import com.playwrigth.application.hipertextual.pages.ResultsPage;

public class ResultPageAssertions extends AbstractStepAssertions<ResultPageAssertions> {

    ResultsPage resultsPage;
    public ResultPageAssertions(ResultsPage resultsPage) {
        this.resultsPage = resultsPage;
    }
    public ResultPageAssertions validateURLContainsSearchTerm(String searchTerm) {
        validate();
        String url = resultsPage.getCurrentURL();
        softAssert.assertTrue(url.contains(searchTerm.replaceAll(" ", "+")), "The url does not contains the search term : " + searchTerm);
        return this;
    }

}
