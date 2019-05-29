package Pollution;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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



        primaryStage.setTitle("Pollution Adder");
        Button btn = new Button();
        Button btn2 = new Button();
        btn.setText("ADD STATION");
        final TextArea text = new TextArea();
        text.setMaxSize(100,10);
        text.setTranslateY(-100);
        final TextArea text2 = new TextArea();
        text2.setMaxSize(100,10);
        text2.setTranslateY(-50);
        final TextArea text3 = new TextArea();
        text3.setMaxSize(100,10);
        text3.setTranslateY(0);
        btn.setTranslateY(50);
        btn2.setTranslateY(90);
        btn2.setText("PRINT");
        Text label1 = new Text("X:");
        Text label2 = new Text("Y:");
        Text label3 = new Text("Name:");
        label1.setTranslateY(-100);
        label2.setTranslateY(-50);
        label3.setTranslateY(-0);
        label1.setTranslateX(-65);
        label2.setTranslateX(-65);
        label3.setTranslateX(-80);




        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {


                OtpErlangAtom atom = new OtpErlangAtom("request");


                OtpErlangObject[] reply = new OtpErlangObject[5];

                reply[0] = atom;

                reply[1] = new OtpErlangAtom("GUI");

                reply[2] = new OtpErlangAtom("server8@rafal-GL553VD");

                reply[3] = new OtpErlangAtom("addStation");

                if(text.getText().equals("") || text2.getText().equals("") ||text3.getText().equals("") )
                {
                    System.out.println("Uzupełnij formularz przed wysłaniem");
                    return;

                }

                OtpErlangObject[] tup = new OtpErlangObject[2];
                OtpErlangFloat x = new OtpErlangFloat(Float.parseFloat(text.getText()));
                OtpErlangFloat y = new OtpErlangFloat(Float.parseFloat(text2.getText()));
                tup[0] = x;
                tup[1]= y;
                OtpErlangTuple location = new OtpErlangTuple(tup);
                OtpErlangString name = new OtpErlangString(text3.getText());
                OtpErlangObject[] para = new OtpErlangObject[2];
                para[0] = name;
                para[1] = location;
                System.out.println(name + " " +location);
                OtpErlangTuple params = new OtpErlangTuple(para);

                reply[4] = params;

                OtpErlangTuple myTuple = new OtpErlangTuple(reply);
                box.send("pollutionServer", "server6@rafal-GL553VD", myTuple);
            }
        });


        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                OtpErlangAtom atom = new OtpErlangAtom("request");


                OtpErlangObject[] reply = new OtpErlangObject[4];

                reply[0] = atom;

                reply[1] = new OtpErlangAtom("GUI");

                reply[2] = new OtpErlangAtom("server8@rafal-GL553VD");

                reply[3] = new OtpErlangAtom("print");

                OtpErlangTuple myTuple = new OtpErlangTuple(reply);
                box.send("pollutionServer", "server6@rafal-GL553VD", myTuple);

            }
        });



        StackPane root = new StackPane();

        root.getChildren().add(text);
        root.getChildren().add(text2);
        root.getChildren().add(text3);
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        root.getChildren().add(label1);
        root.getChildren().add(label2);
        root.getChildren().add(label3);

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
