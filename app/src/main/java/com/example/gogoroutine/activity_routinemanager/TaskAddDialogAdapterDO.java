package com.example.gogoroutine.activity_routinemanager;

public class TaskAddDialogAdapterDO {

    private int taskNum;
    private String name;
    private int time;
    private String emoji;
    private String summary;
    private int category;

    public int getTaskNum(){return this.taskNum;}
    public String getName(){return this.name;}
    public int getTime(){return this.time;}
    public String getEmoji(){return this.emoji;}
    public String getSummary(){return this.summary;}
    public int getCategory(){return this.category;}


    public void setTaskNum(int taskNum){this.taskNum = taskNum;}
    public void setName(String name){this.name = name;}
    public void setTime(int time){this.time = time;}
    public void setEmoji(String emoji){this.emoji = emoji;}
    public void setSummary(String summary){this.summary = summary;}
    public void setCategory(int category){this.category = category;}



}
