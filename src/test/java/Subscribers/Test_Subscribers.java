package Subscribers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureSubscribersTests;
import HelperClasses.SSHManager;
import Pages.MonitoringPage;
import Pages.SubscribersPage;
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
import static Pages.MonitoringPage.isCheckNotVisibleDownload;
import static Pages.SubscribersPage.subscribersPage;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureSubscribersTests
@ExtendWith(TestRules.class)
public class Test_Subscribers {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private static boolean screenshot;

    @BeforeEach
    void setUp(){
        StartTests();
        if (!SubscribersPage.isSubscribersPage().isDisplayed())
            subscribersPage = (SubscribersPage) MonitoringPage.openSectionWEB(SUBSCRIBERS_ITEM_MENU);
        if (subscribersPage == null) subscribersPage = SubscribersPage.getInstance();
        if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        TEST_STATUS = true;
        TEST_MESSAGE = "";
        screenshot = false;
    }

    @Story(value = "Добавляем абонента 5000")
    @Description(value = "Добавляем пользьзователя 5000 и проверяем, что пользователь 5000 был успешно добавился")
    @Test
    void test_Subscriber_5000(){
        assertTimeout(ofSeconds(600), () -> {
            subscribersPage.addSubscriber(SUBSCRIBER_5000, SUBSCRIBER_PORT_DX_5000, false);
            assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_5000), "Пользователь " + SUBSCRIBER_5000 + " не найден");
            if (!SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_5000))
                failedTestWithScreenshot("Пользователь " + SUBSCRIBER_5000 + " не найден на сервере", false);
            if (!SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS))
                failedTestWithScreenshot("Сервер контактов не запущен", false);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Добавляем абонента 5001")
    @Description(value = "Добавляем пользьзователя 5001 и проверяем, что пользователь 5001 был успешно добавился")
    @Test
    void test_Subscriber_5001(){
        assertTimeout(ofSeconds(600), () -> {
            subscribersPage.addSubscriber(SUBSCRIBER_5001, SUBSCRIBER_PORT_DX_5001, false);
            assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_5001), "Пользователь " + SUBSCRIBER_5001 + " не найден");
            if (!SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_5001))
                failedTestWithScreenshot("Пользователь " + SUBSCRIBER_5001 + " не найден на сервере", false);
            if (!SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS))
                failedTestWithScreenshot("Сервер контактов не запущен", false);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Добавляем абонента 4000")
    @Description(value = "Добавляем пользьзователя 4000 и проверяем, что пользователь 4000 был успешно добавился")
    @Test
    void test_Subscriber_4000(){
        assertTimeout(ofSeconds(600), () -> {
            subscribersPage.addSubscriber(SUBSCRIBER_4000, SUBSCRIBER_PORT_DX_4000, true);
            assertTrue(subscribersPage.isFilterSubscriber(SUBSCRIBER_4000), "Пользователь " + SUBSCRIBER_4000 + " не найден");
            if (!SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_4000))
                failedTestWithScreenshot("Пользователь " + SUBSCRIBER_4000 + " не найден на сервере", false);
            if (!SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS))
                failedTestWithScreenshot("Сервер контактов не запущен", false);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Добавляем абонента 4001")
    @Description(value = "Добавляем пользьзователя 4001 и проверяем, что пользователь 4001 был успешно добавился")
    @Test
    void test_Subscriber_4001(){
        assertTimeout(ofSeconds(600), () -> {
            subscribersPage.addSubscriber(SUBSCRIBER_4001, SUBSCRIBER_PORT_DX_4001, true);
            if (!subscribersPage.isFilterSubscriber(SUBSCRIBER_4001))
                failedTestWithScreenshot("Пользователь " + SUBSCRIBER_4001 + " не найден", true);
            if (!SSHManager.isCheckQuerySSH("/var/db/sv-contacts/userlist.sh + grep " + SUBSCRIBER_4001))
                failedTestWithScreenshot("Пользователь " + SUBSCRIBER_4001 + " не найден на сервере", false);
            if (!SSHManager.isCheckQuerySSH(SV_CONTACTS_STATUS))
                failedTestWithScreenshot("Сервер контактов не запущен", false);
        }, () -> "Время теста больше 5 минут");
    }

    void failedTestWithScreenshot(String message, boolean screen) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "; " + message;
        this.screenshot = screen;
    }

    @AfterEach
    void tearDown(){
        if( ! TEST_STATUS){
            fail(TEST_MESSAGE, new Exception(String.valueOf(screenshot)));
        }
    }

}
