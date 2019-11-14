package Pages;

import Pages.Providers.ProvidersPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataSipServer.linkSipServerPage;
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

    @Step(value = "Проверяем, что текущий пользователь {user}")
    public static boolean isCheckUser(String user){
        try{
            $("section#secondary_bar").find("p").shouldHave(Condition.text(user), Condition.visible);
        }catch (ElementNotFound element){
            return false;
        }
        return true;
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
                if(! SipServerPage.getInstance().isCheckSipSettingsPage()) clickItemMenu(section);
                return SipServerPage.getInstance();
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

    /***** Раздел SIP сервера *****/

    @Step(value = "Проверяем, что на 'Холодильнике' у SIP сервера зеленый статус")
    public static boolean isCheckModuleStatusSIPServer(){
        if(isCheckNotVisibleElement()){
            if($("#sv-opensips").find("img").getAttribute("src").contains("green-icon.png")) return true;
            return false;
        }
        return false;
    }

    @Step(value = "Проверяем, что в поле состояние SIP сервера, стоит статус Ok")
    public static boolean isCheckTableStatusSIPServer(){
        if(isCheckNotVisibleElement()){
            $("#sv-opensips").find(byClassName("label_type_module")).click();
            $("table#text-info").find("td").waitUntil(Condition.text("состояние:"), 30000);

            if($("article.module.width_quarter").find("img").getAttribute("src").contains("stat_ok.png")) return true;
            return false;


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
