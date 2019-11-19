package Pages;

import HelperClasses.SSHManager;
import Pages.Providers.ProvidersPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataSipServer.linkSipServerPage;
import static DataTests.DataSubscribers.linkSubscribers;
import static DataTests.Providers.DataProviderDX500.adapterName;
import static DataTests.Providers.Providers.linkProvidersPage;
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
        if($("article#header").find("h3").text().equals("Состояние системы")) return true;
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
            $("div.blockUI.blockMsg.blockPage").waitUntil(Condition.not(Condition.visible), 60000);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    @Step(value = "Ждём, когда пропадут элементы загрузки")
    public static boolean isCheckNotVisibleElement(){
        if(isCheckNotVisibleDownload()) {
            try {
                $("table#text-info").find("img[src*='progress_bar.gif']").waitUntil(Condition.not(Condition.visible), 60000);
            } catch (ElementShould element) {
                return false;
            }
            return true;
        }

        return false;

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

    public static void clickItemMenu(String itemMenu) {
        if(isCheckLogin() && isCheckNotVisibleElement()){
            $(byLinkText(itemMenu)).click();
        }
    }

    @Step(value = "Переходим в раздел {section}")
    public static Object openSectionWEB(String section){
        switch(section){
            case linkSipServerPage:
                clickItemMenu(section);
                return SipServerPage.getInstance();
            case linkSubscribers:
                clickItemMenu(section);
                return SubscribersPage.getInstance();
        }
        return null;
    }

    @Step(value = "Переходим в раздел {section}")
    public static Object openSectionWEB(String section, String form){
        switch(section){
            case linkProvidersPage:
                clickItemMenu(section);
                return ProvidersPage.getInstance().getInstanceProvider(form);
        }
        return null;
    }

    /***** Раздел состояний серверов *****/

    @Step(value = "Проверяем статус службы {server}")
    public static boolean isCheckModuleStatusServer(String service){
        if(isCheckNotVisibleElement()){
            if($("#" + service).find("img").getAttribute("src").contains("green-icon.png")) return true;
            return false;
        }
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
    public static boolean isStatusService(String service){
        if(isCheckNotVisibleElement()){
            if( ! $("table#text-info").find("td").text().contains("состояние службы")) $("#" + service).find(byClassName("label_type_module")).click();
            $("table#text-info").find("td").waitUntil(Condition.text("состояние службы: "), 30000);
            if($(By.xpath("article[@class='module width_quarter']//td[text()='состояние службы: ']//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed()) return true;
        }
        return false;
    }

    @Step(value = "Проверяем на СУ состояние срвера контактов у службы {service}")
    public static String isConnectServerContacts(String service, String controlPort){
        if(isCheckNotVisibleElement()){
            if( ! $("table#text-info").find("td").text().contains("сервер контактов")) $("#" + service).find(byClassName("label_type_module")).click();
            $("table#text-info").find("td").waitUntil(Condition.text("сервер контактов: "), 30000);
            boolean statusSU = $(By.xpath("article[@class='module width_quarter']//td[text()='сервер контактов: ']//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed();
            boolean statusServer = SSHManager.isCheckQuerySSH(" echo s | nc l " + controlPort + " | grep 'db: logined'");
            if(statusServer && ! statusSU) return "Соединение с сервером контактов: на сервере - logined, в СУ - NOK";
            else if( ! statusServer & statusSU) return "Соединение с сервером контактов: на сервере - disconnected, в СУ - OK";
            return null;
        }
        return "Не удалось получить статус сервера контактов";
    }

    @Step(value = "Проверяем на СУ соединение со станцией у службы {service}")
    public static String isConnectStation(String service, String command){
        if(isCheckNotVisibleElement()){
            if( ! $("table#text-info").find("td").text().contains("соединение со станцией")) $("#" + service).find(byClassName("label_type_module")).click();
            $("table#text-info").find("td").waitUntil(Condition.text("соединение со станцией: "), 30000);
            boolean statusSU = $(By.xpath("article[@class='module width_quarter']//td[text()='соединение со станцией: ']//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed();
            boolean statusServer = SSHManager.isCheckQuerySSH(command);
            if(statusServer && ! statusSU) return "Соединение со станцией: на сервере - lapd_establish, в СУ - NOK";
            else if( ! statusServer & statusSU) return "Соединение со станцией: на сервере - disconnected, в СУ - OK";
            return null;
        }
        return "Не удалось получить статус станции";
    }

    @Step(value = "Проверяем на СУ имя интерфейса у службы {service}")
    public static String isAdapterName(String service){
        if(isCheckNotVisibleElement()){
            if( ! $("table#text-info").find("td").text().contains("имя интерфейса")) $("#" + service).find(byClassName("label_type_module")).click();
            $("table#text-info").find("td").waitUntil(Condition.text("имя интерфейса: "), 30000);
            boolean statusSU = $(By.xpath("article[@class='module width_quarter']//td[text()='имя интерфейса: ']//parent::tr//img[contains(@src,'stat_ok.png')]")).isDisplayed();
            boolean adapterSU = $(By.xpath("article[@class='module width_quarter']//td[text()='имя интерфейса: ']//parent::tr/td[contains(text(),'" + adapterName + "')]")).isDisplayed();
            boolean serverAdapter = SSHManager.isCheckQuerySSH("echo s | nc l 2220 | grep 'adapter_255: 0, eth0, ready'");
            if(statusSU && adapterSU && ! serverAdapter) return "Имя интерфейса: на сервере указан некорректный сетевой интерфейс, на СУ статус интерфейса - OK";
            else if(!statusSU && adapterSU && serverAdapter) return "Имя интерфейса: на сервере корректный сетевой интерфейс, на СУ статус интерфейса - NOK";
            else if(statusSU && !adapterSU && serverAdapter) return "Имя интерфейса: на сервере корректный сетевой интерфейс, на СУ статус интерфейса - NOK, но указан некорректное имя интерфеса";
            return null;
        }
        return "Не удалось получить статус сетевого интерфейса";
    }

    @Step(value = "Проверяем состояние конвертера на 'Холодильнике")
    public static boolean isCheckModuleConverterLanE1(String service){
        if(isCheckNotVisibleElement()){
            if($("div[server='" + service + "']").find("img").getAttribute("src").contains("lite-green-icon.png")) return true;
        }
        return false;
    }

    @Step(value = "Проверяем состояние конвертера в таблице состояний")
    public static boolean isCheckTableConverterLanE1(String service){
        if(isCheckNotVisibleElement()){
            $("div[server='" + service + "']").click();
            $("table#text-info").find("td").waitUntil(Condition.text("второй уровень:"), 30000);
            if($("article.module.width_quarter").find("img").getAttribute("src").contains("stat_ok.png")) return true;
        }
        return false;
    }

    /***** Выход из СУ *****/

    @Step(value = "Нажимаем на кнопку 'Logout'")
    public static LoginPage clickButtonLogout(){
        $("section#secondary_bar").find("a.logout_user").click();
        $$("div.ui-dialog-buttonset span").findBy(Condition.text("Выйти")).click();
        return new LoginPage();
    }
}
