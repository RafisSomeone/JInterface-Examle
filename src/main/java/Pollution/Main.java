package Pollution;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.ericsson.otp.erlang.*;

import java.io.IOException;

public class Main extends Application {

    private OtpMbox box;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, OtpErlangExit, OtpErlangDecodeException {

        OtpNode myNode = new OtpNode("server8","erljava");

        box = myNode.createMbox("GUI");



        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        Button btn2 = new Button();
        btn.setText("Say 'Hello World'");
        final TextArea text = new TextArea();
        text.setMaxSize(100,10);
        text.setTranslateY(-100);
        TextArea text2 = new TextArea();
        text2.setMaxSize(100,10);
        text2.setTranslateY(-50);
        TextArea text3 = new TextArea();
        text3.setMaxSize(100,10);
        text3.setTranslateY(0);
        btn.setTranslateY(50);


        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {


                OtpErlangAtom atom = new OtpErlangAtom("request");


                OtpErlangObject[] reply = new OtpErlangObject[5];

                reply[0] = atom;

                reply[1] = new OtpErlangAtom("GUI");

                reply[2] = new OtpErlangAtom("server8@rafal-GL553VD");

                reply[3] = new OtpErlangAtom("addStation");

                OtpErlangObject[] tup = new OtpErlangObject[2];
                OtpErlangFloat x = new OtpErlangFloat((float) 12.3);

                OtpErlangFloat y = new OtpErlangFloat((float) 15.3);
                tup[0] = x;
                tup[1]= y;
                OtpErlangTuple location = new OtpErlangTuple(tup);

                OtpErlangObject[] para = new OtpErlangObject[]{new OtpErlangString("NAZWA"),location};

                OtpErlangTuple params = new OtpErlangTuple(para);

                reply[4] = location;

                OtpErlangTuple myTuple = new OtpErlangTuple(reply);
                box.send("pollutionServer", "server6@rafal-GL553VD", myTuple);
            }
        });


        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

//tu piszesz funckje

            }
        });



        StackPane root = new StackPane();

        root.getChildren().add(text);
        root.getChildren().add(text2);
        root.getChildren().add(text3);
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();



        new Thread(new Runnable() {
            public void run() {

                while (true){
                    try {
                        OtpErlangObject received = box.receive();

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
    }


}
