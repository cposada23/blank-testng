package com.playwrigth.application.hipertextual.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.playwrigth.constants.Wait;
import com.playwrigth.logger.Log;
import com.playwrigth.web.BasePage;

public class ResultsPage extends BasePage {

    Locator resultsPageInfo;
    public ResultsPage(Page page) {
        super(page);
        resultsPageInfo = page.locator("//*[@class='page-title' and contains(text(), 'Resultados de la búsqueda')]");
    }


    public void waitForResults() {
        Log.info("Waiting for results");
        resultsPageInfo.waitFor(new Locator.WaitForOptions().setTimeout(Wait.LONG_WAIT));
    }
    public void scrollTo(String text) {
        Log.info("Scroll to post that have test {}", text);
        Locator thingToScroll = page.locator(String.format("//*[contains(text(), '%s')]", text));
        thingToScroll.first().scrollIntoViewIfNeeded();
    }

    public void clickFirstPostThatContainsText(String text) {
        Log.info("Click first post that contains text {}", text);
        Locator link = page.locator(String.format("//a[contains(text(), '%s')]", text));
        link.first().click();
    }
}
