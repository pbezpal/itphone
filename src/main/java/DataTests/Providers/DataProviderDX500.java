package DataTests.Providers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static DataTests.DataLogin.urlServer;

@Retention(RetentionPolicy.RUNTIME)
public @interface DataProviderDX500 {

    /***** Параметры настройки провайдера DX *****/
    String DX500 = "DX500";
    String dialplanDX500 = "5XXX";

    /***** Параметры настройки SIP(абон)-DX *****/
    String nameSIP = "gw-q";
    String serverSIP = "gw-sip";
    String serverSIPModule = serverSIP + "-dx";
    String SMGSIP = "0";
    String IPConverterSIP = "172.22.50.111";
    String configSIP = serverSIPModule + ".cfg";

    /***** Параметры настрйоки Ассистента *****/
    String serverBooster = "gw-booster";
    String dbIP = "127.0.0.1";
    String adapterName = "eth0";
    String numberStation = "auto";
    String gateIP = "0.0.0.0";
    String gatePort = "1223";
    String configBooster = serverBooster + ".cfg";

    /***** Параметры настройки Пульт *****/
    String serverPult = "gw-pult";
    String SMGPult = "1";
    String IPConverterPult = "172.22.50.112";
    String configPult = serverPult + ".cfg";

    /***** Параметры настройки SIP Пульт *****/
    String serverSIPPult = "sv-ipult";
    String SMGSIPPult = "2";
    String IPConverterSIPPult = "172.22.50.113";
    String configSipPult = serverSIPPult + ".cfg";

    /***** Прооверка конфигурационных файлов на сервере *****/

    /***** Проверка конфигурации сервера Ассистентов *****/
    String boosterDBIP = "grep -E 'db_ip=127.0.0.1' /etc/pult-soft/" + configBooster;
    String boosterDBPort = "grep -E 'db_port=1215' /etc/pult-soft/" + configBooster;
    String boosterAdapterName = "grep -E 'adapter_name=" + adapterName + "' /etc/pult-soft/" + configBooster;
    String boosterStation = "grep -E 'station=auto' /etc/pult-soft/" + configBooster;
    String boosterIP = "grep -E 'ip=0.0.0.0' /etc/pult-soft/" + configBooster;
    String boosterPort = "grep -E 'port=1223' /etc/pult-soft/" + configBooster;

    /***** Проверка конфигурации сервера Пультов *****/
    String pultContactIP = "grep -E 'contact_ip=127.0.0.1' /etc/pult-soft/" + configPult;
    String pultContactPort = "grep -E 'contact_port=1215' /etc/pult-soft/" + configPult;
    String pultSGP = "grep -E 'sig_gate_port=2301' /etc/pult-soft/" + configPult;
    String pultIP = "grep -E 'ip=0.0.0.0' /etc/pult-soft/" + configPult;
    String pultPort = "grep -E 'port=1220' /etc/pult-soft/" + configPult;
    String pultMGP = "grep -E 'media_gate_port=2351' /etc/pult-soft/" + configPult;
    String SMG1_Enable = "grep -E 'SMG1_ENABLE=1' /etc/smg.cfg";
    String SMG1_SG1DEV = "grep -E \"SG1_DEV='" + urlServer + ":2321->" + IPConverterPult + ":2300'\" /etc/smg.cfg";
    String SMG1_MG1DEV = "grep -E \"MG1_DEV='" + urlServer + ":2361->" + IPConverterPult + ":2350|10.10.11.129:2321'\" /etc/smg.cfg";

    /***** Проверка конфигурации сервера SIP *****/
    String sipIP = "cat /etc/pult-soft/" + configSIP  + " | grep 'ip=127.0.0.1'|grep -v 'db_ip\\|sig_gate\\|media_gate'";
    String sipPort = "grep -E 'port=5064' /etc/pult-soft/" + configSIP;
    String sipDBIP = "grep -E 'db_ip=127.0.0.1' /etc/pult-soft/" + configSIP;
    String sipDPort = "grep -E 'db_port=1215' /etc/pult-soft/" + configSIP;
    String sipSGI = "grep -E 'sig_gate_ip=127.0.0.1' /etc/pult-soft/" + configSIP;
    String sipSGP = "grep -E 'sig_gate_port=2300' /etc/pult-soft/" + configSIP;
    String sipMGI = "grep -E 'media_gate_ip=127.0.0.1' /etc/pult-soft/" + configSIP;
    String sipMGP = "grep -E 'media_gate_port=2350' /etc/pult-soft/" + configSIP;
    String SMG0_Enable = "grep -E 'SMG0_ENABLE=1' /etc/smg.cfg";
    String SMG0_SG0DEV = "grep -E \"SG0_DEV='" + urlServer + ":2320->" + IPConverterSIP + ":2300'\" /etc/smg.cfg";
    String SMG0_MG0DEV = "grep -E \"MG0_DEV='" + urlServer + ":2360->" + IPConverterSIP + ":2350|10.10.11.129:2320'\" /etc/smg.cfg";

    /***** Проверка конфигурации сервера SIP-Пульт *****/
    String sipPultSGPort = "grep -E 'sg_port=2303' /etc/pult-soft/" + configSipPult;
    String sipPultIP = "grep -E 'ip=0.0.0.0' /etc/pult-soft/" + configSipPult;
    String sipPultPort = "grep -E 'port=1224' /etc/pult-soft/" + configSipPult;
    String sipPultDBIP = "grep -E 'db_ip=127.0.0.1' /etc/pult-soft/" + configSipPult;
    String sipPultDBPort = "grep -E 'db_port=1215' /etc/pult-soft/" + configSipPult;
    String sipPultMPPort = "grep -E 'mg_port=2353' /etc/pult-soft/" + configSipPult;
    String sipPultRingin = "grep -E 'ring=Ring1' /etc/pult-soft/" + configSipPult;
    String MSG3_Enable = "grep -E 'SMG3_ENABLE=1' /etc/smg.cfg";
    String SMG3_SG3DEV = "grep -E \"SG3_DEV='" + urlServer + ":2323->" + IPConverterSIPPult + ":2300'\" /etc/smg.cfg";
    String SMG3_MG3DEV = "grep -E \"MG3_DEV='" + urlServer + ":2363->" + IPConverterSIPPult + ":2350|10.10.11.129:2323'\" /etc/smg.cfg";

    /***** Проверяем, что в БД mysql записался маршрут вызова *****/
    String mysqlDialplan = "mysql -hlocalhost -uroot -p123456 -e 'select * from dialplan' opensips | grep " + dialplanDX500;
}
