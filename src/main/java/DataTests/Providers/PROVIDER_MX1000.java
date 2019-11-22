package DataTests.Providers;

import static DataTests.GET_DATA_TESTS.*;

public @interface PROVIDER_MX1000 {

    /***** Основные настрйоки провайдера КАТС *****/
    String MX1000_TYPE_PROVIDER = "KATC";
    String MX1000_NAME = getProperty("MX1000_NAME");
    String MX1000_HOST = getProperty("MX1000_HOST");

    /***** Раздел регистрации в провайдере *****/
    String MX1000_USERNAME = getProperty("MX1000_USERNAME");
    String MX1000_PASSWORD = getProperty("MX1000_PASSWORD");
    String MX1000_DIALPLAN = getProperty("MX1000_DIALPLAN");
    String MX1000_DELAY_REGISTRATION = getProperty("MX1000_DELAY_REGISTRATION");

    /***** Команда настройки конфигурациооного файла MX1000 *****/
    String MX1000_STATUS = "systemctl | grep ia-supervisor";
    String MX1000_TABLE_REGISTRANT = "mysql -hlocalhost -uroot -p123456 -e 'select * from registrant' opensips | awk '/" + MX1000_HOST +"/'";
    String MX1000_TABLE_DIALPLAN = "mysql -hlocalhost -uroot -p123456 -e 'select * from dialplan' opensips | awk '/3-5/ && /XXX/'";

    /***** сервер MX1000 *****/
    String MX1000_NAME_SERVICE = "ia-supervisor";
}
