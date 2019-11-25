package RecourcesTests;

import HelperClasses.ScreenshotTests;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import ru.stqa.selenium.factory.WebDriverPool;

import static Pages.MonitoringPage.getArticleModule;
import static com.codeborne.selenide.Selenide.close;

public class TestRules implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            if (cause.getMessage().equals("article module")){
                ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()), getArticleModule());
            }else if(cause.getMessage().equals("DOWNLOAD")){
                ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
                close();
                WebDriverPool.DEFAULT.dismissAll();
            }else if( ! cause.getMessage().equals("false")) ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
        }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (cause.getMessage().equals("article module")){
            ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()), getArticleModule());
        }else if(cause.getMessage().equals("DOWNLOAD")){
            ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
        }else if( ! cause.getMessage().equals("false")) ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
    }
}
