package com.playwrigth.application.hipertextual.assertions;

import com.microsoft.playwright.Page;
import com.playwrigth.application.hipertextual.pages.NewsLetterPage;
import com.playwrigth.application.hipertextual.pages.ResultsPage;

public class HipertextualAssertions {

    Page page;
    public ResultPageAssertions resultPageAssertions;
    public NewsLetterAssertions newsLetterAssertions;

    public HipertextualAssertions(Page page) {
        this.page = page;
        resultPageAssertions = new ResultPageAssertions(new ResultsPage(page));
        newsLetterAssertions = new NewsLetterAssertions(new NewsLetterPage(page));
    }
}
