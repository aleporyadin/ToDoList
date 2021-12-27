package com.project.todolist.entity;

public class User {

    private Integer id;
    private String fullName;
    private String login;
    private String password;


    public User(Integer id, String fullName, String login, String password) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public User(String fullName, String login, String password) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }












    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
