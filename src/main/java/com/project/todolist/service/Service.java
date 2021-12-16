package com.project.todolist.service;

import com.project.todolist.entity.Task;

import java.sql.*;
import java.util.ArrayList;



//ти крч зробив що зберігає в базу але +\- тепер зроби так щоб з бд в табл записувалось

public class Service {


    private static Connection service = null;
    private static Statement stmt = null;
    public static ArrayList<Task> tasks = new ArrayList<Task>();

    private final String url = "jdbc:sqlite:database.db";

    private void showLog(final String message) {
        System.out.print("\n [ "+message+" ] ");
    }

    private Service()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");

            service = DriverManager.getConnection(url);
            stmt = service.createStatement();


            showLog("Connect successfully");

        } catch (Exception e)
        {
            showLog("EXCEPTION: {Service}\n"+e.getMessage());
            System.exit(0);
        }
    }

    public static Connection getConnect()
    {
        if (service == null) new Service();
        return service;
    }

    public void closeConnect(){
        try {
            service.close();
            stmt.close();
        } catch (Exception e) {
            showLog("EXCEPTION: {Close Connection}\n"+e.getMessage());
        }

    }
    private void createTable(){

        try {
            stmt.executeUpdate("CREATE TABLE `task` " +
                    "(`ID`         int(11)          NOT NULL    AUTO_INCREMENT,  " +
                    " `NAME`        varchar(50)     NOT NULL,   " +
                    " `CREATED`     varchar(50)     NOT NULL,   " +
                    " `DEADLINE`    varchar(50)     NOT NULL,   " +
                    " `EXECUTOR`    varchar(50)     NOT NULL,   " +
                    " `DESCRIPTION` varchar(500)    NOT NULL,   " +
                    "  PRIMARY KEY (`ID`) ) " +
                    "  ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4");

        } catch (SQLException e) {
            showLog("EXCEPTION: {Create Table}\n"+e.getMessage());
        }
    }


//
//
//    public void testInsert() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = getConnection()) {
//
//                System.out.println("Connection to Store DB successfully!");
//                Statement statement = conn.createStatement();
//                int rows = statement.executeUpdate(
//                        "INSERT INTO TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION)" +
//                                "VALUES ('Task1', 'created..', 'deadline..', 'exe..', 'supertask' )," +
//                                " ('Task2', 'created..', 'deadline..', 'exe..', 'supertask' );");
//                System.out.printf("Added %d rows", rows);
//            }
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            showLog(ex.getMessage());
//        }
//    }
//
//    public void saveAll() {
//        if (tasks != null) saveTasks(tasks);
//    }
//
//    public ArrayList<Task> getTasks() {
//        ArrayList<Task> tempArr = new ArrayList<Task>();
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = getConnection()) {
//                Statement statement = conn.createStatement();
//
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM Task;");
//
//                while (resultSet.next()){
//                    tasks.clear();
//                    tasks.add(new Task(resultSet.getInt("ID"),
//                            resultSet.getString("NAME"),
//                            resultSet.getString("CREATED"),
//                            resultSet.getString("DEADLINE"),
//                            resultSet.getString("EXECUTOR"),
//                            resultSet.getString("DESCRIPTION")));
//                }
//                return tasks;
//
//            }
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            showLog(ex.getMessage());
//        }
//        return null;
//    }
//
//    public Task getTaskById(Integer id) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = getConnection()) {
//                Statement statement = conn.createStatement();
//
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM Task WHERE ID = " + id + ";");
//
//
//                return new Task(id,
//                        resultSet.getString("NAME"),
//                        resultSet.getString("CREATED"),
//                        resultSet.getString("DEADLINE"),
//                        resultSet.getString("EXECUTOR"),
//                        resultSet.getString("DESCRIPTION"));
//
//            }
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            showLog(ex.getMessage());
//        }
//        return null;
//    }
//
//    public void saveTask(Task task) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = getConnection()) {
//                Statement statement = conn.createStatement();
//                statement.executeUpdate(
//                        "INSERT  INTO " +
//                                "TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION) " +
//                                "VALUES ('" + task.getName() + "', '" + task.getCreated() + "', '" +
//                                task.getDeadLine() + "', '" + task.getExecutor() + "', '" +
//                                task.getDescription() + "' ),");
//            }
//            showLog("Save task successfully");
//
//        } catch (Exception ex) {
//            System.out.println("Connection failed...");
//            showLog(ex.getMessage());
//        }
//    }
//
//    public void saveTasks(ArrayList<Task> tasks) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = getConnection()) {
//                Statement statement = conn.createStatement();
//                for (Task task : tasks) {
//                    statement.executeUpdate(
//                            "INSERT  INTO " +
//                                    "TASK(NAME,CREATED,DEADLINE,EXECUTOR,DESCRIPTION) " +
//                                    "VALUES ('" + task.getName() + "', '" + task.getCreated() + "', '" +
//                                    task.getDeadLine() + "', '" + task.getExecutor() + "', '" +
//                                    task.getDescription() + "' );");
//                }
//            }
//            showLog("Save tasks successfully");
//        } catch (Exception ex) {
//            showLog("Save Tasks failed..."+ ex.getMessage());
//        }
//    }
//
//    public static Connection getConnection() throws SQLException, IOException {
//
//        Properties props = new Properties();
//        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
//            props.load(in);
//        }
//        String url = props.getProperty("url");
//        String username = props.getProperty("username");
//        String password = props.getProperty("password");
//
//        return DriverManager.getConnection(url, username, password);
//    }
//


}
