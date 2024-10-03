package com.playwrigth.listeners.commons;

import com.playwrigth.logger.Log;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class LoggerListener {
    @BeforeMethod(alwaysRun = true)
    public void setupLogger(Method method) {
        Log.startLogger(method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void closeLogger(Method method) {
        Log.endLogger(method.getName());
    }
}
