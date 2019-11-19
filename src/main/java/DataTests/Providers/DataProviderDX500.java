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
    String controlPortBooster = "1233";

    /***** Параметры настройки Пульт *****/
    String serverPult = "gw-pult";
    String SMGPult = "1";
    String IPConverterPult = "172.22.50.112";
    String configPult = serverPult + ".cfg";
    String controlPortPult = "1230";

    /***** Параметры настройки SIP Пульт *****/
    String serverSIPPult = "sv-ipult";
    String SMGSIPPult = "2";
    String IPConverterSIPPult = "172.22.50.113";
    String configSipPult = serverSIPPult + ".cfg";
    String controlPortSIPPult = "1234";

    /***** Параметры занятости ******/
    String serverBusy = "sv-num_st";
    String configBusy = serverBusy + ".cfg";
    String controlPortBusy = "2220";

    /***** Прооверка конфигурационных файлов на сервере *****/

    /***** Проверка конфигурации сервера Ассистентов *****/
    String boosterDBIP = "grep -E 'db_ip=127.0.0.1' /etc/pult-soft/" + configBooster;
    String boosterDBPort = "grep -E 'db_port=1215' /etc/pult-soft/" + configBooster;
    String boosterAdapterName = "grep -E 'adapter_name=" + adapterName + "' /etc/pult-soft/" + configBooster;
    String boosterStation = "grep -E 'station=auto' /etc/pult-soft/" + configBooster;
    String boosterIP = "grep -E 'ip=0.0.0.0' /etc/pult-soft/" + configBooster;
    String boosterPort = "grep -E 'port=1223' /etc/pult-soft/" + configBooster;
    String boosterConnectStation = "echo s | nc l " + controlPortBooster + " | grep 'dl_ready: establish'";

    /***** Проверка конфигурации сервера Пультов *****/
    String pultContactIP = "grep -E 'contact_ip=127.0.0.1' /etc/pult-soft/" + configPult;
    String pultContactPort = "grep -E 'contact_port=1215' /etc/pult-soft/" + configPult;
    String pultSGP = "grep -E 'sig_gate_port=230" + SMGPult + "' /etc/pult-soft/" + configPult;
    String pultIP = "grep -E 'ip=0.0.0.0' /etc/pult-soft/" + configPult;
    String pultPort = "grep -E 'port=1220' /etc/pult-soft/" + configPult;
    String pultMGP = "grep -E 'media_gate_port=235" + SMGPult + "' /etc/pult-soft/" + configPult;
    String SMGPult_Enable = "grep -E 'SMG" + SMGPult + "_ENABLE=1' /etc/smg.cfg";
    String SMGPult_SGDEV = "grep -E \"SG" + SMGPult + "_DEV='" + urlServer + ":232" + SMGPult + "->" + IPConverterPult + ":2300'\" /etc/smg.cfg";
    String SMGPult_MGDEV = "grep -E \"MG" + SMGPult + "_DEV='" + urlServer + ":236" + SMGPult + "->" + IPConverterPult + ":2350|" + urlServer + ":232" + SMGPult + "'\" /etc/smg.cfg";
    String pultConnectStation = "echo s | nc l " + controlPortPult + " | grep 'sg_l3_state: lapd_establish'";

    /***** Проверка конфигурации сервера SIP *****/
    String sipIP = "cat /etc/pult-soft/" + configSIP  + " | grep 'ip=127.0.0.1'|grep -v 'db_ip\\|sig_gate\\|media_gate'";
    String sipPort = "grep -E 'port=5064' /etc/pult-soft/" + configSIP;
    String sipDBIP = "grep -E 'db_ip=127.0.0.1' /etc/pult-soft/" + configSIP;
    String sipDPort = "grep -E 'db_port=1215' /etc/pult-soft/" + configSIP;
    String sipSGI = "grep -E 'sig_gate_ip=127.0.0.1' /etc/pult-soft/" + configSIP;
    String sipSGP = "grep -E 'sig_gate_port=2300' /etc/pult-soft/" + configSIP;
    String sipMGI = "grep -E 'media_gate_ip=127.0.0.1' /etc/pult-soft/" + configSIP;
    String sipMGP = "grep -E 'media_gate_port=2350' /etc/pult-soft/" + configSIP;
    String SMGSIP_Enable = "grep -E 'SMG" + SMGSIP + "_ENABLE=1' /etc/smg.cfg";
    String SMGSIP_SGDEV = "grep -E \"SG" + SMGSIP + "_DEV='" + urlServer + ":232" + SMGSIP + "->" + IPConverterSIP + ":2300'\" /etc/smg.cfg";
    String SMGSIP_MGDEV = "grep -E \"MG" + SMGSIP + "_DEV='" + urlServer + ":236" + SMGSIP + "->" + IPConverterSIP + ":2350|" + urlServer + ":232" + SMGSIP + "'\" /etc/smg.cfg";

    /***** Проверка конфигурации сервера SIP-Пульт *****/
    String sipPultSGPort = "grep -E 'sg_port=2303' /etc/pult-soft/" + configSipPult;
    String sipPultIP = "grep -E 'ip=0.0.0.0' /etc/pult-soft/" + configSipPult;
    String sipPultPort = "grep -E 'port=1224' /etc/pult-soft/" + configSipPult;
    String sipPultDBIP = "grep -E 'db_ip=127.0.0.1' /etc/pult-soft/" + configSipPult;
    String sipPultDBPort = "grep -E 'db_port=1215' /etc/pult-soft/" + configSipPult;
    String sipPultMGPort = "grep -E 'mg_port=2353' /etc/pult-soft/" + configSipPult;
    String sipPultRingin = "grep -E 'ring=Ring1' /etc/pult-soft/" + configSipPult;
    String SMGSIPPult_Enable = "grep -E 'SMG" + SMGSIPPult + "_ENABLE=1' /etc/smg.cfg";
    String SMGSIPPult_SGDEV = "grep -E \"SG" + SMGSIPPult + "_DEV='" + urlServer + ":232" + SMGSIPPult + "->" + IPConverterSIPPult + ":2300'\" /etc/smg.cfg";
    String SMGSIPPult_MGDEV = "grep -E \"MG" + SMGSIPPult + "_DEV='" + urlServer + ":236" + SMGSIPPult + "->" + IPConverterSIPPult + ":2350|" + urlServer + ":232" + SMGSIPPult + "'\" /etc/smg.cfg";
    String SIPPultConnectStation = "echo s | nc l " + controlPortSIPPult + " | grep 'l3_state              : established (connected to Station)'";

    /***** Проверка конфигурации сервера занятости *****/
    String busyAdapterName = "grep -E 'adapter_name=" + adapterName + "' /etc/pult-soft/" + configBusy;
    String busyConnectStation = "echo s | nc l " + controlPortBusy + " | grep 'dl_255_ready: establish'";

    /***** Проверяем, что в БД mysql записался маршрут вызова *****/
    String mysqlDialplan = "mysql -hlocalhost -uroot -p123456 -e 'select * from dialplan' opensips | grep " + dialplanDX500;
}
