package DataTests;

import static DataTests.GET_DATA_TESTS.*;

public @interface LOGIN {

    /***** Общие пармаетры *****/
    String HOST_SERVER = getProperty("HOST_SERVER");
    String IP_SERVER = getProperty("IP_SERVER");
    String HOST_HUB = getProperty("HOST_HUB");

    /***** Параметры для авторизации на WEB *****/
    String LOGIN_ADMIN_WEB = getProperty("LOGIN_ADMIN_WEB");
    String LOGIN_OPER_WEB = getProperty("LOGIN_OPER_WEB");
    String LOGIN_PASSWORD_WEB = getProperty("LOGIN_PASSWORD_WEB");

    /***** Параметры для авторизации по ssh *****/
    String LOGIN_ADMIN_SSH = getProperty("LOGIN_ADMIN_SSH");
    String LOGIN_PASSWORD_SSH = getProperty("LOGIN_PASSWORD_SSH");

}
