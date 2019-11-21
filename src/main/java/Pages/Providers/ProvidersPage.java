package Pages.Providers;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static DataTests.Providers.PROVIDER_DX500.DX500_TYPE_PROVIDER;
import static DataTests.Providers.PROVIDER_MX1000.MX1000_TYPE_PROVIDER;
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
        if(provider.equals(DX500_TYPE_PROVIDER)) return DX500Page.getInstance();
        else if (provider.equals(MX1000_TYPE_PROVIDER)) return KATSPage.getInstance();
        return null;
    }

}
