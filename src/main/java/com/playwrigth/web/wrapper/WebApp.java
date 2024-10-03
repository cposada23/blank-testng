package com.playwrigth.web.wrapper;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;
import com.playwrigth.constants.FilesNames;
import com.playwrigth.utilities.PropertyFileReader;
import com.playwrigth.web.factory.BrowserFactory;
import com.playwrigth.web.factory.browsers.BrowserEnum;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public class WebApp {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public Page getNewPage() {
        return context.newPage();
    }

    public BrowserContext getNewContext() {
        return browser.newContext();
    }

    public WebApp() {
        String browserType = PropertyFileReader.readFromPropertyFile(FilesNames.GENERAL_PROPERTIES, "browser");
        boolean headless = PropertyFileReader.readFromPropertyFile(FilesNames.GENERAL_PROPERTIES, "headless").equals("true");

        this.playwright = Playwright.create();
        BrowserFactory factory = new BrowserFactory(this.playwright);
        this.browser = factory.createBrowser(BrowserEnum.browser(browserType), headless);
        this.context = browser.newContext(
            new Browser.NewContextOptions().setViewportSize(
                headless ?  new ViewportSize(1921, 1080) : null
            )
        );

        this.page = context.newPage();
    }

    private void startTrace() {
        this.context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
    }

    public void takeScreenshot(Path sceenshotPath) {
        this.takeScreenshot(sceenshotPath, false);
    }

    public void takeScreenshot(Path sceenshotPath, boolean fullPage) {
        this.page.screenshot(
                new Page.ScreenshotOptions()
                        .setPath(sceenshotPath)
                        .setFullPage(fullPage)
        );
    }

    public void close() {
        // this.context.tracing().stop();
        this.context.close();
        this.playwright.close();
    }

}
