package Pages;

import HelperClasses.SSHManager;
import Pages.Providers.ProvidersPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.OPENSIPS.*;
import static DataTests.SUBSCRIBERS.*;
import static DataTests.Providers.PROVIDER_DX500.DX500_BOOSTER_ADAPTER_NAME;
import static DataTests.Providers.PROVIDERS.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MonitoringPage extends LoginPage{

    public static MonitoringPage monitoringPage = new MonitoringPage();
    public static MonitoringPage getInstance() { return monitoringPage; }

    @Step(value = "Проверяем, авторизованы ли мы на сервере")
    public static boolean isCheckLogin(){
        try{
            $("h2.section_title").shouldHave(Condition.text("Система управления специализированным сервером"));
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, что находимся в раздеде 'Мониторинг'")
    public static boolean isSectionMonitoring(){
        if($("div#main_server").isDisplayed() && $("article#ext_info").isDisplayed()) return true;
        return false;
    }

    @Step(value = "Проверяем, что текущий пользователь {user}")
    public static boolean isCheckUser(String user){
        try{
            $("section#secondary_bar").find("p").shouldHave(Condition.text(user), Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Переходим в раздел 'Мониторинг'")
    public static void clickButtonMonitoringPage(){
        $("a.link_menu.link_top_menu").shouldHave(Condition.text("Мониторинг")).click();
    }

    @Step(value = "Ждём, когда пропадёт блок загрузки")
    public static boolean isCheckNotVisibleDownload(){
        try{
            $("div.blockUI.blockMsg.blockPage").waitUntil(Condition.not(Condition.visible), 120000);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    @Step(value = "Ждём, когда пропадут элементы загрузки")
    public static boolean isCheckNotVisibleElement(){
        try {
            $("table#text-info").find("img[src*='progress_bar.gif']").waitUntil(Condition.not(Condition.visible), 120000);
        } catch (ElementShould element) {
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, доступна ли кнопка 'Настройка СУ'")
    public static boolean isCheckLinkSettingsSU(){
        try{
            $("a.link_menu.link_top_menu").shouldNotHave(Condition.text("Настройка СУ"), Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    public static void clickItemMenu(String itemMenu) { $(byLinkText(itemMenu)).click(); }

    @Step(value = "Переходим в раздел {section}")
    public static Object openSectionWEB(String section){
        switch(section){
            case OPENSIPS_ITEM_MENU:
                clickItemMenu(section);
                return SipServerPage.getInstance();
            case SUBSCRIBERS_ITEM_MENU:
                clickItemMenu(section);
                return SubscribersPage.getInstance();
        }
        return null;
    }

    @Step(value = "Переходим в раздел {section}")
    public static Object openSectionWEB(String section, String form){
        switch(section){
            case PROVIDERS_ITEM_MENU:
                clickItemMenu(section);
                return ProvidersPage.getInstance().getInstanceProvider(form);
        }
        return null;
    }

    /***** Раздел состояний серверов *****/

    @Step(value = "Проверяем статус службы {server}")
    public static boolean isCheckModuleStatusServer(String service){
        if ($("#" + service).find("img").getAttribute("src").contains("green-icon.png")) return true;
        if ($("#" + service).find("img").getAttribute("src").contains("yellow-icon.png")) return true;
        return false;
    }

    @Step(value = "Проверяем статус SIP сервера в СУ")
    public static boolean isCheckTableStatusServer(){
        if(isCheckNotVisibleElement()){
            $("#sv-opensips").find(byClassName("label_type_module")).click();
            $("table#text-info").find("td").waitUntil(Condition.text("состояние:"), 30000);

            if($("article.module.width_quarter").find("img").getAttribute("src").contains("stat_ok.png")) return true;
        }
        return false;
    }

    @Step(value = "Проверяем на СУ состояние службы {service}")
    public static boolean isStatusService(String service, String head){
        String field_status_service;
        if(service.equals(OPENSIPS_MODULE_ID)) field_status_service = "состояние";
        else field_status_service = "состояние службы";
        if (!$("h3#head_ext_info").text().contains(head) && !$("table#text-info").find("td").text().contains(field_status_service))
            $("#" + service).find(byClassName("label_type_module")).click();
        $("table#text-info").find("td").waitUntil(Condition.text(field_status_service + ": "), 30000);
        if ($(By.xpath("//article[@class='module width_quarter']//td[contains(text(),'" + field_status_service + "')]//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed())
            return true;
        return false;
    }

    @Step(value = "Проверяем на СУ состояние {label} у службы {service}")
    public static String isConnectService(String service, String head, String command, String label){

        if (!$("h3#head_ext_info").text().contains(head) && !$("table#text-info").find("td").text().contains(label))
            $("#" + service).find(byClassName("label_type_module")).click();

        $$("table#text-info td").findBy(Condition.text(label)).waitUntil(Condition.visible, 60000);
        boolean statusSU = $(By.xpath("//article[@class='module width_quarter']//td[contains(text(),'" + label + "')]//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed();
        boolean statusServer = SSHManager.isCheckQuerySSH(command);
        if (statusServer && !statusSU) return label + " на сервере - connect, в СУ - disconnect";
        else if (!statusServer & statusSU) return label + " на сервере - disconnected, в СУ - connect";
        else if (!statusServer & !statusSU)
            return label + "на сервере - disconnected, в СУ - disconnected. Проверьте соединение со станцией!!!";
        return null;
    }

    @Step(value = "Проверяем на СУ имя интерфейса у службы {service}")
    public static String isAdapterName(String service, String head){
        if (!$("h3#head_ext_info").text().contains(head) && !$("table#text-info").find("td").text().contains("имя интерфейса"))
            $("#" + service).find(byClassName("label_type_module")).click();
        $$("table#text-info td").findBy(Condition.text("имя интерфейса: ")).waitUntil(Condition.visible, 60000);
        boolean statusSU = $(By.xpath("//article[@class='module width_quarter']//td[contains(text(),'имя интерфейса')]//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed();
        boolean adapterSU = $(By.xpath("//article[@class='module width_quarter']//td[contains(text(),'имя интерфейса')]//parent::tr/td[contains(text(),'" + DX500_BOOSTER_ADAPTER_NAME + "')]")).isDisplayed();
        boolean serverAdapter = SSHManager.isCheckQuerySSH("echo s | nc l 2220 | grep 'adapter_255: 0, eth0, ready'");
        if (! statusSU && ! adapterSU && ! serverAdapter)
            return "Имя интерфейса: на сервере занятоссти не настроен сетевой интерфейс";
        else if ((! statusSU || ! adapterSU) && serverAdapter)
            return "Имя интерфейса: на сервере корректный сетевой интерфейс, на СУ статус интерфейса - NOK";
        else if ((statusSU || adapterSU) && ! serverAdapter)
            return "Имя интерфейса: на сервере не настроен сетевой интерфейс, на СУ отображается некорректная информация";
        return null;
    }

    @Step(value = "Проверяем состояние конвертера на 'Холодильнике")
    public static boolean isCheckModuleConverterLanE1(String service){
        if($("div[server='" + service + "']").find("img").getAttribute("src").contains("lite-green-icon.png")) return true;
        return false;
    }

    @Step(value = "Проверяем состояние конвертера в таблице состояний")
    public static boolean isCheckTableConverterLanE1(String service){
        $("div[server='" + service + "']").click();
        $("table#text-info").find("td").waitUntil(Condition.text("второй уровень: "), 30000);
        if ($("article.module.width_quarter").find("img").getAttribute("src").contains("stat_ok.png")) return true;
        return false;
    }

    @Step(value = "Нажимаем кнопку 'Обновить'")
    public static void clickUpdateMonitoring(){
        $("button#manual_update_monitoring").shouldBe(Condition.visible).click();
    }

    public static SelenideElement getArticleModule(){
        return $("article.module.width_quarter");
    }

    /***** Выход из СУ *****/

    @Step(value = "Нажимаем на кнопку 'Logout'")
    public static LoginPage clickButtonLogout(){
        $("section#secondary_bar").find("a.logout_user").click();
        $$("div.ui-dialog-buttonset span").findBy(Condition.text("Выйти")).click();
        return new LoginPage();
    }
}
