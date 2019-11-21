package DataTests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static DataTests.GET_DATA_TESTS.*;
import static DataTests.LOGIN.*;

@Retention(RetentionPolicy.RUNTIME)
@LOGIN
public @interface OPENSIPS {

    /***** Параметры SIP сервера *****/

    String OPENSIPS_ITEM_MENU = "SIP-сервер";
    String OPENSIPS_SERVER = getProperty("OPENSIPS_SERVER");
    String OPENSIPS_MODULE_ID = getProperty("OPENSIPS_MODULE_ID");
    String OPENSIPS_SERVER_PORT = getProperty("OPENSIPS_SERVER_PORT");
    String OPENSIPS_BOOSTER_PORT = getProperty("OPENSIPS_BOOSTER_PORT");
    String OPENSIPS_TURN_PORT_MIN = getProperty("OPENSIPS_TURN_PORT_MIN");
    String OPENSIPS_TURN_PORT_MAX = getProperty("OPENSIPS_TURN_PORT_MAX");

    /***** Команды проверки конфигураци SIP сервера *****/
    String OPENSIPS_CONFIG_DXTP = "cat /etc/opensips/opensips.cfg|grep listen=dxtp:" + IP_SERVER +":" + OPENSIPS_BOOSTER_PORT;
    String OPENSIPS_CONFIG_UDP_SERVER = "cat /etc/opensips/opensips.cfg|grep listen=udp:" + IP_SERVER +":" + OPENSIPS_SERVER_PORT;
    String OPENSIPS_CONFIG_UDP_LOCALHOST = "cat /etc/opensips/opensips.cfg|grep listen=udp:127.0.0.1:" + OPENSIPS_SERVER_PORT;
    String OPENSIPS_CONFIG_AVP_LOCAL_IP = "cat /etc/opensips/opensips.cfg | grep '$avp(local_ip)=\"" + IP_SERVER + "\";'";
    String OPENSIPS_CONFIG_AVP_EXTERNAL_IP = "cat /etc/opensips/opensips.cfg | grep '$avp(external_ip)=\"" + IP_SERVER + "\";'";
    String OPENSIPS_CONFIG_TURN_MIN_PORT = "cat /etc/turnserver/turnserver.conf | grep min-port=" + OPENSIPS_TURN_PORT_MIN;
    String OPENSIPS_CONFIG_TURN_MAX_PORT = "cat /etc/turnserver/turnserver.conf | grep max-port=" + OPENSIPS_TURN_PORT_MAX;
    String OPENSIPS_STATUS = "systemctl status opensips | awk '/active/ && !/inactive/'";
}
