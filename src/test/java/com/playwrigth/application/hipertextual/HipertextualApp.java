package com.playwrigth.application.hipertextual;

import com.microsoft.playwright.Page;
import com.playwrigth.application.hipertextual.assertions.HipertextualAssertions;
import com.playwrigth.application.hipertextual.pages.HomePage;
import com.playwrigth.application.hipertextual.pages.NewsLetterPage;
import com.playwrigth.application.hipertextual.pages.ResultsPage;
import com.playwrigth.logger.Log;

public class HipertextualApp {

    private Page page;
    public HomePage homePage;
    public ResultsPage resultsPage;
    public NewsLetterPage newsLetterPage;

    public HipertextualAssertions assertions;

    public HipertextualApp(Page page) {
        this.page = page;
        assertions = new HipertextualAssertions(page);
        this.homePage = new HomePage(page);
        this.resultsPage = new ResultsPage(page);
        this.newsLetterPage = new NewsLetterPage(page);
    }

    public void navigateTo(String url) {
        Log.info("Navigate to URL {}", url);
        page.navigate(url);
        homePage.hardWait(5000);
    }

}
