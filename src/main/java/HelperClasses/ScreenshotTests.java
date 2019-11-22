package HelperClasses;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ScreenshotTests {

    @Attachment(type = "image/png")
    public static byte[] AttachScreen(File screenshot) {
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static AShot aShot = new AShot();
    private static URL input = ScreenshotTests.class.getClassLoader().getResource("target/screenshots");

    public static void AScreenshot(String filename){
        Screenshot screenshot = aShot.takeScreenshot(WebDriverRunner.getWebDriver());
        if(input == null) new File(String.valueOf(input)).mkdirs();
        File currentScreenshot = new File(input + filename + ".png");

        try {
            ImageIO.write(screenshot.getImage(), "png", currentScreenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttachScreen(currentScreenshot);
    }

    public static void AScreenshot(String filename, SelenideElement element){
        Screenshot screenshot = aShot.takeScreenshot(WebDriverRunner.getWebDriver(), element);
        if(input == null) new File(String.valueOf(input)).mkdirs();
        File currentScreenshot = new File(String.valueOf(input) + filename + ".png");

        try {
            ImageIO.write(screenshot.getImage(), "png", currentScreenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttachScreen(currentScreenshot);
    }

}
