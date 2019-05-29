import com.ericsson.otp.erlang.*;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        OtpNode myOtpNode = new OtpNode("server", "mycookie");
        OtpMbox myOtpMbox = myOtpNode.createMbox("counter");



    }

}
