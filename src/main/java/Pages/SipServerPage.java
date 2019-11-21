package Pages;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;

import static DataTests.OPENSIPS.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class SipServerPage {

    public static SipServerPage sipServerPage = new SipServerPage();
    public static SipServerPage getInstance() {return sipServerPage;}

    @Step(value = "Проверяем, что мы на странице 'SIP-сервер'")
    public static boolean isCheckSipSettingsPage(){
        try{
            $("#sv-opensips").waitUntil(Condition.visible, 10000);
        }catch (ElementNotFound elementNotFound){
            return false;
        }
        return true;
    }

    @Step(value = "Вводим {IP} в поле 'Основной IP адрес'")
    public SipServerPage sendExternalIPAddress(String externalIP){
        if( ! $(byName("opensips-extern-addr")).getValue().equals(externalIP)){
            $(byName("opensips-extern-addr")).clear();
            $(byName("opensips-extern-addr")).sendKeys(externalIP);
        }
        return this;
    }

    @Step(value = "Вводим {sipPort} в поле 'Порт сервера'")
    public SipServerPage sendSipPort(String sipPort){
        if( ! $(byName("opensips-server-port")).getValue().equals(sipPort)){
            $(byName("opensips-server-port")).clear();
            $(byName("opensips-server-port")).sendKeys(sipPort);
        }

        return this;
    }

    @Step(value = "Вводим минимальный порт {minPort} в поле 'Диапазон портов'")
    public SipServerPage sendTurnPortMin(String minPort){
        if( ! $(byName("opensips-range-ports-start")).getValue().equals(minPort)){
            $(byName("opensips-range-ports-start")).clear();
            $(byName("opensips-range-ports-start")).sendKeys(minPort);
        }

        return this;
    }

    @Step(value = "Вводим максимальный порт {maxPort} в поле 'Диапазон портов'")
    public SipServerPage sendTurnPortMax(String maxPort){
        if( ! $(byName("opensips-range-ports-stop")).getValue().equals(maxPort)){
            $(byName("opensips-range-ports-stop")).clear();
            $(byName("opensips-range-ports-stop")).sendKeys(maxPort);
        }

        return this;
    }

    public boolean setSettingsSIPServer(String externalIP, String sipPort, String minTurnPort, String maxTurnPort){
        if(isCheckSipSettingsPage()){
            sendExternalIPAddress(externalIP);
            sendSipPort(sipPort);
            if( ! $(byName("opensips-use-it-booster")).isSelected()) $(byName("opensips-use-it-booster")).click();
            if( ! $(byName("opensips-use-rtp-proxy")).isSelected()) $(byName("opensips-use-rtp-proxy")).click();
            sendTurnPortMin(minTurnPort);
            sendTurnPortMax(maxTurnPort);
            $("button").shouldHave(Condition.text("Сохранить")).click();
            $("div.ui-draggable").findAll("div.ui-dialog-buttonset span").findBy(Condition.text("Ok")).click();
            $("div#restart_server").find("span").click();
        }

        try{
            $("div#restart_server").find("p").waitUntil(Condition.text("Настройки успешно изменены"), 30000);
        }catch (ElementNotFound elementNotFound){
            return false;
        }
        return true;

    }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP Ассистента")
    public boolean isCheckSaveSettingsITBooster(){ return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_DXTP); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера (listen=udp)")
    public boolean isCheckSaveSettingsSIPServer() { return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_UDP_SERVER); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера (localhost)")
    public boolean isCheckSaveSettingsLocalhost() { return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_UDP_LOCALHOST); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера ($avp(local_ip))")
    public boolean isCheckSaveSettingsAVPLocalhostIP() { return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_AVP_LOCAL_IP); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера ($avp(external_ip))")
    public boolean isCheckSaveSettingsAVPExternalIP() { return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_AVP_EXTERNAL_IP); }

    @Step(value = "Проверяем. что на сервере сохранились настройки минимального порта Turn сервера")
    public boolean isCheckSaveSettingsMinTurnPort() { return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_TURN_MIN_PORT); }

    @Step(value = "Проверяем. что на сервере сохранились настройки максимального порта Turn сервера")
    public boolean isCheckSaveSettingsMaxTurnPort() { return SSHManager.isCheckQuerySSH(OPENSIPS_CONFIG_TURN_MAX_PORT); }

    public boolean isCheckSettingsSipServer(){
        if(isCheckSaveSettingsITBooster() && isCheckSaveSettingsSIPServer() && isCheckSaveSettingsLocalhost()
                && isCheckSaveSettingsAVPLocalhostIP() && isCheckSaveSettingsAVPExternalIP()
                && isCheckSaveSettingsMinTurnPort() && isCheckSaveSettingsMaxTurnPort()) return true;
        else return false;
    }

    @Step(value = "Проверяем, что на сервере запущен SIP сервер")
    public boolean isCheckStatusOpensips(){ return SSHManager.isCheckQuerySSH(OPENSIPS_STATUS); }

    @Step(value = "Переходим в раздел 'Мониторинг'")
    public void clickButtonMonitoringPage(){
        $("a.link_menu.link_top_menu").shouldHave(Condition.text("Мониторинг")).click();
    }
}
