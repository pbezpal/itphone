import HelperClasses.SSHManager;

public class main {

    static String command = "pgrep opensips";

    public static void main(String[] args){
        System.out.println(SSHManager.isCheckQuerySSH(command));
    }

}
