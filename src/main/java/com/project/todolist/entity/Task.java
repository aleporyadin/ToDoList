package com.project.todolist.entity;

public class Task {

    private Integer id;
    private String name;
    private String created;
    private String deadLine;
    private String executor;
    private String description;


    public Task(Integer _id, String taskName, String taskCreated, String taskDeadLine, String executor, String description) {
        this.id=_id;
        this.name = taskName;
        this.created = taskCreated;
        this.deadLine = taskDeadLine;
        this.executor = executor;
        this.description = description;
    }
    public Task(String taskName, String taskCreated, String taskDeadLine, String executor, String description) {
        this.name = taskName;
        this.created = taskCreated;
        this.deadLine = taskDeadLine;
        this.executor = executor;
        this.description = description;
    }
    public void showTask(){
        System.out.println("\nTask:\nId: "+this.getId()+"\nName: "+
                this.name +"\nCreated: "+this.created+"\nDeadline: "+
                this.deadLine+"\nExecutor: "+this.executor+"\nDescription: "+this.description);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCreated() {
        return created;
    }
    public void setCreated(String created) {
        this.created = created;
    }
    public String getDeadLine() {
        return deadLine;
    }
    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }
    public String getExecutor() {
        return executor;
    }
    public void setExecutor(String executor) {
        this.executor = executor;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
