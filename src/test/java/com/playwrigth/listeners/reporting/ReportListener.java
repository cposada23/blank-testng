package com.playwrigth.listeners.reporting;

import com.playwrigth.constants.FilesNames;
import com.playwrigth.parallelism.ThreadWebApp;
import com.playwrigth.reporting.extentReport.Extent;
import com.playwrigth.reporting.IReport;
import com.playwrigth.utilities.PathTo;
import com.playwrigth.utilities.PropertyFileReader;
import com.playwrigth.web.factory.browsers.BrowserEnum;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportListener implements ITestListener, ISuiteListener  {

    public static IReport report = Extent.getInstance();

    public void cleanLogsFolder() {
        File logsFolder = new File(PathTo.logsFolder());
        if(logsFolder.exists()) {
            File [] logFiles = logsFolder.listFiles();
            for(File file : logFiles) {
                if(FilenameUtils.getExtension(file.getName()).equals("log")) {
                    try {
                        FileUtils.delete(file);
                    } catch (IOException e) {
                        // DO Nothing
                    }
                }
            }
        }
    }

    @Override
    public void onStart(ISuite suite) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
        Date date = new Date();
        String actualDate = format.format(date);

        String browser = PropertyFileReader.readFromPropertyFile(FilesNames.GENERAL_PROPERTIES, "browser");
        BrowserEnum browserEnum = BrowserEnum.browser(browser);
        report.startReport(
                Paths.get(PathTo.reportsFolder(), actualDate),
                String.format("%sExecutionReport.html", suite.getName()),
                browserEnum
        );

        cleanLogsFolder();
    }

    @Override
    public void onFinish(ISuite suite) {
        report.generate();
        // Move log files to report folder
        String reportFolderPath = report.getFolderpath().toString();
        File reportFolder = new File(reportFolderPath);
        File logsFolder = new File(PathTo.logsFolder());
        if(reportFolder.exists() && logsFolder.exists()) {
            // MOVE LOG FILES
            File [] logFiles = logsFolder.listFiles();
            for(File file : logFiles) {
                if(FilenameUtils.getExtension(file.getName()).equals("log")) {
                    String destinationPath = Paths.get(
                            reportFolderPath,
                            "logs",
                            file.getName()
                    ).toString();
                    File destinantionFile = new File(destinationPath);
                    try {
                        FileUtils.moveFile(file, destinantionFile);
                    } catch (IOException e) {
                        // Do nothing
                    }
                }
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
        if(testName.isEmpty()) testName = result.getName();
        report.createTest(testName, result.getTestClass().getRealClass().getSimpleName());
    }


    @Override
    public void onTestFailure(ITestResult testResult) {
        this.addScreenshotToReport(testResult.getName());
        report.testFailure(String.format("Test case: %s is failed", testResult.getName()), testResult.getThrowable());
        //add screenshot for failed test.

    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        this.addScreenshotToReport(testResult.getName());
        report.testSuccess(String.format("Test case: %s Passed", testResult.getName()));
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        report.testSkipped(String.format("Test case: %s is Skipped", testResult.getName()), testResult.getThrowable());
    }

    private void addScreenshotToReport(String info) {

        if(ThreadWebApp.getInstance() != null) {
            Path screenshotName = Paths.get(
                    "screenshots",
                    info + ".jpeg"
            );

            Path screenshotPath = Paths.get(
                    report.getFolderpath().toString(), screenshotName.toString()
            );

            ThreadWebApp.getInstance().getWebApp().takeScreenshot(screenshotPath, false);

            report.addScreenShot(screenshotName, info);
        }

    }
}
