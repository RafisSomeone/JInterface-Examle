import com.ericsson.otp.erlang.*;

import java.io.IOException;
import java.util.Date;

public class SpamClient {
    public static void main(String[] args) {
        try {
            OtpNode myNode = new OtpNode("client","erljava");
            final OtpMbox myMbox = myNode.createMbox("sendingspam");

            new Thread(new Runnable() {
                public void run() {
                    OtpErlangString otpErlangString;
                    OtpErlangObject[] reply;
                    OtpErlangTuple myTuple;
                    while(true) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        otpErlangString = new OtpErlangString(String.valueOf(new Date()));
                        reply = new OtpErlangObject[]{myMbox.self(),otpErlangString};
                        myTuple = new OtpErlangTuple(reply);
                        myMbox.send("counterserver", "server@micha-HP-ProBook-650-G1", myTuple);
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
