package RecourcesTests;

import HelperClasses.ScreenshotTests;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;


public class TestRules implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            ScreenshotTests.screenshot();
        }
}
