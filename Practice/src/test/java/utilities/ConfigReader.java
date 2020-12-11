package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            //we will be reading from a file
            String path = "Practice/src/test/resources/configuration.properties";

            FileInputStream input = new FileInputStream(path);

            //we will load our configurations into Properties object
            properties = new Properties();
            properties.load(input);
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //single method that we will use to read a value from a properties object
    public static String getProperty(String key){
        return properties.getProperty(key).trim();
    }

}
