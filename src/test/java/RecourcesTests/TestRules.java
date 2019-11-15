package RecourcesTests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Selenide.close;

public class TestRules implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            makeScreenshotOnFailure();
            close();
            WebDriverRunner.getWebDriver().close();
        }

        @Attachment(type = "image/png")
        public byte[] makeScreenshotOnFailure() {
            return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
        }
}
