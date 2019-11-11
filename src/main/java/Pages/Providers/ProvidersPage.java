package Pages.Providers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ProvidersPage implements IProvidersPage {

    /***** Элементы страницы Провайдеры *****/
    private static final SelenideElement elementButtonAddProvider = $(By.xpath("//button[@id='add_provider']")); //Кнопка добавления провайдера
    private static final SelenideElement elementSelectTypeProvider = $(By.xpath("//select[@id='provider_type_route']"));
    private static final SelenideElement elementButtonAddProviders = $(By.xpath("//span[@class='ui-button-text' and text()='Добавить']")); //Кнопка 'Добавить'
    private static final String textAdd = "Добавление...";
    private static final String textDownload = "Загрузка...";
    private static final String textSave = "Сохранение...";
    private static final String textDelete = "Удаление...";

    public static ProvidersPage providersPage = new ProvidersPage();
    public static ProvidersPage getInstance() {return providersPage;}

    @Step(value = "Нажимаем кнопку 'Редактировать' у провайдера {provider}")
    public void clickButtonEditProvider(String provider){
        SelenideElement elementTableProvider = $(By.xpath("//table[@id='it_phone_providers']//td[text()='" + provider + "']//parent::tr//img[@class='cmd-button cmd-button-edit']"));
        elementTableProvider.click();
    }

    @Step(value = "Нажимаем на кнопку 'Добавить'")
    public void clickButtonAddProvider(){
        elementButtonAddProvider.click();
    }

    @Step(value = "Выбираем тип провайдера - {type}")
    public void clickSelectTypeProvider(String type){
        if(isCheckFormEditProvider()){
            elementSelectTypeProvider.click();
            elementSelectTypeProvider.$(By.xpath("option[@value='" + type +"']")).click();
        }
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

}
