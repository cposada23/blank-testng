package com.playwrigth.logger;

import com.aventstack.extentreports.Status;
import com.playwrigth.reporting.extentReport.Extent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;
import java.util.Arrays;

/**
 * This class will help to keep the logs for each test separate in a different file
 * Uses Thread safe approach
 */
public class Log {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static synchronized void startLogger(String sTestCaseName) {
        sTestCaseName = sTestCaseName.replaceAll("[^a-zA-Z0-9]", "_").replaceAll("_+", "_");

        // Report folder only generates when we run the test suite
        // If we just run one test there is no folder setup for the report

        String directory = Extent.getInstance().getFolderpath() != null ?
                Extent.getInstance().getFolderpath().toString() :
                System.getProperty("user.dir");

        startLog(
                directory,
                sTestCaseName
        );
        info("\n\n************** Execution Started : " + sTestCaseName + "**************\n");
    }

    public static void endLogger(String sTestCaseName) {
        info("\n\n************** Execution End : " + sTestCaseName + "**************\n");
    }

    private static void startLog(String dirPath, String testCaseName) {

        int noOfFiles = 0;

        File dir = new File(dirPath);
        if (dir.exists()) {
            int count = 0;
            for (File file : dir.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".log") && file.getName().contains(testCaseName)) {
                    count++;
                }
            }
            noOfFiles = count;
        }

        noOfFiles++;
        String logFileName = testCaseName + "_" + noOfFiles;

        ThreadContext.put("logFilename", logFileName);
    }

    public static org.apache.logging.log4j.Logger getCurrentLog() {
        return logger;
    }

    public static String getCallInfo() {

        String callInfo;
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

        callInfo = String.format("%s:%s %d == ", className, methodName, lineNumber);
        return callInfo;

    }

    public static void error(String message) {
        logToExtentReport(Status.FAIL, message);
        message = String.format("%s %s", getCallInfo(), message);
        getCurrentLog().error(message);
    }

    public static void error(String message, Object... params) {
        logToExtentReport(Status.FAIL, message, params);
        message = String.format("%s %s", getCallInfo(), message);
        getCurrentLog().error(message, params);
    }

    public static void info(String message) {
        logToExtentReport(Status.INFO, message);
        message = String.format("%s %s", getCallInfo(), message);
        getCurrentLog().info(message);
    }
    public static void info(String message, Object... params) {
        logToExtentReport(Status.INFO, message, params);
        message = String.format("%s %s", getCallInfo(), message);
        getCurrentLog().info( message, params);
    }


    public static void warn(String message) {
        logToExtentReport(Status.WARNING, message);
        message = String.format("%s %s", getCallInfo(), message);
        getCurrentLog().warn(message);
    }

    public static void warn(String message, Object... params) {
        logToExtentReport(Status.WARNING, message, params);
        message = String.format("%s %s", getCallInfo(), message);
        getCurrentLog().warn(message, params);
    }

    private static void logToExtentReport(Status status, String message) {
        if(Extent.getInstance().getFolderpath() != null) {
            Extent.getInstance().logInfo( message );
        }
    }

    private static void logToExtentReport(Status status, String message, Object... params) {
        if(Extent.getInstance().getFolderpath() != null) {
            message = message.replaceAll("\\{}", "%s");
            params = Arrays.stream(params).map(String::valueOf).toArray();
            message = String.format(message, params);
            Extent.getInstance().logInfo( message );
        }
    }
}
