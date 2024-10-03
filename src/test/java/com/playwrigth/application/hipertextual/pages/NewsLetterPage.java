package com.playwrigth.application.hipertextual.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.playwrigth.constants.Wait;
import com.playwrigth.logger.Log;
import com.playwrigth.web.BasePage;

public class NewsLetterPage extends BasePage {

    Locator inputEmail;
    Locator newsLetter;
    Locator buttonSubscribe;
    Locator subscribeSuccess;
    public NewsLetterPage(Page page) {
        super(page);
        inputEmail = page.locator("[id='custom-substack-embed'] form input");
        newsLetter = page.locator("//a[text() = 'newsletter']");
        buttonSubscribe = page.locator("//button[text() = 'Suscríbete']");
        subscribeSuccess = page.locator("//p[@class='success']");
    }

    public void waitForAndScrollToNewsLetter() {
        newsLetter.waitFor(new Locator.WaitForOptions().setTimeout(Wait.LONG_WAIT));
        newsLetter.scrollIntoViewIfNeeded();
        PlaywrightAssertions.assertThat(newsLetter).isVisible();
    }
    public void enterEmail(String email) {
        Log.info("Enter email {}", email);
        inputEmail.fill(email);
    }

    public void clickButtonSubscribe() {
        Log.info("Click button subscribe");
        buttonSubscribe.click();
    }

    public boolean wasSubscribeSuccessful() {
        Log.info("Verifying subscribe to news letter was successful");
        return isVisible(subscribeSuccess, Wait.LONG_WAIT, 500);
    }
    
}
