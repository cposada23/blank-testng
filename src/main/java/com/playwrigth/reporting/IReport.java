package com.playwrigth.reporting;

import com.playwrigth.web.factory.browsers.BrowserEnum;

import java.nio.file.Path;

public interface IReport {

    public void startReport(Path reportFolderPath, String reportName, BrowserEnum browserEnum);
    public void createTest(String testName, String category);
    public void generate();
    public void testFailure(String cause);
    public void testFailure(String cause, Throwable throwable);
    public void testSkipped(String cause);
    public void testSkipped(String cause, Throwable throwable);
    public void testSuccess(String message);
    public Path getFolderpath();
    public String getReportName();

    public void addScreenShot(Path screenshotPath, String name);

    public void logInfo(String message);

}
