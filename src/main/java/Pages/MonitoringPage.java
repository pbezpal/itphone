package Pages;

import Pages.Providers.IProvidersPage;
import Pages.Providers.ProvidersPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataSipServer.linkSipServerPage;

import static DataTests.Providers.DataProvidersDX500.linkProvidersPage;
import static com.codeborne.selenide.Selenide.$;

public class MonitoringPage extends LoginPage{

    /***** Элементы тестирования авторизации на сервере *****/
    private static final SelenideElement elementSectionTitle = $(By.xpath("//h2[@class='section_title' and text()='Система управления специализированным сервером']"));
    private static final SelenideElement elementButtonLogout = $(By.xpath("//a[@class='logout_user']"));
    private static final SelenideElement elementSettingsSU = $(By.xpath("//a[@class='link_menu link_top_menu' and text()='Настройка СУ']"));
    private static final SelenideElement elementConfirmLogout = $(By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable']"));
    private static final SelenideElement elementButtonConfirmLogout = $(By.xpath("//span[@class='ui-button-text' and text()='Выйти']"));

    /***** Холодильник *****/
    private static final SelenideElement elementSystemInfo = $(By.xpath("//table[@id='text-info']//td[text()='сетевые интерфейсы:']"));

    /***** SIP сервер *****/
    private static final SelenideElement elementModuleStatusSIPServer = $(By.xpath("//div[@id='sv-opensips']//img[@src='icons/green-icon.png']"));
    private static final SelenideElement elementModuleSIPServer = $(By.xpath("//div[@id='sv-opensips']//div[@class='label_type_module']"));
    private static final SelenideElement elementTableLabelStatusSIPServer = $(By.xpath("//table[@id='text-info']//td[text()='состояние:']"));
    private static final SelenideElement elementTableStatusSIPServer = $(By.xpath("//article[@class='module width_quarter']//img[@src='icons/stat_ok.png']"));

    /***** Элементы загрузки страницы *****/
    private static final String textDownload = "//h1[text()='Загрузка...']";
    private static final String progressBar = "//img[@src='icons/progress_bar.gif']";

    public MonitoringPage(){}

    @Step(value = "Проверяем, авторизованы ли мы на сервере")
    public static boolean isCheckLogin(){
        try{
            elementSectionTitle.waitUntil(Condition.visible, 10000).isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, что текущий пользователь {user}")
    public static boolean isCheckUser(String user){
        SelenideElement elementUserName = $(By.xpath("//div[@class='user']/p[text()='" + user + "']"));
        try{
            elementUserName.isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Ждём, когда пропадёт {locator}")
    public static boolean isCheckNotVisibleElement(String locator){
        Configuration.timeout = 60000;
        try{
            $(By.xpath(locator)).shouldNot(Condition.visible);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, доступна ли кнопка 'Настройка СУ'")
    public boolean isCheckLinkSettingsSU(){
        try{
            elementSettingsSU.shouldNotBe(Condition.exist);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    public void clickItemMenu(String itemMenu) {
        if(isCheckLogin() && isCheckNotVisibleElement(textDownload) && isCheckNotVisibleElement(progressBar)){
            $(By.xpath("//a[@class='link_menu' and @title='" + itemMenu + "']")).click();
        }
        Configuration.timeout = 4000;
    }

    @Step(value = "Переходим в раздел {section}")
    public Object openSectionWEB(String section){
        switch(section){
            case linkSipServerPage:
                if(! SipServerPage.getInstance().isCheckSipSettingsPage()) clickItemMenu(section);
                return SipServerPage.getInstance();
        }
        return null;
    }

    @Step(value = "Переходим в раздел {section}")
    public Object openSectionWEB(String section, String form){
        switch(section){
            case linkProvidersPage:
                if(! ProvidersPage.getInstance().isCheckProviderPage()) clickItemMenu(section);
                return ProvidersPage.getInstance().getInstanceProvider(form);
        }
        return null;
    }

    /***** Раздел SIP сервера *****/

    @Step(value = "Проверяем, что на 'Холодильнике' у SIP сервера зеленый статус")
    public boolean isCheckModuleStatusSIPServer(){
        if(isCheckNotVisibleElement(textDownload) && isCheckNotVisibleElement(progressBar)) {
            try {
                elementModuleStatusSIPServer.isEnabled();
            } catch (ElementNotFound element) {
                return false;
            }
            return true;
        }else return false;
    }

    @Step(value = "Проверяем, что в поле состояние SIP сервера, стоит статус Ok")
    public boolean isCheckTableStatusSIPServer(){
        if(isCheckNotVisibleElement(textDownload) && isCheckNotVisibleElement(progressBar)){
            Configuration.timeout = 4000;
            elementModuleSIPServer.click();
            try{
                elementTableLabelStatusSIPServer.waitUntil(Condition.exist, 30000).isEnabled();
                try{
                    elementTableStatusSIPServer.isEnabled();
                }catch (ElementNotFound element){
                    return false;
                }
                return true;
            }catch (ElementNotFound element){
                return false;
            }
        }else return false;
    }

    /***** Выход из СУ *****/

    @Step(value = "Проверяем, что появилась форма подтверждения выхода")
    public static boolean isCheckFormConfirmLogout(){
        try{
            elementConfirmLogout.isEnabled();
            return true;
        }catch (ElementNotFound element){
            return false;
        }
    }

    @Step(value = "Нажимаем на кнопку 'Logout'")
    public static LoginPage clickButtonLogout(){
        if(isCheckLogin() && isCheckNotVisibleElement(textDownload) && isCheckNotVisibleElement(progressBar)) {
            Configuration.timeout = 4000;
            elementButtonLogout.click();
            if(isCheckFormConfirmLogout()) elementButtonConfirmLogout.click();
        }

        return new LoginPage();
    }
}
