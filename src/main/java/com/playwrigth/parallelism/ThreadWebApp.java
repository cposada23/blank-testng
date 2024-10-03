package com.playwrigth.parallelism;

import com.playwrigth.web.wrapper.WebApp;

public class ThreadWebApp  {

    private static ThreadWebApp instance;
    private ThreadLocal<WebApp> webApp = new ThreadLocal<>();

    private ThreadWebApp () {}

    public void  initApp() {
        webApp.set(new WebApp());
    }

    public static ThreadWebApp getInstance() {
        if (instance == null) instance = new ThreadWebApp();
        return instance;
    }

    public WebApp getWebApp() {
        return webApp.get();
    }

    public void closeApp() {
        webApp.get().close();
        webApp.remove();
    }
}
