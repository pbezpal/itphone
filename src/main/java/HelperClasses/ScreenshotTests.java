package HelperClasses;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class ScreenshotTests {

    //Удаляем все вскриншоты
    public static void deleteAllScreenshots(){
        for(File screenshot : new File(".\\build\\reports\\tests").listFiles()){
            if(screenshot.isFile()) screenshot.delete();
        }
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] screenshot() {
        File screenshot = Screenshots.getLastScreenshot();
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            return null;
        }
        //return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
