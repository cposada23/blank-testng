package com.playwrigth.application.hipertextual.assertions;

import com.playwrigth.application.commons.assertion.AbstractStepAssertions;
import com.playwrigth.application.hipertextual.pages.NewsLetterPage;

public class NewsLetterAssertions extends AbstractStepAssertions<NewsLetterAssertions> {

    NewsLetterPage newsLetterPage;

    public NewsLetterAssertions(NewsLetterPage newsLetterPage) {
        this.newsLetterPage = newsLetterPage;
    }
    public NewsLetterAssertions verifySubscribeToNewsLetterWasSuccessful() {
        validate();
        softAssert.assertTrue(
                newsLetterPage.wasSubscribeSuccessful(),
                "Subscribe to news letter failed"
        );
        return this;
    }

}
