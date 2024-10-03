package com.playwrigth.reporting.extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.playwrigth.reporting.IReport;
import com.playwrigth.web.factory.browsers.BrowserEnum;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Extent implements IReport {

    private Extent() {}

    private static Extent instance = new Extent();

    public static Extent getInstance() {
        return instance;
    }
    ExtentReports extentReport;
    public Path reportFolderPath;
    public String reportName;

    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void startReport(Path reportFolderPath, String reportName, BrowserEnum browserEnum) {
        this.reportName = reportName;
        this.reportFolderPath = reportFolderPath;

        Path reportPath = Paths.get(reportFolderPath.toString(), reportName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath.toString());
        ExtentSparkReporterConfig config = ExtentSparkReporterConfig
                .builder()
                .documentTitle(reportName)
                .theme(Theme.DARK)
                .reportName(reportName)
                .build();
        sparkReporter.config(config);

        extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);

        extentReport.setSystemInfo("Environment:", "QA");
        extentReport.setSystemInfo("Browser: ", browserEnum.getBrowserName());
        extentReport.setSystemInfo("OS: ", System.getProperty("os.name"));
        extentReport.setSystemInfo("Executed by user: ", System.getProperty("user.name"));

    }

    @Override
    public void createTest(String testName, String category) {
        validate();
        ExtentTest test = extentReport.createTest(testName)
                .assignCategory(category);
        extentTest.set(test);
    }

    @Override
    public void generate() {
        validate();
        extentReport.flush();
    }

    @Override
    public void testFailure(String cause) {
        testFailure(cause, null);
    }

    @Override
    public void testFailure(String cause, Throwable throwable) {
        validate();
        ExtentTest test = extentTest.get();
        test.log(Status.FAIL,cause);
        if(throwable != null) {
            test.log(Status.FAIL, throwable);
        }
        remove();
    }

    @Override
    public void testSkipped(String cause) {
        this.testSkipped(cause, null);
    }

    @Override
    public void testSkipped(String cause, Throwable throwable) {
        validate();
        ExtentTest test = extentTest.get();
        test.log(Status.SKIP, cause);
        if(throwable != null) {
            test.log(Status.SKIP, throwable);
        }
        remove();
    }

    @Override
    public void testSuccess(String message) {
        validate();
        ExtentTest test = extentTest.get();
        test.log(Status.PASS, message);
        remove();
    }

    @Override
    public Path getFolderpath() {
        return reportFolderPath;
    }

    @Override
    public String getReportName() {
        return reportName;
    }

    @Override
    public void addScreenShot(Path screenshotPath, String name) {
        ExtentTest test = extentTest.get();
        test.addScreenCaptureFromPath(screenshotPath.toString(), name);
    }

    @Override
    public void logInfo(String message) {
        ExtentTest test = extentTest.get();
        if(test != null) {
            test.log(Status.INFO, message);
        }
    }

    public void validate() {
        Objects.requireNonNull(extentReport, "must call startReport() method first to create the report");
    }

    public void remove() {
        extentTest.remove();
    }
}
