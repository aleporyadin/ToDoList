package com.project.todolist.controller;


import com.project.todolist.service.Service;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.project.todolist.Main;
import com.project.todolist.entity.Task;

import static com.project.todolist.service.Service.tasks;


public class MainController implements Initializable {
    @FXML public TextField fieldTaskName;
    @FXML public TextField fieldTimeToEnd;
    @FXML public TextArea fieldDescription;
    @FXML public TextField fieldExecutor;
    @FXML public DatePicker fieldDatePicker;
    @FXML public TableView<Task> fieldTableView;
    @FXML public TableColumn<Task,Integer> fieldColumnId;
    @FXML public TableColumn<Task, String> fieldColumnName;
    @FXML public TableColumn<Task, String> fieldColumnCreated;
    @FXML public TableColumn<Task, String> fieldColumnDeadLine;
    @FXML public TableColumn<Task, String> fieldColumnDescription;
    @FXML public TableColumn<Task, String> fieldColumnExecutor;
    @FXML public MenuBar menuBar;
    @FXML public Menu menuFile;
    @FXML public ImageView logo;
    @FXML private Button button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (fieldTableView.getItems().size() == 0)
            fieldTableView.setPlaceholder(new Label("No rows to display"));
        this.fieldDatePicker.setValue(LocalDate.now());
        this.fieldTimeToEnd.setText(String.valueOf(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))));
        refreshTable();

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
        Service service = new Service();
        tasks = service.getTasks();
        fieldTableView.getItems().clear();
        if (tasks != null && !tasks.isEmpty()) {
            for (Task task : tasks) {

//                ObservableList<Task> data = fieldTableView.getItems();
//                data.add(task);


                fieldTableView.getColumns().clear();
                fieldColumnId = new TableColumn<Task, Integer>("#");
                fieldColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));

                fieldColumnName = new TableColumn<Task, String>("Name");
                fieldColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

                fieldColumnCreated = new TableColumn<Task, String>("Created");
                fieldColumnCreated.setCellValueFactory(new PropertyValueFactory<>("created"));

                fieldColumnDeadLine = new TableColumn<Task, String>("DeadLine");
                fieldColumnDeadLine.setCellValueFactory(new PropertyValueFactory<>("deadline"));

                fieldColumnDescription = new TableColumn<Task, String>("Description");
                fieldColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

                fieldColumnExecutor = new TableColumn<Task, String>("Executor");
                fieldColumnExecutor.setCellValueFactory(new PropertyValueFactory<>("executor"));
                fieldTableView.getColumns().addAll(Arrays.asList(
                        fieldColumnId, fieldColumnName,fieldColumnCreated,fieldColumnDeadLine,
                        fieldColumnDescription,fieldColumnExecutor)
                );
                fieldTableView.getItems().add(task);
               // fieldTableView.getColumns().add(fieldColumnId,fieldColumnName);

            }
        } else {
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
            String name = null;
            String executor = null;
            String description = null;
            String deadLine = null;

            String created = dateFormat.format(new Date());

            Service service = new Service();

            Pattern pattern = null;

            // check name
            if (!fieldTaskName.getText().isBlank()) {
                fieldTaskName.setStyle("-fx-border-color: #BEBEBE;");
                name=fieldTaskName.getText();


            }else{
                fieldTaskName.setStyle("-fx-border-color: #660000;");
                fieldTaskName.setPromptText("Invalid name task..");
            }



            // check date
            if(fieldDatePicker.getValue()==null) {
                fieldTimeToEnd.setPromptText("Invalid date..");
                fieldDatePicker.setStyle("-fx-border-color: #660000;");
            }else{
                fieldDatePicker.setStyle("-fx-border-color: #BEBEBE;");
            }

            // check timetoend
            if (!fieldTimeToEnd.getText().isBlank() &&
                    Pattern.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",fieldTimeToEnd.getText().formatted("HH:mm:ss"))) {
                deadLine = fieldDatePicker.getValue().format(formatter) + " " +
                        fieldTimeToEnd.getText().formatted("HH:mm:ss");
                fieldTimeToEnd.setStyle("-fx-border-color: #BEBEBE;");
            }
            else {
                fieldTimeToEnd.setPromptText("Invalid time..");
                fieldTimeToEnd.setStyle("-fx-border-color: #660000;");
            }


            // check executor
            if (!fieldExecutor.getText().toString().isEmpty()) {
                executor = fieldExecutor.getText();
                fieldExecutor.setStyle("-fx-border-color: #BEBEBE;");
            }
            else {
                fieldExecutor.setPromptText("Invalid name executor..");
                fieldExecutor.setStyle("-fx-border-color: #660000;");
            }

            // check description
            if (!fieldDescription.getText().toString().isEmpty()) {
                fieldDescription.setStyle("-fx-border-color: #BEBEBE;");
                description = fieldDescription.getText();
            }
            else {
                fieldDescription.setPromptText("Empty description..");
                fieldDescription.setStyle("-fx-border-color: #660000;");
            }

            if(name!=null&& created!=null&&deadLine!=null&&executor!=null&&description!=null){
                Task task = new Task(name, created, deadLine, executor, description);
                service.saveTask(task);
            }
            refreshTable();

        } catch (Exception e) {
            System.out.print("Add task failed" + e.getMessage());
            e.printStackTrace();
        }

    }


}