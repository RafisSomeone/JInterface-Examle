package ClientJavaErlangServer;

import com.ericsson.otp.erlang.*;

import java.io.IOException;
import java.util.Date;

public class SpamClient {
    public static void main(String[] args) {
        try {
            final OtpNode myNode = new OtpNode("client","erljava");
            final OtpMbox myMbox = myNode.createMbox("sendingspam");

            new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                       OtpErlangString otpErlangString = new OtpErlangString(String.valueOf(new Date()));

                        OtpErlangObject[] reply = new OtpErlangObject[3];

                        reply[0] = new OtpErlangAtom(myMbox.getName());

                        reply[1] = new OtpErlangAtom(String.valueOf(myNode));

                        reply[2] = otpErlangString;

                        OtpErlangTuple myTuple = new OtpErlangTuple(reply);
                        myMbox.send("server5", "server2@rafal-GL553VD", myTuple);
                    }
                }
            }).start();



            new Thread(new Runnable() {
                public void run() {
                    OtpErlangObject received;
                    while (true){
                        try {
                            received = myMbox.receive();

                            System.out.println("Received: ");
                            System.out.println(received);

                        } catch (OtpErlangExit otpErlangExit) {
                            otpErlangExit.printStackTrace();
                        } catch (OtpErlangDecodeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}