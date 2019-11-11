package RecourcesTests;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.stqa.selenium.factory.WebDriverPool;

import static com.codeborne.selenide.Selenide.close;

public class AfterAllTests implements AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext context){
        close();
        WebDriverPool.DEFAULT.dismissAll();
    }
}
