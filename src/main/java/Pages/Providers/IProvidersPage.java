package Pages.Providers;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.Providers.DataProvidersDX500.DX500;
import static com.codeborne.selenide.Selenide.$;

public interface IProvidersPage {

    SelenideElement elementTitleProviderPage = $(By.xpath("//h3[text()='Настройка провайдеров']"));
    SelenideElement elementFormEditProvider = $(By.xpath("//div[@aria-describedby='provider_dialog_params']")); //Форма добавления провайдера
    SelenideElement elementButtonSaveProvider = $(By.xpath("//button[@id='save_and_restart_server']")); //Кнопка сохранить и перезагрузить
    String textDownload = "Загрузка...";
    String textSave = "Сохранение...";

    @Step(value = "Проверяем, что мы находимся на странице, Провайдеры")
    default boolean isCheckProviderPage() {
        try{
            elementTitleProviderPage.isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, есть ли провайдер {provider}")
    default boolean isCheckProvider(String provider){
        SelenideElement elementTableProvider = $(By.xpath("//table[@id='it_phone_providers']//td[text()='" + provider + "']"));
        Configuration.timeout = 4000;
        try {
            elementTableProvider.waitUntil(Condition.exist, 10000).isEnabled();
        } catch (ElementNotFound element) {
            return false;
        }
        return true;
    }

    void clickButtonEditProvider(String provider);
    void clickButtonAddProvider();

    @Step(value = "Проверяем, что появилась форма 'Добавление провайдера'")
    default boolean isCheckFormEditProvider(){
        try{
            elementFormEditProvider.isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    void clickSelectTypeProvider(String type);

    @Step(value = "Ждём, когда пропадёт надпись '{label}'")
    default boolean isNotVisibleLabel(String label){
        Configuration.timeout = 60000;
        try{
            $(By.xpath("//h1[text()='" + label + "']")).shouldNotBe(Condition.visible);
        }catch (ElementShould element){
            return false;
        }
        return true;
    }

    void clickButtonAddProviders();

    @Step(value = "Нажимаем кнопку 'Сохранить' у провайдера {provider}")
    default boolean clickSaveProvider(String provider){
        if(isCheckProviderPage()){
            if(isNotVisibleLabel(textDownload) && isCheckFormEditProvider()) elementButtonSaveProvider.click();
            if(isNotVisibleLabel(textSave)) return isCheckProvider(provider);
        }
        return false;
    }

    @Step(value = "Проверяем, запущен ли сервер {serverName}")
    default boolean isConfigurationServerBooster(){ return false; }

    @Step(value = "Проверяем, запущен ли сервер {serverName}")
    default boolean isCheckStartServers(String serverName){
        return SSHManager.isCheckQuerySSH("pgrep " + serverName);
    }

    default Object getInstanceProvider(String provider){
        switch(provider){
            case DX500:
                return DX500Page.getInstance();
        }
        return null;
    }
}
