package HelperClasses;

import DataTests.DataLogin;
import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public  class SSHManager {

    private static String server = DataLogin.urlServer;
    private static String login = DataLogin.sshLogin;
    private static String password = DataLogin.sshPassword;
    private static JSch ssh = new JSch();

    public SSHManager(){}

    public SSHManager(String server, String login, String password){
        this.server = server;
        this.login = login;
        this.password = password;
    }

    public static boolean isCheckQuerySSH(String command){
        try {
            Session s = ssh.getSession(login, server, 22);
            s.setPassword(password);
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
            String line;
            while ((line = reader.readLine()) != null) {
            }

            ce.disconnect();
            s.disconnect();

            if(ce.getExitStatus() == 0) return true;
            else return false;

        } catch (JSchException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
