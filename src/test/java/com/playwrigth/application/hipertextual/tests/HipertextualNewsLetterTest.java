package com.playwrigth.application.hipertextual.tests;

import com.playwrigth.application.hipertextual.HipertextualApp;
import com.playwrigth.listeners.commons.LoggerListener;
import com.playwrigth.parallelism.ThreadWebApp;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HipertextualNewsLetterTest extends LoggerListener {

    ThreadWebApp webApp = ThreadWebApp.getInstance();
    HipertextualApp hipertextualApp;

    String searchTerm = "Steve Jobs";


    @BeforeClass(alwaysRun = true)
    protected void setupApp() {
        webApp.initApp();
        hipertextualApp = new HipertextualApp(webApp.getWebApp().getPage());
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        webApp.closeApp();
    }


    @Test(
            testName = "Verify User can navigate to app URL"
    )
    public void userCanNavigateToAppURL() {
        hipertextualApp.navigateTo("https://hipertextual.com");
    }

    @Test(
            dependsOnMethods = {"userCanNavigateToAppURL"},
            testName = "Validate user can search for given search term"
    )
    public void validateUserCanSearchForSearchTerm() {
        hipertextualApp.homePage.searchFor(searchTerm);
        hipertextualApp.resultsPage.waitForResults();
    }

    @Test(
            dependsOnMethods = {"validateUserCanSearchForSearchTerm"},
            testName = "Validate user can scroll down to the first post about Reed Jobs"
    )
    public void scrollDownToFirstPostAboutReedJobs() {
        hipertextualApp.resultsPage.scrollTo("Reed Jobs");
    }

    @Test(
            dependsOnMethods = {"scrollDownToFirstPostAboutReedJobs"},
            testName = "validate URL contains the search term"
    )
    public void validateURLContainsTheSearchTerm() {
        hipertextualApp.assertions.resultPageAssertions.initSoftAssert()
                .validateURLContainsSearchTerm(searchTerm)
                .assertAll();
    }

    @Test(
            dependsOnMethods = {"validateURLContainsTheSearchTerm"},
            testName = "validate user cna click on post that contains Reed Jobs"
    )
    public void validateUserCanClickOnPOstThatContainsReedJobs() {
        hipertextualApp.resultsPage.clickFirstPostThatContainsText("Reed Jobs");
    }


    @Test(
            dependsOnMethods = {"validateUserCanClickOnPOstThatContainsReedJobs"},
            testName = "validate user can subscribe to news letter"
    )
    public void validateUserCanSubscribeToNewsLetter() {
        hipertextualApp.newsLetterPage.waitForAndScrollToNewsLetter();
        hipertextualApp.newsLetterPage.enterEmail("test@test.com");
        hipertextualApp.newsLetterPage.clickButtonSubscribe();

        hipertextualApp.assertions.newsLetterAssertions
                .initSoftAssert()
                .verifySubscribeToNewsLetterWasSuccessful()
                .assertAll();
    }

}
