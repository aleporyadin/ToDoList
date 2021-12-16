package com.project.todolist.service;

import com.project.todolist.entity.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;



//ти крч зробив що зберігає в базу але +\- тепер зроби так щоб з бд в табл записувалось

public class Service {

    public static ArrayList<Task> tasks = new ArrayList<Task>();

    public void testInsert() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()) {

                System.out.println("Connection to Store DB successfully!");
                Statement statement = conn.createStatement();
                int rows = statement.executeUpdate(
                        "INSERT INTO TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION)" +
                                "VALUES ('Task1', 'created..', 'deadline..', 'exe..', 'supertask' )," +
                                " ('Task2', 'created..', 'deadline..', 'exe..', 'supertask' );");
                System.out.printf("Added %d rows", rows);
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            showLog(ex.getMessage());
        }
    }

    public void saveAll() {
        if (tasks != null) saveTasks(tasks);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tempArr = new ArrayList<Task>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()) {
                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM Task;");

                while (resultSet.next()){
                    tasks.clear();
                    tasks.add(new Task(resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("CREATED"),
                            resultSet.getString("DEADLINE"),
                            resultSet.getString("EXECUTOR"),
                            resultSet.getString("DESCRIPTION")));
                }
                return tasks;

            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            showLog(ex.getMessage());
        }
        return null;
    }

    public Task getTaskById(Integer id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()) {
                Statement statement = conn.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM Task WHERE ID = " + id + ";");


                return new Task(id,
                        resultSet.getString("NAME"),
                        resultSet.getString("CREATED"),
                        resultSet.getString("DEADLINE"),
                        resultSet.getString("EXECUTOR"),
                        resultSet.getString("DESCRIPTION"));

            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            showLog(ex.getMessage());
        }
        return null;
    }

    public void saveTask(Task task) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()) {
                Statement statement = conn.createStatement();
                statement.executeUpdate(
                        "INSERT  INTO " +
                                "TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION) " +
                                "VALUES ('" + task.getName() + "', '" + task.getCreated() + "', '" +
                                task.getDeadLine() + "', '" + task.getExecutor() + "', '" +
                                task.getDescription() + "' ),");
            }
            showLog("Save task successfully");

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            showLog(ex.getMessage());
        }
    }

    public void saveTasks(ArrayList<Task> tasks) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()) {
                Statement statement = conn.createStatement();
                for (Task task : tasks) {
                    statement.executeUpdate(
                            "INSERT  INTO " +
                                    "TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION) " +
                                    "VALUES ('" + task.getName() + "', '" + task.getCreated() + "', '" +
                                    task.getDeadLine() + "', '" + task.getExecutor() + "', '" +
                                    task.getDescription() + "' );");
                }
            }
            showLog("Save tasks successfully");
        } catch (Exception ex) {
            showLog("Save Tasks failed..."+ ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

    private void showLog(String message) {
        System.out.print(message);
    }

}
