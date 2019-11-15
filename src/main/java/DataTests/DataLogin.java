package DataTests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataLogin {

    /***** Общие пармаетры *****/
    String urlServer = "10.10.11.128";
    String urlHUB = "http://10.10.199.45:";
    String portHub = "4444";

    /***** Параметры для авторизации на WEB *****/
    String webPort = "40443";
    String webLoginAdmin = "admin";
    String webLoginOper = "user";
    String webPassword = "123456";

    /***** Параметры для авторизации по ssh *****/
    String sshLogin = "root";
    String sshPassword = "Art7Tykx78Dp";
}
