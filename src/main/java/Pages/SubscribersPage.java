package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataLogin.urlServer;
import static DataTests.DataSubscribers.portSIP;
import static DataTests.DataSubscribers.subscriberPassword;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.fail;

public class SubscribersPage {

    public static SubscribersPage subscribersPage = new SubscribersPage();
    public static SubscribersPage getInstance() {return subscribersPage;}

    @Step(value = "Проверяем, что мы на странице 'Справочник абонентов'")
    public static SelenideElement isSubscribersPage(){
        return $("#add_subscriber");
    }

    @Step(value = "Проверяем, существует ли абонент {subscriber}")
    public boolean isFilterSubscriber(String subscriber){
        $("div#list_users_filter").find("input").setValue(subscriber).pressEnter();
        try{
            $("table#list_users").find("td.tooltip_subscriber.tooltipstered").shouldBe(Condition.visible);
        }catch(ElementNotFound elementNotFound){
            return false;
        }
        return true;
    }

    @Step(value = "Добавляем абонета {subscriber}")
    public SubscribersPage addSubscriber(String subscriber, String portDX, boolean KATS){
        if( ! isFilterSubscriber(subscriber)){
            $("button#add_subscriber").click();
            $("form#subscriber_change").shouldBe(Condition.visible);
            $("i#sp_user_params_trigger").click();
            $("input#sp_user_name").clear();
            $("input#sp_user_name").setValue(subscriber);
            if( ! $("input#sp_ns_ip").text().equals(urlServer)){
                $("input#sp_ns_ip").clear();
                $("input#sp_ns_ip").setValue(urlServer);
            }
            if( ! $("input#sp_ns_port").text().equals("1220")){
                $("input#sp_ns_port").clear();
                $("input#sp_ns_port").setValue("1220");
            }
            $("input#sp_user_password").setValue(subscriberPassword);
            $("input#sp_user_pwd_confirm").setValue(subscriberPassword);
            $(byXpath("//span[text()='SIP']//ancestor::div[@class='sp-wrap-header']/i")).click();
            By locatorsip = byXpath("//span[text()='SIP']//ancestor::fieldset[contains(@class,'dialog sp-wrap-label params-set set-type')]");
            $(locatorsip).find(byName("name")).setValue(subscriber);
            if( ! $(locatorsip).find(byName("serv_ip")).text().equals(urlServer)){
                $(locatorsip).find(byName("serv_ip")).clear();
                $(locatorsip).find(byName("serv_ip")).setValue(urlServer);
            }
            if( ! $(locatorsip).find(byName("serv_port")).text().equals(portSIP)){
                $(locatorsip).find(byName("serv_port")).clear();
                $(locatorsip).find(byName("serv_port")).setValue(portSIP);
            }
            if( ! KATS) {
                if (!$(locatorsip).find(byName("port1")).text().equals(portDX)) {
                    $(locatorsip).find(byName("port1")).clear();
                    $(locatorsip).find(byName("port1")).setValue(portDX);
                }
            }
            $(locatorsip).find(byName("number_list")).setValue(subscriber);
            $(byName("phone")).setValue(subscriber);
            $(byName("2")).setValue(subscriber);
            if(KATS){
                $("i#sp_katc_params_trigger").click();
                $("div[aria-describedby='sp_katc']").shouldBe(Condition.visible);
                if( ! $("input#katc_fActive").isSelected()) $("input#katc_fActive").click();
                $$("div[aria-describedby='sp_katc'] span").findBy(Condition.text("Добавить")).click();
                $("span.js-katcInfo").shouldBe(Condition.visible);
            }
            $$("div.ui-dialog-buttonset span").findBy(Condition.text("Сохранить")).click();
            $("div.ui-resizable").shouldHave(Condition.visible);
            $("div.ui-resizable").findAll("div.ui-dialog-buttonset span").findBy(Condition.text("Нет")).click();
        }else fail("Пользователь " + subscriber + " уже существует");
        return this;
    }
}
