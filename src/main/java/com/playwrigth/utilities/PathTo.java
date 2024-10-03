package com.playwrigth.utilities;

import java.nio.file.Paths;

public class PathTo {

    public static String root() {
        return System.getProperty("user.dir");
    }

    public static String reportsFolder() {
        return Paths.get(
                PathTo.root(),
                "Reports"
        ).toString();
    }

    public static String resourcesFolder() {
        return Paths.get(
                PathTo.root(),
                "src",
                "test",
                "resources"
        ).toString();
    }


    public static String logsFolder() {
        return Paths.get(
                PathTo.root(),
                "logs"
        ).toString();
    }
}
