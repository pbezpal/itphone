package Pages.Providers;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static DataTests.LOGIN.IP_SERVER;
import static DataTests.Providers.PROVIDER_MX1000.*;
import static Pages.MonitoringPage.isCheckNotVisibleDownload;
import static com.codeborne.selenide.Selenide.$;

public class KATSPage extends ProvidersPage implements IProvidersPage {

    public static KATSPage katsPage = new KATSPage();
    public static KATSPage getInstance() {return katsPage;}

    @Step(value = "Проверяем, установлен ли на сервер MAX1000")
    public boolean isMX1000(){
        return SSHManager.isCheckQuerySSH(MX1000_STATUS);
    }

    @Step(value = "Вводим IP адрес и порт {domain}")
    public KATSPage setInputAddress(String domain){
        $("#provider_address").setValue(domain);
        return this;
    }

    @Step(value = "Вводим имя пользователя {username}")
    public KATSPage setUsername(String username){
        $("#provider_login_trank").setValue(username);
        return this;
    }

    @Step(value = "Вводим пароль {password}")
    public KATSPage setPassword(String password){
        $("#provider_pas_trank").setValue(password);
        return this;
    }

    @Step(value = "Подтверждаем пароль {password}")
    public KATSPage setConfirmPassword(String password){
        $("#provider_pas_duplicate_trank").setValue(password);
        return this;
    }

    @Step(value = "Выбираем IP SIP сервера")
    public KATSPage selectIPSipServer(){
        $("select#provider_sip_binding_uri").selectOptionContainingText(IP_SERVER);
        return this;
    }

    @Step(value = "Вводим количество секунд {seconds} в поле 'Интервал регистрации'")
    public KATSPage setPeriodRegistration(String seconds){
        $("#provider_registr_interval").setValue(seconds);
        return this;
    }

    public boolean addMX1000(String name, String domain, String username, String password, String dialplan, String interval){
        if(isCheckNotVisibleDownload()) {
            isCheckProviderPage();
            $("#add_provider").click();
            isFormEditProvider();
            clickSelectTypeProvider(MX1000_TYPE_PROVIDER);
            setNameProvider(name);
            setInputAddress(domain);
            setUsername(username);
            setPassword(password);
            setConfirmPassword(password);
            selectIPSipServer();
            setPeriodRegistration(interval);
            setRouteCalls(dialplan);
            clickButtonAddProviders();
            if(isCheckNotVisibleDownload()) return isCheckProvider(MAX1000_NAME);
        }

        return false;
    }

    @Step(value = "Проверяем, добавление провайдера в базу данных")
    public boolean isSelectProvider(){
        return SSHManager.isCheckQuerySSH(MX1000_TABLE_REGISTRANT);
    }

    @Step(value = "Проверяем, что маршрут добавлен в базу данных")
    public boolean isSelectDialplan(){
        return SSHManager.isCheckQuerySSH(MX1000_TABLE_DIALPLAN);
    }
}
