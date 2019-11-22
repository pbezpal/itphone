package DataTests.Providers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static DataTests.GET_DATA_TESTS.*;
import static DataTests.LOGIN.IP_SERVER;

public @interface PROVIDER_DX500 {

    /***** Параметры настройки провайдера DX *****/
    String DX500_TYPE_PROVIDER = getProperty("DX500_TYPE_PROVIDER");
    String DX500_DIALPLAN = getProperty("DX500_DIALPLAN");

    /***** Параметры настройки SIP(абон)-DX *****/
    String DX500_SIP_ABON_DX_HEADER_MODULE = "SIP(аб)-DXшлюз";
    String DX500_SIP_ABON_DX = getProperty("DX500_SIP_ABON_DX");
    String DX500_SIP_ABON_DX_OPTION = getProperty("DX500_SIP_ABON_DX_OPTION");
    String DX500_SIP_ABON_DX_SMG = getProperty("DX500_SIP_ABON_DX_SMG");
    String DX500_SIP_ABON_DX_IP_CONVERTER = getProperty("DX500_SIP_ABON_DX_IP_CONVERTER");
    String DX500_SIP_ABON_DX_CONFIG = getProperty("DX500_SIP_ABON_DX_CONFIG");

    /***** Параметры настрйоки Ассистента *****/
    String DX500_BOOSTER_HEAD_MODULE = "Ассистент";
    String DX500_BOOSTER = getProperty("DX500_BOOSTER");
    String DX500_BOOSTER_IP_DB = getProperty("DX500_BOOSTER_IP_DB");
    String DX500_BOOSTER_ADAPTER_NAME = getProperty("DX500_BOOSTER_ADAPTER_NAME");
    String DX500_BOOSTER_STATION = getProperty("DX500_BOOSTER_STATION");
    String DX500_BOOSTER_GATE_IP = getProperty("DX500_BOOSTER_GATE_IP");
    String DX500_BOOSTER_GATE_PORT = getProperty("DX500_BOOSTER_GATE_PORT");
    String DX500_BOOSTER_CONFIG = getProperty("DX500_BOOSTER_CONFIG");
    String DX500_BOOSTER_CONTROL_PORT = getProperty("DX500_BOOSTER_CONTROL_PORT");

    /***** Параметры настройки Пульт *****/
    String DX500_PULT_HEAD_MODULE = "Пульт";
    String DX500_PULT = getProperty("DX500_PULT");
    String DX500_PULT_SMG = getProperty("DX500_PULT_SMG");
    String DX500_PULT_CONVERTER_IP = getProperty("DX500_PULT_CONVERTER_IP");
    String DX500_PULT_CONFIG = getProperty("DX500_PULT_CONFIG");
    String DX500_PULT_CONTROL_PORT = getProperty("DX500_PULT_CONTROL_PORT");

    /***** Параметры настройки SIP Пульт *****/
    String DX500_SIP_PULT_HEAD_MODULE = "SIP-Пульт";
    String DX500_SIP_PULT = getProperty("DX500_SIP_PULT");
    String DX500_SIP_PULT_SMG = getProperty("DX500_SIP_PULT_SMG");
    String DX500_SIP_PULT_CONVERTER_IP = getProperty("DX500_SIP_PULT_CONVERTER_IP");
    String DX500_SIP_PULT_CONFIG = getProperty("DX500_SIP_PULT_CONFIG");
    String DX500_SIP_PULT_CONTROL_PORT = getProperty("DX500_SIP_PULT_CONTROL_PORT");

    /***** Параметры занятости ******/
    String DX500_BUSY_HEAD_MODULE = "Занятость Eth";
    String DX500_BUSY = getProperty("DX500_BUSY");
    String DX500_BUSY_CONFIG = getProperty("DX500_BUSY_CONFIG");
    String DX500_BUSY_CONTROL_PORT = getProperty("DX500_BUSY_CONTROL_PORT");

    /***** Прооверка конфигурационных файлов на сервере *****/

    /***** Проверка конфигурации сервера Ассистентов *****/
    String BOOSTER_CONFIG_DB_IP = "grep -E 'db_ip=127.0.0.1' " + DX500_BOOSTER_CONFIG;
    String BOOSTER_CONFIG_DB_PORT = "grep -E 'db_port=1215' " + DX500_BOOSTER_CONFIG;
    String BOOSTER_CONFIG_ADAPTER_NAME = "grep -E 'adapter_name=" + DX500_BOOSTER_ADAPTER_NAME + "' " + DX500_BOOSTER_CONFIG;
    String BOOSTER_CONFIG_STATION = "grep -E 'station=auto' " + DX500_BOOSTER_CONFIG;
    String BOOSTER_CONFIG_IP = "grep -E 'ip=0.0.0.0' " + DX500_BOOSTER_CONFIG;
    String BOOSTER_CONFIG_PORT = "grep -E 'port=1223' " + DX500_BOOSTER_CONFIG;
    String BOOSTER_CONNECT_STATION = "echo s | nc l " + DX500_BOOSTER_CONTROL_PORT + " | grep 'dl_ready: establish'";
    String BOOSTER_CONNECT_CONTACTS = "echo s | nc l " + DX500_BOOSTER_CONTROL_PORT + " | grep 'db: logined'";

    /***** Проверка конфигурации сервера Пультов *****/
    String PULT_CONFIG_CONTACT_IP = "grep -E 'contact_ip=127.0.0.1' " + DX500_PULT_CONFIG;
    String PULT_CONFIG_CONTACT_PORT = "grep -E 'contact_port=1215' " + DX500_PULT_CONFIG;
    String PULT_CONFIG_SIG_GATE_PORT = "grep -E 'sig_gate_port=230" + DX500_PULT_SMG + "' " + DX500_PULT_CONFIG;
    String PULT_CONFIG_IP = "grep -E 'ip=0.0.0.0' " + DX500_PULT_CONFIG;
    String PULT_CONFIG_PORT = "grep -E 'port=1220' " + DX500_PULT_CONFIG;
    String PULT_CONFIG_MEDIA_GATE_PORT = "grep -E 'media_gate_port=235" + DX500_PULT_SMG + "' " + DX500_PULT_CONFIG;
    String PULT_SMG_ENABLE = "grep -E 'SMG" + DX500_PULT_SMG + "_ENABLE=1' /etc/smg.cfg";
    String PULT_SMG_SGDEV = "grep -E \"SG" + DX500_PULT_SMG + "_DEV='" + IP_SERVER + ":232" + DX500_PULT_SMG + "->" + DX500_PULT_CONVERTER_IP + ":2300'\" /etc/smg.cfg";
    String PULT_SMG_MGDEV = "grep -E \"MG" + DX500_PULT_SMG + "_DEV='" + IP_SERVER + ":236" + DX500_PULT_SMG + "->" + DX500_PULT_CONVERTER_IP + ":2350|" + IP_SERVER + ":232" + DX500_PULT_SMG + "'\" /etc/smg.cfg";
    String PULT_CONNECT_STATION = "echo s | nc l " + DX500_PULT_CONTROL_PORT + " | grep 'sg_l3_state: lapd_establish'";
    String PULT_CONNECT_CONTACTS = "echo s | nc l " + DX500_PULT_CONTROL_PORT + " | grep 'db: logined'";

    /***** Проверка конфигурации сервера SIP *****/
    String SIP_ABON_DX_CONFIG_IP = "cat " + DX500_SIP_ABON_DX_CONFIG + " | grep 'ip=127.0.0.1'|grep -v 'db_ip\\|sig_gate\\|media_gate'";
    String SIP_ABON_DX_CONFIG_PORT = "grep -E 'port=5064' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_DB_IP = "grep -E 'db_ip=127.0.0.1' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_DB_PORT = "grep -E 'db_port=1215' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_SIG_GATE_IP = "grep -E 'sig_gate_ip=127.0.0.1' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_SIG_GATE_PORT = "grep -E 'sig_gate_port=2300' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_MEDIA_GATE_IP = "grep -E 'media_gate_ip=127.0.0.1' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_MEDIA_GATE_PORT = "grep -E 'media_gate_port=2350' " + DX500_SIP_ABON_DX_CONFIG;
    String SIP_ABON_DX_CONFIG_SMG_ENABLE = "grep -E 'SMG" + DX500_SIP_ABON_DX_SMG + "_ENABLE=1' /etc/smg.cfg";
    String SIP_ABON_DX_CONFIG_SGDEV = "grep -E \"SG" + DX500_SIP_ABON_DX_SMG + "_DEV='" + IP_SERVER + ":232" + DX500_SIP_ABON_DX_SMG + "->" + DX500_SIP_ABON_DX_IP_CONVERTER + ":2300'\" /etc/smg.cfg";
    String SIP_ABON_DX_CONFIG_MGDEV = "grep -E \"MG" + DX500_SIP_ABON_DX_SMG + "_DEV='" + IP_SERVER + ":236" + DX500_SIP_ABON_DX_SMG + "->" + DX500_SIP_ABON_DX_IP_CONVERTER + ":2350|" + IP_SERVER + ":232" + DX500_SIP_ABON_DX_SMG + "'\" /etc/smg.cfg";

    /***** Проверка конфигурации сервера SIP-Пульт *****/
    String SIP_PULT_CONFIG_SG_PORT = "grep -E 'sg_port=2303' " + DX500_SIP_PULT_CONFIG;
    String SIP_PULT_CONFIG_IP = "grep -E 'ip=0.0.0.0' " + DX500_SIP_PULT_CONFIG;
    String SIP_PULT_CONFIG_PORT = "grep -E 'port=1224' " + DX500_SIP_PULT_CONFIG;
    String SIP_PULT_CONFIG_DB_IP = "grep -E 'db_ip=127.0.0.1' " + DX500_SIP_PULT_CONFIG;
    String SIP_PULT_CONFIG_DB_PORT = "grep -E 'db_port=1215' " + DX500_SIP_PULT_CONFIG;
    String SIP_PULT_CONFIG_MG_PORT = "grep -E 'mg_port=2353' " + DX500_SIP_PULT_CONFIG;
    String sipPultRingin = "grep -E 'ring=Ring1' " + DX500_SIP_PULT_CONFIG;
    String SIP_PULT_CONFIG_SMG_ENSBLE = "grep -E 'SMG" + DX500_SIP_PULT_SMG + "_ENABLE=1' /etc/smg.cfg";
    String SIP_PULT_CONFIG_SGDEV = "grep -E \"SG" + DX500_SIP_PULT_SMG + "_DEV='" + IP_SERVER + ":232" + DX500_SIP_PULT_SMG + "->" + DX500_SIP_PULT_CONVERTER_IP + ":2300'\" /etc/smg.cfg";
    String SIP_PULT_CONFIG_MGDEV = "grep -E \"MG" + DX500_SIP_PULT_SMG + "_DEV='" + IP_SERVER + ":236" + DX500_SIP_PULT_SMG + "->" + DX500_SIP_PULT_CONVERTER_IP + ":2350|" + IP_SERVER + ":232" + DX500_SIP_PULT_SMG + "'\" /etc/smg.cfg";
    String SIP_PULT_CONNECT_STATION = "echo s | nc l " + DX500_SIP_PULT_CONTROL_PORT + " | grep 'l3_state              : established (connected to Station)'";
    String SIP_PULT_CONNECT_CONTACTS = "echo s | nc l " + DX500_SIP_PULT_CONTROL_PORT + " | grep 'connection_to_contacts: yes'";
    String SIP_PULT_CONNECT_BUSY = "echo s | nc l "  + DX500_SIP_PULT_CONTROL_PORT +  " | grep 'connection_to_busy    : yes'";
    String SIP_PULT_STATUS_LEC = "echo s | nc l "  + DX500_SIP_PULT_CONTROL_PORT +  " | grep 'lec                   : enabled'";

    /***** Проверка конфигурации сервера занятости *****/
    String BUSY_CONFIG_ADAPTER_NAME = "grep -E 'adapter_name=" + DX500_BOOSTER_ADAPTER_NAME + "' " + DX500_BUSY_CONFIG;
    String BUSY_CONNECT_STATION = "echo s | nc l " + DX500_BUSY_CONTROL_PORT + " | grep 'dl_255_ready: establish'";

    /***** Проверяем, что в БД mysql записался маршрут вызова *****/
    String MYSQL_DX500_DIALPLAN = "mysql -hlocalhost -uroot -p123456 -e 'select * from dialplan' opensips | awk '/" + DX500_DIALPLAN + "/'";
}
