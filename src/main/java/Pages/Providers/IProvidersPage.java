package Pages.Providers;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public interface IProvidersPage {

    @Step(value = "Проверяем, что мы находимся на странице, Провайдеры")
    default SelenideElement isCheckProviderPage() {
        return $("#provider_button_panel").waitUntil(Condition.visible, 30000);
    }

    @Step(value = "Проверяем, что появился провайдер {provider} в таблице провайдеров")
    default boolean isCheckProvider(String provider, int milliseconds){
        try {
            $$("table#it_phone_providers td").findBy(Condition.text(provider)).waitUntil(Condition.visible, milliseconds);
        }catch (ElementNotFound elementNotFound){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, есть ли провайдер {provider} в таблице провайдеров")
    default boolean isCheckProvider(String provider){
        if($$("table#it_phone_providers td").findBy(Condition.text(provider)).isDisplayed()) return true;
        return false;
    }

    @Step(value = "Нажимаем кнопку 'Редактировать' у провайдера {provider}")
    default void clickButtonEditProvider(String provider){
        $$("table#it_phone_providers td").findBy(Condition.text(provider)).find(By.xpath("parent::tr")).find("img.cmd-button-edit").click();
    }

    @Step(value = "Выбираем тип провайдера - {type}")
    default void clickSelectTypeProvider(String type){
        $("#provider_type_route").click();
        $("#provider_type_route").selectOptionContainingText(type);

    }

    @Step(value = "Нажимаем кнопку 'Добавить'")
    default void clickButtonAddProviders(){
        $$("div.ui-dialog-buttonset span").findBy(Condition.text("Добавить")).click();

    }

    @Step(value = "Нажимаем кнопку 'Сохранить' у провайдера {provider}")
    default boolean clickSaveProvider(String provider){
        isCheckProviderPage();
        $("#provider_dialog_params").waitUntil(Condition.visible, 60000);
        $("#save_and_restart_server").click();
        $("div.blockUI.blockMsg.blockPage").waitUntil(Condition.not(Condition.visible), 60000);
        return isCheckProvider(provider, 60000);
    }

    @Step(value = "Проверяем, запущен ли сервер {serverName}")
    default boolean isCheckStartServers(String serverName){
        return SSHManager.isCheckQuerySSH("systemctl status " + serverName + " | awk '/active/ && !/inactive/'");
    }

    default Object getInstanceProvider(String provider){ return null; }

    @Step(value = "Вводим название провайдера {providerName}")
    default void setNameProvider(String providerName){
        $$("#provider_tab_name li").findBy(Condition.attribute("aria-controls", "provider_common_params")).click();
        $("#provider_name").clear();
        $("#provider_name").setValue(providerName);
    }

    @Step(value = "Переходим в раздел 'Маршрутизация вызовов' и настраиваем маршрут {patternRoute}")
    default void setRouteCalls(String patternRoute){
        $$("#provider_tab_name li").findBy(Condition.attribute("aria-controls", "provider_calls")).click();
        if($("#provider_table_dialplans").find("tr").isDisplayed()){
            if( ! $$("#provider_table_dialplans span").findBy(Condition.text(patternRoute)).isDisplayed())
            {
                $$("#provider_table_dialplans img").findBy(Condition.cssClass("cmd-button-edit")).click();
            }
        }else $("#provider_add_dialplan").click();


        $("#dialog_edit_dialplan_params").shouldBe(Condition.visible);
        if( ! $("#provider_flag_active_mode").isSelected()) $("#provider_flag_active_mode").click();
        $("#provider_dialplan_match_mask_pattern").clear();
        $("#provider_dialplan_match_mask_pattern").setValue(patternRoute);
        $$("div.ui-dialog-buttonset span").findBy(Condition.text("Применить")).click();
    }
}
