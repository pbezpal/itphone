package RecourcesTests;

import HelperClasses.ScreenshotTests;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import static Pages.MonitoringPage.getArticleModule;

public class TestRules implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            if(cause.getMessage().equals("false"))
            if(cause.getMessage().equals("article module")){
                ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()), getArticleModule());
            }else ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
        }
}
