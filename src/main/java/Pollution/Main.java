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
        Button btn3 = new Button();
        btn3.setText("GET NORMS");
        Button btn4 = new Button();
        btn4.setText("ADD VALUE");
        Button btn6 = new Button();
        btn6.setText("STOP SERVER");
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
        btn3.setTranslateY(130);
        btn4.setTranslateY(170);
        btn6.setTranslateY(210);
        btn2.setText("PRINT");
        Text label1 = new Text("X:");
        Text label2 = new Text("Y:");
        Text label3 = new Text("Name:");
        label1.setTranslateY(-100);
        label2.setTranslateY(-50);
        label3.setTranslateY(-0);
        label1.setTranslateX(-130);
        label2.setTranslateX(-130);
        label3.setTranslateX(-145);

        Text label4 = new Text("S:");
        Text label5 = new Text("M:");
        Text label6 = new Text("H:");

        label4.setTranslateY(-150);
        label5.setTranslateY(-200);
        label6.setTranslateY(-250);
        label4.setTranslateX(-130);
        label5.setTranslateX(-130);
        label6.setTranslateX(-130);


        Text label7 = new Text("D:");
        Text label8 = new Text("M:");
        Text label9 = new Text("Y:");

        label7.setTranslateY(-150);
        label8.setTranslateY(-200);
        label9.setTranslateY(-250);
        label7.setTranslateX(50);
        label8.setTranslateX(50);
        label9.setTranslateX(50);

        Text label10 = new Text("Type:");
        Text label11 = new Text("LvL:");

        label10.setTranslateY(-100);
        label11.setTranslateY(-50);
        label10.setTranslateX(35);
        label11.setTranslateX(35);

        final TextArea year = new TextArea();

        final TextArea month = new TextArea();

        final TextArea day = new TextArea();


        final TextArea h = new TextArea();

        final TextArea m = new TextArea();

        final TextArea s = new TextArea();


        final TextArea type = new TextArea();


        final TextArea lvl = new TextArea();

        year.setMaxSize(100,10);
        year.setTranslateY(-250);

        month.setMaxSize(100,10);
        month.setTranslateY(-200);

        day.setMaxSize(100,10);
        day.setTranslateY(-150);

        h.setMaxSize(100,10);
        h.setTranslateY(-250);

        m.setMaxSize(100,10);
        m.setTranslateY(-200);

        s.setMaxSize(100,10);
        s.setTranslateY(-150);

        type.setMaxSize(100,10);
        type.setTranslateY(-100);

        lvl.setMaxSize(100,10);
        lvl.setTranslateY(-50);
        int a = 120;
        year.setTranslateX(a);
        month.setTranslateX(a);
        day.setTranslateX(a);
        type.setTranslateX(a);
        lvl.setTranslateX(a);

        a = -60;
        h.setTranslateX(a);
        m.setTranslateX(a);
        s.setTranslateX(a);

        text.setTranslateX(a);
        text2.setTranslateX(a);
        text3.setTranslateX(a);










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
                OtpErlangDouble x = new OtpErlangDouble(Double.parseDouble(text.getText()));
                OtpErlangDouble y = new OtpErlangDouble(Double.parseDouble(text2.getText()));
                tup[0] = x;
                tup[1]= y;
                OtpErlangTuple location = new OtpErlangTuple(tup);
                OtpErlangString name = new OtpErlangString(text3.getText());
                OtpErlangObject[] para = new OtpErlangObject[2];
                para[0] = name;
                para[1] = location;

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

        btn3.setOnAction(new EventHandler<ActionEvent>() {
            //getNorms
            @Override
            public void handle(ActionEvent event) {

                OtpErlangAtom atom = new OtpErlangAtom("request");


                OtpErlangObject[] reply = new OtpErlangObject[5];

                reply[0] = atom;

                reply[1] = new OtpErlangAtom("GUI");

                reply[2] = new OtpErlangAtom("server8@rafal-GL553VD");

                reply[3] = new OtpErlangAtom("getNorms");

                reply[4] = new OtpErlangAtom("getNorms");

                OtpErlangTuple myTuple = new OtpErlangTuple(reply);

                box.send("pollutionServer", "server6@rafal-GL553VD", myTuple);
            }
        });

        btn4.setOnAction(new EventHandler<ActionEvent>() {
            //addValue
            @Override
            public void handle(ActionEvent event) {

                OtpErlangAtom atom = new OtpErlangAtom("request");

                if(text.getText().equals("") || text2.getText().equals("") ||year.getText().equals("")||month.getText().equals("")||day.getText().equals("")||h.getText().equals("")||m.getText().equals("")||s.getText().equals("")||type.getText().equals("")||lvl.getText().equals("") )
                {
                    System.out.println("Uzupełnij formularz przed wysłaniem");
                    return;

                }


                OtpErlangObject[] reply = new OtpErlangObject[5];

                reply[0] = atom;

                reply[1] = new OtpErlangAtom("GUI");

                reply[2] = new OtpErlangAtom("server8@rafal-GL553VD");

                reply[3] = new OtpErlangAtom("addValue");

                OtpErlangObject[] tmp = new OtpErlangObject[2];

                OtpErlangDouble x = new OtpErlangDouble(Double.parseDouble(text.getText()));

                OtpErlangDouble y = new OtpErlangDouble(Double.parseDouble(text2.getText()));

                tmp[0] = x;
                tmp[1]= y;

                OtpErlangTuple location = new OtpErlangTuple(tmp);

                OtpErlangObject[] tupY = new OtpErlangObject[]{new OtpErlangInt(Integer.parseInt(year.getText())),new OtpErlangInt(Integer.parseInt(month.getText())),new OtpErlangInt(Integer.parseInt(day.getText()))};
                OtpErlangObject[] tupT = new OtpErlangObject[]{new OtpErlangInt(Integer.parseInt(h.getText())),new OtpErlangInt(Integer.parseInt(m.getText())),new OtpErlangInt(Integer.parseInt(s.getText()))};
                OtpErlangObject[] tup2 = new OtpErlangObject[]{new OtpErlangTuple(tupY), new OtpErlangTuple(tupT)};

                OtpErlangTuple date = new OtpErlangTuple(tup2);
                OtpErlangObject[] para = new OtpErlangObject[]{location,date, new OtpErlangString(type.getText()), new OtpErlangDouble(Double.parseDouble(lvl.getText()))};
                OtpErlangTuple last = new OtpErlangTuple(para);
                reply[4] = last;

                OtpErlangTuple myTuple = new OtpErlangTuple(reply);
                box.send("pollutionServer", "server6@rafal-GL553VD", myTuple);
            }
        });

        btn6.setOnAction(new EventHandler<ActionEvent>() {
            //stop
            @Override
            public void handle(ActionEvent event) {

                OtpErlangAtom atom = new OtpErlangAtom("request");

                OtpErlangObject[] reply = new OtpErlangObject[4];

                reply[0] = atom;

                reply[1] = new OtpErlangAtom("GUI");

                reply[2] = new OtpErlangAtom("server8@rafal-GL553VD");

                reply[3] = new OtpErlangAtom("stop");



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
        root.getChildren().add(btn3);
        root.getChildren().add(btn4);
        root.getChildren().add(label1);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
        root.getChildren().add(year);
        root.getChildren().add(month);
        root.getChildren().add(day);
        root.getChildren().add(h);
        root.getChildren().add(m);
        root.getChildren().add(s);
        root.getChildren().add(lvl);
        root.getChildren().add(type);
        root.getChildren().add(label4);
        root.getChildren().add(label5);
        root.getChildren().add(label6);
        root.getChildren().add(label7);
        root.getChildren().add(label8);
        root.getChildren().add(label9);
        root.getChildren().add(label10);
        root.getChildren().add(label11);
        root.getChildren().add(btn6);

        primaryStage.setScene(new Scene(root, 400, 580));
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
