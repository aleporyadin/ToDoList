package com.project.todolist.controller;


import com.project.todolist.service.Service;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.project.todolist.Main;
import com.project.todolist.entity.Task;

import static com.project.todolist.service.Service.tasks;

public class MainController implements Initializable {

    public TextField fieldTaskName;
    public TextField fieldTimeToEnd;
    public TextArea fieldDescription;
    public TextField fieldExecutor;
    public DatePicker fieldDatePicker;
    public TableView<Task> fieldTableView;
    public TableColumn<Task, Integer> fieldColumnId;
    public TableColumn<Task, String> fieldColumnName;
    public TableColumn<Task, String> fieldColumnCreated;
    public TableColumn<Task, String> fieldColumnDeadLine;
    public TableColumn<Task, String> fieldColumnDescription;
    public TableColumn<Task, String> fieldColumnExecutor;
    public Service service= new Service();

    @FXML
    public MenuBar menuBar;

    @FXML
    public Menu menuFile;


    @FXML
    private Button button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Service service = new Service();
        service.testInsert();
        if (fieldTableView.getItems().size() == 0)
            fieldTableView.setPlaceholder(new Label("No rows to display"));
        this.fieldDatePicker.setValue(LocalDate.now());
        this.fieldTimeToEnd.setText(String.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
    }

    public void exitBtn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to get out?");
        alert.setContentText("Choose action");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            Platform.exit();
        else
            alert.close();
    }

    private void refreshTable() {
        service.saveAll();
        tasks= service.getTasks();
        if (!tasks.isEmpty()) {
            for(Task task : tasks) {
                fieldTableView.getItems().add(task);
            }
        }
        else {
            fieldTableView.setPlaceholder(new Label("No rows to display"));
        }
    }


    @FXML
    public void otherLabs() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("labTest.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // WxH
        File file = new File("./src/main/resources/styles/base.css");

        stage.setMinWidth(600);
        stage.setMinHeight(400);

        scene.getStylesheets().add(file.toURI().toString());

        stage.setTitle("[PySoft] To do List");
        stage.setOnCloseRequest(event -> {
            event.consume();
            stage.close();
        });
        refreshTable();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void menuFileClose() throws IOException {
        exitBtn();

    }

    @FXML
    public void addTask() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            String name;
            String executor;
            String description;
            String deadLine;

            if (!fieldTaskName.getText().toString().isEmpty())
                name = fieldTaskName.getText();
            else throw new Exception("Field {name} empty");

            String created = dateFormat.format(new Date());
            if (!fieldTaskName.getText().toString().isEmpty())
                deadLine = fieldDatePicker.getValue().format(formatter) + " " +
                        fieldTimeToEnd.getText().formatted("HH:mm::ss");
            else throw new Exception("Field {datepicker or time} empty");

            if (!fieldExecutor.getText().toString().isEmpty())
                executor = fieldExecutor.getText();
            else throw new Exception("Field {executor} empty");

            if (!fieldDescription.getText().toString().isEmpty())
                description = fieldDescription.getText();
            else throw new Exception("Field {description} empty");

            Task task = new Task(name, created, deadLine, executor, description);


            tasks.add(task);
            refreshTable();
        } catch (Exception e) {
            System.out.print("Add task failed"+e.getMessage());
            e.printStackTrace();
        }

    }


}