package RecourcesTests;

import HelperClasses.ScreenshotTests;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import static Pages.MonitoringPage.getArticleModule;
import static com.codeborne.selenide.Selenide.refresh;

public class TestRules implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause){
        if (cause.getCause().toString().contains("article module")) {
            ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()), getArticleModule());
            refresh();
        }else if ( ! cause.getCause().toString().contains("false")) {
        }else if(cause == null){
            ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
            refresh();
        }

    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (cause.getCause().toString().contains("article module")) {
            ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()), getArticleModule());
            refresh();
        }else if (cause.getCause().toString().contains("false")) {

        }else if(cause == null){
            ScreenshotTests.AScreenshot(String.valueOf(context.getTestMethod()));
            refresh();
        }
    }
}
