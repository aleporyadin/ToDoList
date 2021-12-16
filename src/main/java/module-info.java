module com.project.todolist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.java;

    opens com.project.todolist to javafx.fxml;
    exports com.project.todolist;
    exports com.project.todolist.controller;
    opens com.project.todolist.controller to javafx.fxml;
    exports com.project.todolist.entity;
    opens com.project.todolist.entity to javafx.fxml;
}