package com.playwrigth.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Properties;

public class PropertyFileReader {

    public static String readFromPropertyFile(String fileName, String key) {
        Properties prop = new Properties();

        String pathToPropertiesFile = Paths.get(
                PathTo.resourcesFolder(),
                fileName
        ).toString();

        try(InputStream input = new FileInputStream(pathToPropertiesFile)) {
            prop.load(input);
            if(prop.containsKey(key)) {
                return prop.getProperty(key);
            } else {
                throw new NoSuchElementException("There is no key ["  + key + "] in property file");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
