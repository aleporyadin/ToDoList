package com.project.todolist.service;

import com.project.todolist.entity.Task;

import java.sql.*;
import java.util.ArrayList;


//ти крч зробив що зберігає в базу але +\- тепер зроби так щоб з бд в табл записувалось

public class Service {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet resSet = null;
    private static int count =0;
    private final String url = "jdbc:sqlite:database.db";

    public static ArrayList<Task> tasks = new ArrayList<Task>();


    public Service() {
        try {
            if(count==0)createTable();
            count++;

            showLog("Connecting successfully");
        } catch (Exception e) {
            showLog("Connecting failed..", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }
    }

    private void createTable() {

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS task " +
                            "(ID          INTEGER         NOT NULL    PRIMARY KEY,  " +
                            " NAME        varchar(50)     NOT NULL,   " +
                            " CREATED     varchar(50)     NOT NULL,   " +
                            " DEADLINE    varchar(50)     NOT NULL,   " +
                            " EXECUTOR    varchar(50)     NOT NULL,   " +
                            " DESCRIPTION varchar(500)    NOT NULL);");
            stmt.close();
            showLog("Table create `task` successfully");
        } catch (SQLException e) {
            showLog("EXCEPTION: {Create Table}", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }
    }

    public void testInsert() {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT INTO TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION)" +
                            "VALUES ('Task1', 'created..', 'deadline..', 'exe..', 'supertask' )," +
                            " ('Task2', 'created..', 'deadline..', 'exe..', 'supertask' );");
            showLog("Test insert successfully");
        } catch (Exception e) {
            showLog("Test insert Failed", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }
    }

    private void showLog(final String message, Exception e) {
        System.out.print("\n [ " + message + " ] " + e.getMessage());
    }

    private void showLog(final String message) {
        System.out.print("\n [ " + message + " ] ");
    }

    public void saveAll() {
        if (tasks != null) saveTasks(tasks);
    }

    public ArrayList<Task> getTasks() {

        ArrayList<Task> tempArr = new ArrayList<Task>();
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Task;");
            while (resultSet.next()) {
                tasks.clear();
                tasks.add(new Task(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("CREATED"),
                        resultSet.getString("DEADLINE"),
                        resultSet.getString("EXECUTOR"),
                        resultSet.getString("DESCRIPTION")));
            }
            return tasks;
        } catch (Exception e) {
            showLog("GetTasks failed...", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }

        return null;
    }


    public Task getTaskById(Integer id) {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Task WHERE ID = " + id + ";");
            return new Task(id,
                    resultSet.getString("NAME"),
                    resultSet.getString("CREATED"),
                    resultSet.getString("DEADLINE"),
                    resultSet.getString("EXECUTOR"),
                    resultSet.getString("DESCRIPTION"));
        } catch (Exception e) {
            showLog("GetTaskById failed..", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }
        return null;
    }

    public void saveTask(Task task) {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt.executeUpdate(
                    "INSERT  INTO " +
                            "TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION) " +
                            "VALUES ('" + task.getName() + "', '" + task.getCreated() + "', '" +
                            task.getDeadline() + "', '" + task.getExecutor() + "', '" +
                            task.getDescription() + "' );");
            showLog("Save task successfully");
        } catch (Exception e) {
            showLog("SaveTask failed...", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }
    }


    public void saveTasks(ArrayList<Task> tasks) {
        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            for (Task task : tasks) {
                stmt.executeUpdate(
                        "INSERT  INTO " +
                                "TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION) " +
                                "VALUES ('" + task.getName() + "', '" + task.getCreated() + "', '" +
                                task.getDeadline() + "', '" + task.getExecutor() + "', '" +
                                task.getDescription() + "' );");

            }
            showLog("Save tasks successfully");
        } catch (Exception e) {
            showLog("Save Tasks failed...", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                showLog("Closing failed..", e);
            }
        }
    }


}
