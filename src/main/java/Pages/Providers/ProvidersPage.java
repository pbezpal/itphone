package Pages.Providers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static DataTests.Providers.DataProviderDX500.DX500;
import static DataTests.Providers.DataProviderKATS.KATS;
import static com.codeborne.selenide.Selenide.$;

public class ProvidersPage implements IProvidersPage{

    public static ProvidersPage providersPage = new ProvidersPage();
    public static ProvidersPage getInstance() {return providersPage;}

    @Step(value = "Проверяем, что мы находимся на странице, Провайдеры")
    public static SelenideElement isCheckProviderPage() {
        return $("#provider_button_panel");
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
