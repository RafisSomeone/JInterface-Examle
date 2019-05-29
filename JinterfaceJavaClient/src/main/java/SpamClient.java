import com.ericsson.otp.erlang.*;

import java.io.IOException;
import java.util.Date;

public class SpamClient {
    public static void main(String[] args) {
        try {
            OtpNode myNode = new OtpNode("client","erljava");
            OtpMbox myMbox = myNode.createMbox("sendingspam");

            OtpErlangString otpErlangString;
            OtpErlangObject[] reply;

            OtpErlangTuple myTuple;

            while(true) {
                Thread.sleep(2000);
                otpErlangString = new OtpErlangString(String.valueOf(new Date()));
                reply = new OtpErlangObject[]{myMbox.self(),otpErlangString};
                myTuple = new OtpErlangTuple(reply);
                myMbox.send("counterserver", "server@micha-HP-ProBook-650-G1", myTuple);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
