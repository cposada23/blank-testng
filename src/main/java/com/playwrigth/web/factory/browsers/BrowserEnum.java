package com.playwrigth.web.factory.browsers;

import lombok.Getter;

@Getter
public enum BrowserEnum {

    CHROME("chrome"),
    FIREFOX("firefox");

    String browserName;

    BrowserEnum(String name) {
        this.browserName = name;
    }

    public static BrowserEnum browser(String name) {

        for (BrowserEnum browser: values()) {
            if(browser.browserName.equals(name)) return browser;
        }
        throw new RuntimeException(String.format("No browser with name: %s", name));
    }
}
