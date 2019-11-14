package HelperClasses;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;

import java.io.File;
import java.io.IOException;

public class ScreenshotTests {

    @Attachment(type = "image/png")
    public static byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

}
