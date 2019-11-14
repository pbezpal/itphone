package DataTests.Providers;

import static DataTests.DataLogin.urlServer;

public @interface DataProviderKATS {

    /***** Основные настрйоки провайдера КАТС *****/
    String KATS = "KATC";
    String MX1000 = "MX1000";
    String IPAddress = urlServer + ":5068";

    /***** Раздел регистрации в провайдере *****/
    String usernameMX1000 = "ITPhone";
    String passwordMX1000 = "itphone123";
    String dialplanMX1000 = "[3-5]XXX";
    String delayRegistration = "60";

    /***** Команда настройки конфигурациооного файла MX1000 *****/
    String testServerMX1000 = "systemctl | grep ia-supervisor";
    String checkSelectProvider = "mysql -hlocalhost -uroot -p123456 -e \"select * from registrant\" opensips | grep " + urlServer + ":5068";
    String checkSelectDialplan = "mysql -hlocalhost -uroot -p123456 -e \"select * from dialplan\" opensips | grep '[3-5]'";

    /***** сервер MX1000 *****/
    String serverMX1000 = "ia-supervisor";
}
