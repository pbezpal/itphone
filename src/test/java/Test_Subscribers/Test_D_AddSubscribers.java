package Test_Subscribers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureSubscribersTests;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.SubscribersPage;
import RecourcesTests.BeforeAllTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.SUBSCRIBERS.*;
import static Pages.SubscribersPage.subscribersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureSubscribersTests
@ExtendWith({TestRules.class, BeforeAllTests.class})
public class Test_D_AddSubscribers {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;

    @BeforeEach
    void setUp(){
        if (!SubscribersPage.isSubscribersPage().isDisplayed())
            subscribersPage = (SubscribersPage) MonitoringPage.openSectionWEB(SUBSCRIBERS_ITEM_MENU);
        if (subscribersPage == null) subscribersPage = SubscribersPage.getInstance();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
    }

    @Story(value = "Добавляем абонента 5000")
    @Description(value = "Добавляем пользьзователя 5000 и проверяем, что пользователь 5000 был успешно добавился")
    @Test
    void test_Subscriber_5000(){
        subscribersPage.addSubscriber(SUBSCRIBER_5000, SUBSCRIBER_PORT_DX_5000, false);
        assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_5000), "Пользователь " + SUBSCRIBER_5000 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_5000)) failedTestWithScreenshot("Пользователь " + SUBSCRIBER_5000 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента 5001")
    @Description(value = "Добавляем пользьзователя 5001 и проверяем, что пользователь 5001 был успешно добавился")
    @Test
    void test_Subscriber_5001(){
        subscribersPage.addSubscriber(SUBSCRIBER_5001, SUBSCRIBER_PORT_DX_5001, false);
        assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_5001), "Пользователь " + SUBSCRIBER_5001 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_5001)) failedTestWithScreenshot("Пользователь " + SUBSCRIBER_5001 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента 4000")
    @Description(value = "Добавляем пользьзователя 4000 и проверяем, что пользователь 4000 был успешно добавился")
    @Test
    void test_Subscriber_4000(){
        subscribersPage.addSubscriber(SUBSCRIBER_4000, SUBSCRIBER_PORT_DX_4000, true);
        assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_4000), "Пользователь " + SUBSCRIBER_4000 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_4000)) failedTestWithScreenshot("Пользователь " + SUBSCRIBER_4000 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    @Story(value = "Добавляем абонента 4001")
    @Description(value = "Добавляем пользьзователя 4001 и проверяем, что пользователь 4001 был успешно добавился")
    @Test
    void test_Subscriber_4001(){
        subscribersPage.addSubscriber(SUBSCRIBER_4001, SUBSCRIBER_PORT_DX_4001, true);
        assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_4001), "Пользователь " + SUBSCRIBER_4001 + " не найден");
        if( ! SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_4001)) failedTestWithScreenshot("Пользователь " + SUBSCRIBER_4001 + " не найден на сервере", false);
        if( ! SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS)) failedTestWithScreenshot("Сервер контактов не запущен", false);
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "; " + message;
        if(screenshot) ScreenshotTests.screenshot();
    }

    @AfterEach
    void tearDown(){
        assertTrue(TEST_STATUS, TEST_MESSAGE);
    }

}
