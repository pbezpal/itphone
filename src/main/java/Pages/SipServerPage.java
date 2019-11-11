package Pages;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShouldNot;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataSipServer.*;
import static com.codeborne.selenide.Selenide.$;

public class SipServerPage {

    /***** Кнопка Мониторинг *****/
    private SelenideElement elementButtonMonitoringPage = $(By.xpath("//a[@class='link_menu link_top_menu' and text()='Мониторинг']"));

    /***** Раздел SIP сервер *****/
    private static final SelenideElement elementTitleForm = $(By.xpath("//a[@id='sv-opensips']")); //
    private static final SelenideElement elementInputSIPExternalAddr = $(By.xpath("//input[@name='opensips-extern-addr']")); // Поле - Основной IP адрес
    private static final SelenideElement elementInputSipAddressList = $(By.xpath("//input[@name='opensips-address-list']")); // Поле - Дополнительные IP адреса
    private static final SelenideElement elementInputSIPPort = $(By.xpath("//input[@name='opensips-server-port']")); // Поле - Порт сервера
    private static final SelenideElement elementUseITBooster = $(By.xpath("//input[@name='opensips-use-it-booster']")); // Чекбокс - Ассистент (booster)
    private static final SelenideElement elementUseRTPProxy = $(By.xpath("//input[@name='opensips-use-rtp-proxy']")); // Чекбокс - RTP прокси
    private static final SelenideElement elementInputTurnPortMin = $(By.xpath("//input[@name='opensips-range-ports-start']")); // Поле - Диапазон портов (начальный порт)
    private static final SelenideElement elementInputTurnPortMax = $(By.xpath("//input[@name='opensips-range-ports-stop']")); // Поле - Диапазон портов (конечный порт)
    private static final SelenideElement elementButtonSave = $(By.xpath("//button[@class='save_setting button button_standart']")); // Кнопка - Сохранить
    private static final SelenideElement elementFormConfirmSave = $(By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable']")); // Окно - Сохранение настроек
    private static final SelenideElement elementButtonConfirmOk = $(By.xpath("//span[@class='ui-button-text' and text()='Ok']")); // Кнопка - ОК, в окне сохранение настроек
    private static final SelenideElement elementRestartServer = $(By.xpath("//div[@id='restart_server']//span")); // Ссылка для рестарта сервисов
    private static final SelenideElement elementSettingsSuccessfully = $(By.xpath("//div[@id='restart_server']/p[text()='Настройки успешно изменены']")); // Надпись после сохранения и перезагрузки сервисов

    public static SipServerPage sipServerPage = new SipServerPage();
    public static SipServerPage getInstance() {return sipServerPage;}

    @Step(value = "Проверяем, что мы на странице 'SIP-сервер'")
    public static boolean isCheckSipSettingsPage(){
        try{
            elementTitleForm.waitUntil(Condition.visible, 10000).isEnabled();
            return true;
        }catch(ElementNotFound element){
            return false;
        }
    }

    @Step(value = "Вводим {IP} в поле 'Основной IP адрес'")
    public SipServerPage sendExternalIPAddress(String externalIP){
        if( ! elementInputSIPExternalAddr.getValue().equals(externalIP)){
            elementInputSIPExternalAddr.clear();
            elementInputSIPExternalAddr.sendKeys(externalIP);
        }

        return this;
    }

    @Step(value = "Вводим {sipPort} в поле 'Порт сервера'")
    public SipServerPage sendSipPort(String sipPort){
        if( ! elementInputSIPPort.getValue().equals(sipPort)){
            elementInputSIPPort.clear();
            elementInputSIPPort.sendKeys(sipPort);
        }

        return this;
    }

    @Step(value="Проверяем, что стоит галочка 'Ассистент (booster)'")
    public boolean isCheckSelectedUseITBooster(){
        if(elementUseITBooster.isSelected()) return true;
        else return false;
    }

    @Step(value = "Проверяем, что чтоит галочка 'RTP прокси'")
    public boolean isCheckSelectedUseRTPProxy(){
        if(elementUseRTPProxy.isSelected()) return true;
        else return false;
    }

    @Step(value = "Вводим минимальный порт {minPort} в поле 'Диапазон портов'")
    public SipServerPage sendTurnPortMin(String minPort){
        if( ! elementInputTurnPortMin.getValue().equals(minPort)){
            elementInputTurnPortMin.clear();
            elementInputTurnPortMin.sendKeys(minPort);
        }

        return this;
    }

    @Step(value = "Вводим максимальный порт {maxPort} в поле 'Диапазон портов'")
    public SipServerPage sendTurnPortMax(String maxPort){
        if( ! elementInputTurnPortMax.getValue().equals(maxPort)){
            elementInputTurnPortMax.clear();
            elementInputTurnPortMax.sendKeys(maxPort);
        }

        return this;
    }

    @Step(value = "Нажимаем кнопку 'Сохранить'")
    public SipServerPage clickButtonSave(){
        elementButtonSave.click();
        return this;
    }

    @Step(value = "Проверяем, что появилось окно 'Сохранение настроек'")
    public boolean isCheckConfirmSave(){
        try{
            elementFormConfirmSave.isEnabled();
            return true;
        }catch (ElementNotFound element){
            return false;
        }
    }

    @Step(value = "Нажимаем кнопку 'Ok' в окне 'Сохранение настроек'")
    public SipServerPage clickButtonOkConfirmSave(){
        if(isCheckConfirmSave()) elementButtonConfirmOk.click();
        return this;
    }

    @Step(value = "Проверяем, что появилась ссылка для перезагрузки служб")
    public boolean isCheckLinkRestartServer(){
        try{
            elementRestartServer.isEnabled();
            return true;
        }catch (ElementNotFound element){
            return false;
        }
    }

    @Step(value = "Перезагружаем службы связанные с SIP сервером")
    public SipServerPage clickLinkRestartServer(){
        if(isCheckLinkRestartServer()) elementRestartServer.click();
        return this;
    }

    @Step(value = "Проверяем, что настройки успешно применены")
    public boolean isCheckSettingsSuccessfully(){
        try{
            elementSettingsSuccessfully.shouldBe(Condition.exist);
            return true;
        }catch (ElementShouldNot element){
            return false;
        }
    }

    public boolean setSettingsSIPServer(String externalIP, String sipPort, String minTurnPort, String maxTurnPort){
        if(isCheckSipSettingsPage()){
            sendExternalIPAddress(externalIP);
            sendSipPort(sipPort);
            if( ! isCheckSelectedUseITBooster()) elementUseITBooster.click();
            if( ! isCheckSelectedUseRTPProxy()) elementUseRTPProxy.click();
            sendTurnPortMin(minTurnPort);
            sendTurnPortMax(maxTurnPort);
            clickButtonSave();
            clickButtonOkConfirmSave();
            clickLinkRestartServer();
        }

        return isCheckSettingsSuccessfully();
    }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP Ассистента")
    public boolean isCheckSaveSettingsITBooster(){ return SSHManager.isCheckQuerySSH(commandCheckSettingsDXTP); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера (listen=udp)")
    public boolean isCheckSaveSettingsSIPServer() { return SSHManager.isCheckQuerySSH(commandCheckSettingsUDPServer); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера (localhost)")
    public boolean isCheckSaveSettingsLocalhost() { return SSHManager.isCheckQuerySSH(commandCheckSettingsUDPLocalhost); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера ($avp(local_ip))")
    public boolean isCheckSaveSettingsAVPLocalhostIP() { return SSHManager.isCheckQuerySSH(commandCheckAVPLocalIP); }

    @Step(value = "Проверяем. что на сервере сохранились настройки для SIP сервера ($avp(external_ip))")
    public boolean isCheckSaveSettingsAVPExternalIP() { return SSHManager.isCheckQuerySSH(commandCheckAVPExternalIP); }

    @Step(value = "Проверяем. что на сервере сохранились настройки минимального порта Turn сервера")
    public boolean isCheckSaveSettingsMinTurnPort() { return SSHManager.isCheckQuerySSH(commandCheckSettingsTurnMinPort); }

    @Step(value = "Проверяем. что на сервере сохранились настройки максимального порта Turn сервера")
    public boolean isCheckSaveSettingsMaxTurnPort() { return SSHManager.isCheckQuerySSH(commandCheckSettingsTurnMaxPort); }

    public boolean isCheckSettingsSipServer(){
        if(isCheckSaveSettingsITBooster() && isCheckSaveSettingsSIPServer() && isCheckSaveSettingsLocalhost()
                && isCheckSaveSettingsAVPLocalhostIP() && isCheckSaveSettingsAVPExternalIP()
                && isCheckSaveSettingsMinTurnPort() && isCheckSaveSettingsMaxTurnPort()) return true;
        else return false;
    }

    @Step(value = "Проверяем, что на сервере запущен SIP сервер")
    public boolean isCheckStatusOpensips(){ return SSHManager.isCheckQuerySSH(commandStatusSIPServer); }

    @Step(value = "Переходим в раздел 'Мониторинг'")
    public MonitoringPage clickButtonMonitoringPage(){
        elementButtonMonitoringPage.click();
        return new MonitoringPage();
    }
}
