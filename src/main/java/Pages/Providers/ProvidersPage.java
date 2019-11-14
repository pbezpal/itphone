package Pages.Providers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.Providers.DataProviderDX500.DX500;
import static DataTests.Providers.DataProviderKATS.KATS;
import static com.codeborne.selenide.Selenide.$;

public class ProvidersPage implements IProvidersPage{

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

    @Override
    public Object getInstanceProvider(String provider){
        switch(provider){
            case DX500:
                return DX500Page.getInstance();
            case KATS:
                return KATSPage.getInstance();
        }
        return null;
    }

}
