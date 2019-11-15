package Test_3_Subscribers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureSubscribersTests;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.SubscribersPage;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataSubscribers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureSubscribersTests
@ExtendWith(TestRules.class)
public class Test_3_AddSubscribers {

    private SubscribersPage subscribersPage = null;
    private boolean test;
    private boolean screenshot;
    private String failedMessage;

    @BeforeEach
    void setUp() {
        BeforeEachTests.beforeStartTests();
        if (!SubscribersPage.isSubscribersPage().isDisplayed())
            subscribersPage = (SubscribersPage) MonitoringPage.openSectionWEB(linkSubscribers);
        if (subscribersPage == null) subscribersPage = SubscribersPage.getInstance();
        test = true;
        screenshot = false;
        failedMessage = "";
    }

    @Story(value = "Добавляем абонента " + subscriber5000)
    @Description(value = "Добавляем пользьзователя " + subscriber5000 + " и проверяем, что пользователь " + subscriber5000 + " был успешно добавился")
    @Test
    void test_Subscriber_5000(){
        subscribersPage.addSubscriber(subscriber5000, portDX, false);
        assertTrue(subscribersPage.isFilterSubscriber(subscriber5000), "Пользователь " + subscriber5000 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + subscriber5000)) failedTestWithScreenshot("Пользователь " + subscriber5000 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(commandStatusSIPServer)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента " + subscriber5001)
    @Description(value = "Добавляем пользьзователя " + subscriber5001 + " и проверяем, что пользователь " + subscriber5001 + " был успешно добавился")
    @Test
    void test_Subscriber_5001(){
        subscribersPage.addSubscriber(subscriber5001, portDX, false);
        assertTrue(subscribersPage.isFilterSubscriber(subscriber5001), "Пользователь " + subscriber5001 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + subscriber5001)) failedTestWithScreenshot("Пользователь " + subscriber5001 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(commandStatusSIPServer)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @AfterEach
    void tearDown(){
        if(!test){
            fail(failedMessage);
            if(screenshot) ScreenshotTests.screenshot();
        }
        ScreenshotTests.deleteAllScreenshots();
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        test = false;
        this.screenshot = screenshot;
        failedMessage = message;
    }

}
