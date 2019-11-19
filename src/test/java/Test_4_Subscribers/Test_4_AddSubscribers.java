package Test_4_Subscribers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureSubscribersTests;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataSubscribers.*;
import static Pages.SubscribersPage.subscribersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureSubscribersTests
@ExtendWith({TestRules.class, BeforeEachTests.class})
public class Test_4_AddSubscribers {

    @Story(value = "Добавляем абонента " + subscriber5000)
    @Description(value = "Добавляем пользьзователя " + subscriber5000 + " и проверяем, что пользователь " + subscriber5000 + " был успешно добавился")
    @Test
    void test_Subscriber_5000(){
        subscribersPage.addSubscriber(subscriber5000, portDX5000, false);
        assertTrue(subscribersPage.isFilterSubscriber(subscriber5000), "Пользователь " + subscriber5000 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + subscriber5000)) failedTestWithScreenshot("Пользователь " + subscriber5000 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(commandStatusSIPServer)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента " + subscriber5001)
    @Description(value = "Добавляем пользьзователя " + subscriber5001 + " и проверяем, что пользователь " + subscriber5001 + " был успешно добавился")
    @Test
    void test_Subscriber_5001(){
        subscribersPage.addSubscriber(subscriber5001, portDX5001, false);
        assertTrue(subscribersPage.isFilterSubscriber(subscriber5001), "Пользователь " + subscriber5001 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + subscriber5001)) failedTestWithScreenshot("Пользователь " + subscriber5001 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(commandStatusSIPServer)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента " + subscriber4000)
    @Description(value = "Добавляем пользьзователя " + subscriber4000 + " и проверяем, что пользователь " + subscriber4000 + " был успешно добавился")
    @Test
    void test_Subscriber_4000(){
        subscribersPage.addSubscriber(subscriber4000, portDX4000, true);
        assertTrue(subscribersPage.isFilterSubscriber(subscriber4000), "Пользователь " + subscriber4000 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + subscriber4000)) failedTestWithScreenshot("Пользователь " + subscriber4000 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(commandStatusSIPServer)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента " + subscriber4001)
    @Description(value = "Добавляем пользьзователя " + subscriber4001 + " и проверяем, что пользователь " + subscriber4001 + " был успешно добавился")
    @Test
    void test_Subscriber_4001(){
        subscribersPage.addSubscriber(subscriber4001, portDX4001, true);
        assertTrue(subscribersPage.isFilterSubscriber(subscriber4001), "Пользователь " + subscriber4001 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + subscriber4001)) failedTestWithScreenshot("Пользователь " + subscriber4001 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(commandStatusSIPServer)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        fail(message);
        if(screenshot) ScreenshotTests.screenshot();
    }

}
