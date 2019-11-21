package HelperClasses;

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static DataTests.LOGIN.*;

public class SSHManager {

    private static JSch ssh = new JSch();
    private static  String line;

    public SSHManager(){}

    public static boolean isCheckQuerySSH(String command){
        try {
            Session s = ssh.getSession(LOGIN_ADMIN_SSH, IP_SERVER, 22);
            s.setPassword(LOGIN_PASSWORD_SSH);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            s.setConfig(config);
            s.connect();

            Channel c = s.openChannel("exec");
            ChannelExec ce = (ChannelExec) c;
            ce.setCommand(command);
            ce.connect();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(ce.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            line = reader.readLine();

            ce.disconnect();
            s.disconnect();

            if(line != null) return true;
            return false;

        } catch (JSchException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
