package HelperClasses;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;

import java.io.File;
import java.io.IOException;

public class ScreenshotTests {

    /*@Rule
    public TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            makeScreenshotOnFailure();
        }

        @Override
        protected void finished(Description description) {
            WebDriverRunner.getWebDriver().close();
        }

        @Attachment(“Screenshot on failure”)
        public byte[] makeScreenshotOnFailure() {
            return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
        }

    };*/

    @Attachment(type = "image/png")
    public static byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getLastScreenshot();
        return screenshot == null ? null : Files.toByteArray(screenshot);
    }

}
