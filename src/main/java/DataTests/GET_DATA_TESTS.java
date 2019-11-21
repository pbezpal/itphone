package DataTests;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public interface GET_DATA_TESTS {

    Properties prop = new Properties();
    InputStream input = GET_DATA_TESTS.class.getClassLoader().getResourceAsStream("config/tests.properties");

    static HashMap<String, String> getLoadConfig()
    {
        try {
            prop.load(input);
        } catch (IOException e) {
            return null;
        }
        return new HashMap(prop);
    }

    static String getProperty(String prop){
        return getLoadConfig().get(prop);
    }

    static boolean setProperty(String property, String value) {
        try{
            prop.load(input);
        }catch (IOException e){
            return false;
        }
        prop.setProperty(property, value);
        return true;
    }
}
