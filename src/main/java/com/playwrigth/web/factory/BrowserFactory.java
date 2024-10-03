package com.playwrigth.web.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.playwrigth.web.factory.browsers.BrowserEnum;

import java.util.List;

public class BrowserFactory {

    Playwright playwright;
    public BrowserFactory(Playwright playwright) {
        this.playwright = playwright;
    }

    public Browser createBrowser(BrowserEnum browser, boolean headless) {
        switch (browser) {
            case FIREFOX:
                return playwright.firefox().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(headless).setSlowMo(50)
                );
            case CHROME:
            default:
                return playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                        .setHeadless(headless).setSlowMo(50)
                        .setArgs(List.of("--start-maximized"))
                );
        }
    }
}
