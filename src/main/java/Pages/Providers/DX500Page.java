package Pages.Providers;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.DataLogin.urlServer;
import static DataTests.Providers.DataProvidersDX500.*;
import static com.codeborne.selenide.Selenide.$;

public class DX500Page extends ProvidersPage{

    /***** Раздел 'Основные настройки' *****/
    private static final SelenideElement elementSectionGeneralSettings = $(By.xpath("//li[@aria-controls='provider_common_params']")); //Вкладка 'Основные настройки'
    private static final SelenideElement elementInputNameProvider = $(By.xpath("//input[@id='provider_name']")); //Поле - Название

    /***** Раздел 'Маршрутизация вызовов' *****/
    private static final SelenideElement elementSectionRoutingCalls = $(By.xpath("//li[@aria-controls='provider_calls']")); //Вкладка 'Маршрутизация вызовов'
    private static final SelenideElement elementAddRouteCall = $(By.xpath("//button[@id='provider_add_dialplan']")); //Кнопка 'Добавить'
    private static final SelenideElement elementTableRouteCall = $(By.xpath("//table[@id='provider_table_dialplans']//tr//span")); //Запись шаблона маршрута
    private static final SelenideElement elementButtonEditRouteCall = $(By.xpath("//table[@id='provider_table_dialplans']//img[@class='cmd-button cmd-button-edit simple-mode']")); //Кнопка правки маршрута
    private static final SelenideElement elementFormEditRouteCall = $(By.xpath("//div[@id='dialog_edit_dialplan_params']")); //Форма правки маршрута
    private static final SelenideElement elementInputPatternRoute = $(By.xpath("//input[@id='provider_dialplan_match_mask_pattern']")); //Поле шаблона маршрута
    private static final SelenideElement elementButtonOkRouteCall = $(By.xpath("//span[@class='ui-button-text' and text()='Применить']")); //Кнопка 'Применить'

    /***** Раздел 'DX500' *****/
    private static final SelenideElement elementSectionDX500 = $(By.xpath("//li[@aria-controls='dx500_settings']"));
    private static final SelenideElement elementSelectServer = $(By.xpath("//select[@id='sel_add_new_server_to_prov']")); //Список серверов
    private static final SelenideElement elementButtonAddServer = $(By.xpath("//button[@id='btn_add_new_server_to_prov']")); //Кнопка добавления сервера

    /***** Раздел Ассистент *****/
    private static final SelenideElement elementSelectAdapterName = $(By.xpath("//form[contains(@id,'ac_gw-booster')]//select[@name='adapter_name']")); //Выбор имени адаптера
    private static final SelenideElement elementInputStation = $(By.xpath("//form[contains(@id,'ac_gw-booster')]//input[@name='station']")); //Поле - Номер станции
    private static final SelenideElement elementInputGateIP = $(By.xpath("//form[contains(@id,'ac_gw-booster')]//input[@name='gate_ip']")); //Поле - IP-адрес
    private static final SelenideElement elementInputGatePort = $(By.xpath("//form[contains(@id,'ac_gw-booster')]//input[@name='gate_port']")); //Поле - порт

    public static DX500Page dx500Page = new DX500Page();
    public static DX500Page getInstance() {return dx500Page;}

    public boolean addDX500Provider(){
        if(isCheckProviderPage()){
            if(! isCheckFormEditProvider()) clickButtonAddProvider();
            clickSelectTypeProvider(DX500);
            addServers();
            setGeneralSettings();
            setRouteCalls(patternRoute);
            clickButtonAddProviders();

            if(isNotVisibleLabel(getTextAdd())) return isCheckProvider(DX500);
        }
        return false;
    }

    @Step(value = "Создаем сервер {serverName}")
    public DX500Page addServer(String serverName){
        try{
            $(By.xpath("//li[contains(@aria-controls,'" + serverName + "')]")).isEnabled();
        }catch (ElementNotFound element){
            elementSelectServer.click();
            if(serverName.equals(serverSIP)) serverName = nameSIP;
            elementSelectServer.$(By.xpath("option[@value='" + serverName + "']")).click();
            elementButtonAddServer.click();
        }

        return this;
    }

    public SelenideElement getElementSectionServer(String serverName){
        return $(By.xpath("//li[contains(@aria-controls,'" + serverName + "')]"));
    }

    @Step(value = "Проверяем, создан ли сервер {serverName}")
    public boolean isCheckServer(String serverName){
        try{
            getElementSectionServer(serverName).isEnabled();
        }catch (ElementNotFound element){
            return false;
        }

        return true;
    }

    @Step(value = "Проверяем, есть ли раздел DX500")
    public boolean isSectionFormEditDX500(){
        try{
            elementSectionDX500.isEnabled();
        }catch (ElementNotFound dx500){
            return false;
        }
        return true;
    }

    @Step(value = "Переходим в раздел сервера {serverName}")
    public DX500Page clickSectionServer(String serverName){
        if(getElementSectionServer(serverName).getAttribute("aria-selected").equals("false")) {
            getElementSectionServer(serverName).click();
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Добавить SMG' на сервере {serverName}")
    public DX500Page clickButtonAddSMG(String serverName){
        $(By.xpath("//form[contains(@id,'" + serverName + "')]//button[@class='btn-dx500-add-smg']")).click();
        return this;
    }

    @Step(value = "Проверяем, что появилась форма для добавления SMG для сервера {serverName}")
    public boolean isCheckFormAddSMG(String serverName){
        try{
            $(By.xpath("//form[contains(@id,'" + serverName + "')]//div[@class='container_smg']"));
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Вводим номер SMG - {numberSMG} для сервера {serverName}")
    public DX500Page sendInputSMG(String serverName, String numberSMG){
        SelenideElement elementSMG = $(By.xpath("//form[contains(@id,'" + serverName + "')]//input[@name='smg']"));
        if( ! elementSMG.getValue().equals(numberSMG)) {
            elementSMG.clear();
            elementSMG.sendKeys(numberSMG);
        }
        return this;
    }

    @Step(value = "Настраиваем тип конвертера дял сервера {serverName}")
    public DX500Page selectTypeConverter(String serverName){
        SelenideElement elementSelectTypeConverter = $(By.xpath("//form[contains(@id,'" + serverName + "')]//select[@name='smg_converter']"));
        elementSelectTypeConverter.click();
        elementSelectTypeConverter.$(By.xpath("option[@value='Конвертер']")).click();
        return this;
    }

    @Step(value = "Вводим IP адрес конвертера - {converterIP} для сервера {serverName}")
    public DX500Page sendInputConverterIP(String serverName, String converterIP){
        SelenideElement elementConverterIP = $(By.xpath("//form[contains(@id,'" + serverName + "')]//input[@name='conv_ip']"));
        if( ! elementConverterIP.getValue().equals(converterIP)) {
            elementConverterIP.clear();
            elementConverterIP.sendKeys(converterIP);
        }
        return this;
    }

    @Step(value = "Выбираем IP адрес шлюза для сервера {serverName}")
    public DX500Page selectGateIP(String serverName){
        SelenideElement elementSelectGateIp = $(By.xpath("//form[contains(@id,'" + serverName + "')]//select[@name='gate_ip']"));
        elementSelectGateIp.click();
        elementSelectGateIp.$(By.xpath("option[@value='" + urlServer + "']")).click();
        return this;
    }

    @Step(value = "Проверяемб что сервер {serverName} не запущен")
    public boolean isCheckStatusStopServer(String serverName){
        SelenideElement elementStopServer = $(By.xpath("//form[contains(@id,'" + serverName + "')]//div[@class='provider-flex-row-dx500 group-button-control']//i[not(contains(@class,'status-start'))]"));
        try {
            elementStopServer.isEnabled();
        }catch (ElementNotFound Start){
            return false;
        }
        return true;
    }

    @Step(value = "Проверяемб что сервер {serverName} запущен")
    public boolean isCheckStatusStartServer(String serverName){
        SelenideElement elementStartServer = $(By.xpath("//form[contains(@id,'" + serverName + "')]//div[@class='provider-flex-row-dx500 group-button-control']//i[contains(@class,'status-start')]"));
        try {
            elementStartServer.waitUntil(Condition.visible, 60000).isEnabled();
        }catch (ElementNotFound Stop){
            return false;
        }
        return true;
    }

    public DX500Page setServer(String serverName, String numberSMG, String converterIP){
        if( ! isCheckServer(serverName)) addServer(serverName);
        if(serverName.equals(nameSIP)) serverName = serverSIP;
        clickSectionServer(serverName);
        clickButtonAddSMG(serverName);
        if(isCheckFormAddSMG(serverName)){
            sendInputSMG(serverName, numberSMG);
            selectTypeConverter(serverName);
            sendInputConverterIP(serverName, converterIP);
            selectGateIP(serverName);
        }
        return this;
    }

    public DX500Page setServer(){
        if( ! isCheckServer(serverBooster)) addServer(serverBooster);
        clickSectionServer(serverBooster);
        $(By.xpath("//form[contains(@id,'" + serverBooster + "')]//input[@name='ip']")).clear();
        $(By.xpath("//form[contains(@id,'" + serverBooster + "')]//input[@name='ip']")).sendKeys(dbIP);
        elementSelectAdapterName.click();
        elementSelectAdapterName.$(By.xpath("option[@value='" + adapterName + "']")).click();
        elementInputStation.sendKeys(numberStation);
        elementInputGateIP.sendKeys(gateIP);
        elementInputGatePort.sendKeys(gatePort);
        return this;
    }

    public DX500Page addServers(){
        setServer(serverSIP, SMGSIP, IPConverterSIP);
        setServer();
        setServer(serverPult, SMGPult, IPConverterPult);
        setServer(serverSIPPult, SMGSIPPult, IPConverterSIPPult);
        return this;
    }

    @Step(value = "Переходим в раздел 'Основные настройки' и добавляем название провайдера")
    public DX500Page setGeneralSettings(){
        elementSectionGeneralSettings.click();
        elementInputNameProvider.sendKeys(DX500);
        return this;
    }

    @Step(value = "Проверяем, есть ли в таблице запись с шаблоном номера")
    public boolean isCheckPatternRoute(String patternRoute){
        try {
            elementTableRouteCall.isEnabled();
            if(elementTableRouteCall.getText().equals(patternRoute)) return true;
        }catch (ElementNotFound element){
            return false;
        }
        return false;
    }

    @Step(value = "Проверяем, появилась ли форма 'Редактирование маршрута'")
    public boolean isCheckFormEditRoute(){
        try{
            elementFormEditRouteCall.isEnabled();
        }catch (ElementNotFound element){
            return false;
        }
        return true;
    }

    @Step(value = "Переходим в раздел 'Маршрутизация вызовов' и настраиваем маршрут")
    public DX500Page setRouteCalls(String patternRoute){
        elementSectionRoutingCalls.click();
        try{
            elementTableRouteCall.isEnabled();
            if( ! isCheckPatternRoute(patternRoute)) elementButtonEditRouteCall.click();
        }catch (ElementNotFound element) {
            elementAddRouteCall.click();
        }

        if(isCheckFormEditRoute()) {
            if (!elementInputPatternRoute.getValue().equals(patternRoute)) {
                elementInputPatternRoute.clear();
                elementInputPatternRoute.sendKeys(patternRoute);
            }
            elementButtonOkRouteCall.click();
        }
        return this;
    }

    @Step(value = "Переходим на вкладку DX500")
    public DX500Page clickSectionDX500(){
        if(isCheckProviderPage()){
            if(! isCheckFormEditProvider()) clickButtonEditProvider(DX500);
            if(isNotVisibleLabel(getTextDownload()) && isCheckFormEditProvider()) elementSectionDX500.click();
        }
        return this;
    }

    @Step(value = "Запускаем сервер {serverName}")
    public boolean startServer(String serverName){
        SelenideElement elementControlServer = $(By.xpath("//form[contains(@id,'" + serverName + "')]//div[@class='provider-flex-row-dx500 group-button-control ']//i[contains(@data-process,'" + serverName + "')]"));
        clickSectionServer(serverName);
        if(isCheckStatusStopServer(serverName)) elementControlServer.click();
        if(isCheckStatusStartServer(serverName)) return true;
        return false;
    }

    /***** Проверка конфигурации серверов DX500 *****/

    @Step(value = "Проверяем конфигурацию сервера Ассистентов")
    @Override
    public boolean isConfigurationServerBooster(){
        if(SSHManager.isCheckQuerySSH(boosterDBIP) && SSHManager.isCheckQuerySSH(boosterDBPort)
                && SSHManager.isCheckQuerySSH(boosterAdapterName) && SSHManager.isCheckQuerySSH(boosterStation)
                && SSHManager.isCheckQuerySSH(boosterIP) && SSHManager.isCheckQuerySSH(boosterPort)) return true;

        return false;
    }

    @Step(value = "Проверка конфигурации сервера Пультов")
    public boolean isConfigurationServerPult(){
        if(SSHManager.isCheckQuerySSH(pultContactIP) && SSHManager.isCheckQuerySSH(pultContactPort)
                && SSHManager.isCheckQuerySSH(pultSGP) && SSHManager.isCheckQuerySSH(pultIP)
                && SSHManager.isCheckQuerySSH(pultPort) && SSHManager.isCheckQuerySSH(pultMGP)
                && SSHManager.isCheckQuerySSH(SMG1_Enable) && SSHManager.isCheckQuerySSH(SMG1_SG1DEV)
                && SSHManager.isCheckQuerySSH(SMG1_MG1DEV)) return true;

        return false;
    }

    @Step(value = "Проверка конфигурации сервера SIP")
    public boolean isConfigurationServerSIP(){
        if(SSHManager.isCheckQuerySSH(sipIP) && SSHManager.isCheckQuerySSH(sipPort)
                && SSHManager.isCheckQuerySSH(sipDBIP) && SSHManager.isCheckQuerySSH(sipDPort)
                && SSHManager.isCheckQuerySSH(sipSGI) && SSHManager.isCheckQuerySSH(sipSGP)
                && SSHManager.isCheckQuerySSH(sipMGI) && SSHManager.isCheckQuerySSH(sipMGP)
                && SSHManager.isCheckQuerySSH(SMG0_Enable) && SSHManager.isCheckQuerySSH(SMG0_SG0DEV)
                && SSHManager.isCheckQuerySSH(SMG0_MG0DEV)) return true;

        return false;
    }

    @Step(value = "Проверка конфигурации сервера SIP Пульт")
    public boolean isConfigurationSIPPult(){
        if(SSHManager.isCheckQuerySSH(sipPultSGPort) && SSHManager.isCheckQuerySSH(sipPultIP)
                && SSHManager.isCheckQuerySSH(sipPultPort) && SSHManager.isCheckQuerySSH(sipPultDBIP)
                && SSHManager.isCheckQuerySSH(sipPultDBPort) && SSHManager.isCheckQuerySSH(sipPultMPPort)
                && SSHManager.isCheckQuerySSH(sipPultRingin) && SSHManager.isCheckQuerySSH(MSG3_Enable)
                && SSHManager.isCheckQuerySSH(SMG3_SG3DEV) && SSHManager.isCheckQuerySSH(SMG3_MG3DEV)) return true;

        return false;
    }

}
