package week1.les1.practicum7;


import java.io.*;
import java.net.*;
import java.util.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;

public class ChatClient extends Application implements EventHandler<ActionEvent> {
    private Socket verbinding;
    private PrintWriter sender;
    private Scanner receiver;
    private TextArea chatBox;
    private TextField chatField;
    private Button submit;
    private TextField chatName;

    public ChatClient() throws IOException{
        verbinding = new Socket("localhost",8080);

        sender = new PrintWriter(verbinding.getOutputStream());
        receiver = new Scanner(verbinding.getInputStream());

        Thread listener = new MessageListener();
        listener.start();
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane(10,10);
        root.setPadding(new Insets(10));


        chatBox = new TextArea();
        chatBox.setPrefWidth(480);
        chatBox.setEditable(false);
        root.getChildren().add(chatBox);

        Label labelNaam = new Label("Naam:");
        chatName = new TextField();
        root.getChildren().addAll(labelNaam, chatName);

        Label labelMessage = new Label("Message");
        chatField = new TextField();
        root.getChildren().addAll(labelMessage, chatField);

        submit = new Button("Send");
        submit.setOnAction(this);
        root.getChildren().add(submit);




        Scene scene = new Scene(root, 500, 275);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    verbinding.close();
                }
                catch(Exception e) { }
            }
        });

        primaryStage.setTitle("Chat Application 1.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void handle(ActionEvent event) {
        if (event.getSource() == submit) {
            sendMessage();
        }
    }

    public void close() {

    }

    public void sendMessage(){
        if (!chatName.isDisabled()) {
            if (chatName.getText().isEmpty()) {
                chatBox.appendText("Voer eerst een chatnaam in!\n");
                return;
            }
            else {
                chatName.setDisable(true);
            }
        }

        if (!verbinding.isConnected()) {
            System.out.println("Verbinding is verbroken");
            chatBox.appendText("Verbinding is verbroken!\n");
            return;
        }

        sender.println(chatName.getText() + ":\t" + chatField.getText());
        sender.flush();
        chatField.setText("");
    }



    public static void main(String[] args) { Application.launch(args); }

    private class MessageListener extends Thread {
        public void run() {
            String message = receiver.nextLine();
            while (!message.isEmpty()) {

                chatBox.setText(chatBox.getText() + message + "\n");
                message = receiver.nextLine();
            }

            chatBox.setText("Verbinding is verbroken!");
        }
    }
}