package Pages.Providers;

import HelperClasses.SSHManager;
import Pages.MonitoringPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.Providers.DataProvidersDX500.*;
import static com.codeborne.selenide.Selenide.$;

public class ProvidersPage extends MonitoringPage implements IProvidersPage {

    /***** Элементы страницы Провайдеры *****/
    private static final SelenideElement elementTitleProviderPage = $(By.xpath("//h3[text()='Настройка провайдеров']"));
    private static final SelenideElement elementButtonAddProvider = $(By.xpath("//button[@id='add_provider']")); //Кнопка добавления провайдера
    private static final SelenideElement elementFormEditProvider = $(By.xpath("//div[@aria-describedby='provider_dialog_params']")); //Форма добавления провайдера
    private static final SelenideElement elementSelectTypeProvider = $(By.xpath("//select[@id='provider_type_route']"));
    private static final SelenideElement elementButtonAddProviders = $(By.xpath("//span[@class='ui-button-text' and text()='Добавить']")); //Кнопка 'Добавить'
    private static final SelenideElement elementButtonSaveProvider = $(By.xpath("//button[@id='save_and_restart_server']")); //Кнопка сохранить и перезагрузить
    private static final String textAdd = "Добавление...";
    private static final String textDownload = "Загрузка...";
    private static final String textSave = "Сохранение...";
    private static final String textDelete = "Удаление...";

    public static ProvidersPage providersPage = new ProvidersPage();
    public static ProvidersPage getInstance() {return providersPage;}

    @Step(value = "Проверяем, что мы находимся на странице, Провайдеры")
    @Override
    public boolean isCheckProviderPage(){
        try{
            elementTitleProviderPage.isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, есть ли провайдер {provider}")
    @Override
    public boolean isCheckProvider(String provider){
        SelenideElement elementTableProvider = $(By.xpath("//table[@id='it_phone_providers']//td[text()='" + provider + "']"));
        Configuration.timeout = 4000;
        try {
            elementTableProvider.waitUntil(Condition.exist, 10000).isEnabled();
        } catch (ElementNotFound element) {
            return false;
        }
            return true;
    }

    @Step(value = "Нажимаем кнопку 'Редактировать' у провайдера {provider}")
    public void clickButtonEditProvider(String provider){
        SelenideElement elementTableProvider = $(By.xpath("//table[@id='it_phone_providers']//td[text()='" + provider + "']//parent::tr//img[@class='cmd-button cmd-button-edit']"));
        elementTableProvider.click();
    }

    @Step(value = "Нажимаем на кнопку 'Добавить'")
    public void clickButtonAddProvider(){
        elementButtonAddProvider.click();
    }

    @Step(value = "Проверяем, что появилась форма 'Добавление провайдера'")
    @Override
    public boolean isCheckFormEditProvider(){
        try{
            elementFormEditProvider.isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Выбираем тип провайдера - {type}")
    public void clickSelectTypeProvider(String type){
        if(isCheckFormEditProvider()){
            elementSelectTypeProvider.click();
            elementSelectTypeProvider.$(By.xpath("option[@value='" + type +"']")).click();
        }
    }

    @Step(value = "Ждём, когда пропадёт надпись '{label}'")
    public boolean isNotVisibleLabel(String label){
        Configuration.timeout = 60000;
        try{
            $(By.xpath("//h1[text()='" + label + "']")).shouldNotBe(Condition.visible);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    @Step(value = "Нажимаем кнопку 'Добавить'")
    public void clickButtonAddProviders(){ elementButtonAddProviders.click(); }

    public String getTextAdd(){
        return textAdd;
    }

    public String getTextDownload(){
        return textDownload;
    }

    public String getTextSave(){
        return textSave;
    }

    public String getTextDelete(){
        return textDelete;
    }

    @Step(value = "Нажимаем кнопку 'Сохранить' у провайдера {provider}")
    @Override
    public boolean clickSaveProvider(String provider){
        if(isCheckProviderPage()){
            if(isNotVisibleLabel(textDownload) && isCheckFormEditProvider()) elementButtonSaveProvider.click();
            if(isNotVisibleLabel(textSave)) return isCheckProvider(provider);
        }
        return false;
    }

    @Step(value = "Проверяем, запущен ли сервер {serverName}")
    public boolean isCheckStartServersDX500(String serverName){
        return SSHManager.isCheckQuerySSH("pgrep " + serverName);
    }

    @Override
    public Object getInstanceProvider(String provider){
        switch(provider){
            case DX500:
                return DX500Page.getInstance();
        }
        return null;
    }
}
