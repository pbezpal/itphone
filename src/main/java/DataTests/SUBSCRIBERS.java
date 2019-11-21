package DataTests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static DataTests.GET_DATA_TESTS.*;

//@Retention(RetentionPolicy.RUNTIME)
public @interface SUBSCRIBERS {

    String SUBSCRIBERS_ITEM_MENU = "Справочник абонентов";
    String SUBSCRIBER_PASSWORD = getProperty("SUBSCRIBER_PASSWORD");
    String SUBSCRIBER_SIP_PORT = getProperty("SUBSCRIBER_SIP_PORT");

    //Параметры пользователя 5000
    String SUBSCRIBER_5000 = getProperty("SUBSCRIBER_5000");
    String SUBSCRIBER_PORT_DX_5000 = getProperty("SUBSCRIBER_PORT_DX_5000");

    //Парметры пользователя 5001
    String SUBSCRIBER_5001 = getProperty("SUBSCRIBER_5001");
    String SUBSCRIBER_PORT_DX_5001 = getProperty("SUBSCRIBER_PORT_DX_5001");

    //Параметры пользователя 4000
    String SUBSCRIBER_4000 = getProperty("SUBSCRIBER_4000");
    String SUBSCRIBER_PORT_DX_4000 = getProperty("SUBSCRIBER_PORT_DX_4000");

    //Параметры пользователя 4001
    String SUBSCRIBER_4001 = getProperty("SUBSCRIBER_4001");
    String SUBSCRIBER_PORT_DX_4001 = getProperty("SUBSCRIBER_PORT_DX_4001");

    //Проверяем, работает ли сервер контактов
    String SV_CONTACTS_STATUS = "systemctl status sv-contacts | awk '/active/ && !/inactive/'";
}
