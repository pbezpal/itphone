package RecourcesTests;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.close;

public class TestRules implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            try {
                makeScreenshotOnFailure();
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();
            WebDriverRunner.getWebDriver().close();
        }

        @Attachment(type = "image/png")
        public byte[] makeScreenshotOnFailure() throws IOException {
            File screenshot = Screenshots.getLastScreenshot();
            return screenshot == null ? null : Files.toByteArray(screenshot);
            //return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
        }
}
