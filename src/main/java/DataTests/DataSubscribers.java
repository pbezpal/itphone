package DataTests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataSubscribers {

    String linkSubscribers = "Справочник абонентов";
    String subscriberPassword = "123456Vv";
    String portSIP = "5060";

    //Параметры пользователя 5000
    String subscriber5000 = "5000";
    String portDX5000 = "0,101";

    //Парметры пользователя 5001
    String subscriber5001 = "5001";
    String portDX5001 = "0,102";

    //Параметры пользователя 4000
    String subscriber4000 = "4000";
    String portDX4000 = "0,1";

    //Параметры пользователя 4001
    String subscriber4001 = "4001";
    String portDX4001 = "0,2";

    //Проверяем, работает ли сервер контактов
    String commandStatusSIPServer = "systemctl status sv-contacts | awk '/active/ && !/inactive/'";
}
