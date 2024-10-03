package com.playwrigth.web;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.playwrigth.logger.Log;

public abstract class BasePage {

    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void hardWait(int millis) {
        Log.info("Hard Wait for {}ms", millis);
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isVisible(Locator locator, int millis, int polling) {
        if(polling <= 0) throw new RuntimeException("polling can not be zero or less than zero");
        boolean visible = false;
        while (millis > 0  && !visible) {
            visible = locator.isVisible();
            millis -= polling;
            if(!visible) hardWait(polling);
        }

        return visible;
    }

    public String getCurrentURL() {
        return page.url();
    }
}
