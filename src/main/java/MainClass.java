import HelperClasses.SSHManager;
import Pages.Providers.ProvidersPage;
import com.sun.corba.se.spi.ior.IdentifiableFactory;

import static DataTests.Providers.DataProvidersDX500.*;

public class MainClass {
    //private static ProvidersPage providersPage = new ProvidersPage();
     /*public static void main(String[] args){
         System.out.println("BOOSTER");
         System.out.println("boosterDBIP: " + SSHManager.isCheckQuerySSH(boosterDBIP));
         System.out.println("boosterDBPort: " + SSHManager.isCheckQuerySSH(boosterDBPort));
         System.out.println("boosterAdapterName: " + SSHManager.isCheckQuerySSH(boosterAdapterName));
         System.out.println("boosterStation: " + SSHManager.isCheckQuerySSH(boosterStation));
         System.out.println("boosterIP: " + SSHManager.isCheckQuerySSH(boosterIP));
         System.out.println("boosterPort: " + SSHManager.isCheckQuerySSH(boosterPort));
         System.out.println("SIP");
         System.out.println("sipIP: " + SSHManager.isCheckQuerySSH(sipIP));
         System.out.println("sipPort: " + SSHManager.isCheckQuerySSH(sipPort));
         System.out.println("sipDBIP: " + SSHManager.isCheckQuerySSH(sipDBIP));
         System.out.println("sipDPort: " + SSHManager.isCheckQuerySSH(sipDPort));
         System.out.println("sipSGI: " + SSHManager.isCheckQuerySSH(sipSGI));
         System.out.println("sipSGP: " + SSHManager.isCheckQuerySSH(sipSGP));
         System.out.println("sipMGI: " + SSHManager.isCheckQuerySSH(sipMGI));
         System.out.println("sipMGP: " + SSHManager.isCheckQuerySSH(sipMGP));
         System.out.println("SMG0_Enable: " + SSHManager.isCheckQuerySSH(SMG0_Enable));
         System.out.println("SMG0_SG0DEV: " + SSHManager.isCheckQuerySSH(SMG0_SG0DEV));
         System.out.println("SMG0_MG0DEV: " + SSHManager.isCheckQuerySSH(SMG0_MG0DEV));
         System.out.println("SIP Pult");

     }*/

    interface Iface{
       default boolean isCheck(){ return false;}
    }

    public static class A implements Iface {

        public A(){}

        //public boolean isCheck(){ return true; }
    }

    public static class B {
        public static void main(String[] args){
            A a = new A();
            System.out.println(a.isCheck());
         }
    }

}
