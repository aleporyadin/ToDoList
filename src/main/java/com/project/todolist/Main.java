package com.project.todolist;


import com.project.todolist.service.Service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import java.util.Optional;


public class Main extends Application {

    static final  int WIDTH = 950;
    static final  int HEIGHT = 700;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT); // WxH
        File file = new File("./src/main/resources/styles/base.css");
        Service service = new Service();
        Font.loadFont(getClass().getResourceAsStream("/Montserrat-ExtraLight.otf.ttf"), 12);


        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);

        scene.getStylesheets().add(file.toURI().toString());

        stage.setTitle("[PrSoft] To do List");
        stage.setOnCloseRequest(event -> {
            event.consume();
            exitBtn();
        });


        stage.setScene(scene);
        stage.show();
    }
    public void exitBtn(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to get out?");
        alert.setContentText("Choose action");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            Platform.exit();
        else
            alert.close();

        System.out.println("Exit");
    }
    public void otherLabs() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("labTest.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT); // WxH

        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT+35);

        File file = new File("./src/main/resources/styles/base.css");
        scene.getStylesheets().add(file.toURI().toString());

        stage.setTitle("[PySoft] To do List");
        stage.setOnCloseRequest(event -> {
            event.consume();
            exitBtn();
        });

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}