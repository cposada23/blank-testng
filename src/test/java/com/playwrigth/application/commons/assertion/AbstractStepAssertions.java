package com.playwrigth.application.commons.assertion;

import org.testng.asserts.SoftAssert;

import java.util.Objects;

public class AbstractStepAssertions<T> {
    protected SoftAssert softAssert;

    public T initSoftAssert() {
        softAssert = new SoftAssert();
        return (T) this;
    }

    protected void validate() {
        Objects.requireNonNull(softAssert, "must call initSoftAssert() method first to create the softAssertions");
    }

    public void assertAll() {
        softAssert.assertAll();
    }
}
