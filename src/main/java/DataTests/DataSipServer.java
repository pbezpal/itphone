package DataTests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static DataTests.DataLogin.urlServer;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataSipServer {

    /***** Параметры SIP сервера *****/

    String linkSipServerPage = "SIP-сервер";
    String sipServerPort = "5060";
    String sipBoosterPort = "5160";
    String turnPortMin = "49152";
    String turnPortMax = "49182";

    /***** Команды проверки конфигураци SIP сервера *****/
    String commandCheckSettingsDXTP = "cat /etc/opensips/opensips.cfg|grep listen=dxtp:" + urlServer +":" + sipBoosterPort;
    String commandCheckSettingsUDPServer = "cat /etc/opensips/opensips.cfg|grep listen=udp:" + urlServer +":" + sipServerPort;
    String commandCheckSettingsUDPLocalhost = "cat /etc/opensips/opensips.cfg|grep listen=udp:127.0.0.1:" + sipServerPort;
    String commandCheckAVPLocalIP = "cat /etc/opensips/opensips.cfg | grep '$avp(local_ip)=\"" + urlServer + "\";'";
    String commandCheckAVPExternalIP = "cat /etc/opensips/opensips.cfg | grep '$avp(external_ip)=\"" + urlServer + "\";'";
    String commandCheckSettingsTurnMinPort = "cat /etc/turnserver/turnserver.conf | grep min-port=" + turnPortMin;
    String commandCheckSettingsTurnMaxPort = "cat /etc/turnserver/turnserver.conf | grep max-port=" + turnPortMax;
    String commandStatusSIPServer = "systemctl status opensips | awk '/active/ && !/inactive/'";
}
