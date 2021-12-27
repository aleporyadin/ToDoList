package com.project.todolist.controller;

import com.project.todolist.entity.Task;
import com.project.todolist.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditTaskController implements Initializable {


    @FXML
    public DatePicker secondFieldDatePicker;
    @FXML
    public TextField secondFieldTaskName;
    @FXML
    public TextField secondFieldTimeToEnd;
    @FXML
    public TextArea secondFieldDescription;
    @FXML
    public TextField secondFieldExecutor;
    @FXML
    public DatePicker secondFieldDatePickerCreated;
    @FXML
    public TextField secondFieldTimeCreated;

    public Service service = new Service();
    @FXML
    public Button secondButtonConfirm;
    @FXML
    public Button secondButtonCancel;
    public static Task taskToEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (taskToEdit != null) {
            secondFieldTaskName.setText(taskToEdit.getName());
            secondFieldDescription.setText(taskToEdit.getDescription());
            secondFieldExecutor.setText(taskToEdit.getExecutor());

        }


    }

    @FXML
    public void buttonConfirm(ActionEvent actionEvent) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            String name = null;
            String executor = null;
            String description = null;
            String deadLine = null;

            String created = dateFormat.format(new Date());


            Pattern pattern = null;

            // check name
            if (!secondFieldTaskName.getText().isBlank()) {
                secondFieldTaskName.setStyle("-fx-border-color: #BEBEBE;");
                name = secondFieldTaskName.getText();


            } else {
                secondFieldTaskName.setStyle("-fx-border-color: #660000;");
                secondFieldTaskName.setPromptText("Invalid name task..");
            }


            // check date created
            if (secondFieldDatePickerCreated.getValue() == null) {
                secondFieldTimeCreated.setPromptText("Invalid date..");
                secondFieldDatePickerCreated.setStyle("-fx-border-color: #660000;");
            } else {
                secondFieldDatePickerCreated.setStyle("-fx-border-color: #BEBEBE;");
            }

            // check timetoend
            if (!secondFieldTimeCreated.getText().isBlank() &&
                    Pattern.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", secondFieldTimeCreated.getText().formatted("HH:mm:ss"))) {
                deadLine = secondFieldDatePickerCreated.getValue().format(formatter) + " " +
                        secondFieldTimeCreated.getText().formatted("HH:mm:ss");
                secondFieldTimeCreated.setStyle("-fx-border-color: #BEBEBE;");
            } else {
                secondFieldTimeCreated.setPromptText("Invalid time..");
                secondFieldTimeCreated.setStyle("-fx-border-color: #660000;");
            }


            // check date deadline
            if (secondFieldDatePicker.getValue() == null) {
                secondFieldTimeToEnd.setPromptText("Invalid date..");
                secondFieldDatePicker.setStyle("-fx-border-color: #660000;");
            } else {
                secondFieldDatePicker.setStyle("-fx-border-color: #BEBEBE;");
            }


            // check timetoend
            if (!secondFieldTimeToEnd.getText().isBlank() &&
                    Pattern.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", secondFieldTimeToEnd.getText().formatted("HH:mm:ss"))) {
                deadLine = secondFieldDatePicker.getValue().format(formatter) + " " +
                        secondFieldTimeToEnd.getText().formatted("HH:mm:ss");
                secondFieldTimeToEnd.setStyle("-fx-border-color: #BEBEBE;");
            } else {
                secondFieldTimeToEnd.setPromptText("Invalid time..");
                secondFieldTimeToEnd.setStyle("-fx-border-color: #660000;");
            }


            // check executor
            if (!secondFieldExecutor.getText().toString().isEmpty()) {
                executor = secondFieldExecutor.getText();
                secondFieldExecutor.setStyle("-fx-border-color: #BEBEBE;");
            } else {
                secondFieldExecutor.setPromptText("Invalid name executor..");
                secondFieldExecutor.setStyle("-fx-border-color: #660000;");
            }

            // check description
            if (!secondFieldDescription.getText().toString().isEmpty()) {
                secondFieldDescription.setStyle("-fx-border-color: #BEBEBE;");
                description = secondFieldDescription.getText();
            } else {
                secondFieldDescription.setPromptText("Empty description..");
                secondFieldDescription.setStyle("-fx-border-color: #660000;");
            }

//            if(name!=null && deadLine!=null && executor!=null && description!=null){
//                Task task = new Task(name, created, deadLine, executor, description);
//                service.saveTask(task);
//
//                secondFieldTaskName.setText("");
//                secondFieldDescription.setText("");
//                secondFieldExecutor.setText("");
//            }

            MainController mc = new MainController();
            mc.refreshTable();

        } catch (Exception e) {
            System.out.print("Add task failed" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) secondButtonCancel.getScene().getWindow();
        stage.close();
    }

    public Task getTaskToEdit() {
        return taskToEdit;
    }

    public void setTaskToEdit(Task taskToEdit) {
        this.taskToEdit = taskToEdit;
    }
}
