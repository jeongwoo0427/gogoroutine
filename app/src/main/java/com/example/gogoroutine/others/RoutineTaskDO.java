package com.example.gogoroutine.others;

public class RoutineTaskDO {
    private int routineNum;
    private String name;
    private int hour;
    private int minute;
    private int second;
    private String emoji;
    private String summary;
    private int order;

    public int getRoutineNum(){return this.routineNum;}
    public String getName(){return this.name;}
    public int getHour(){return this.hour;}
    public int getMinute(){return this.minute;}
    public int getSecond(){return this.second;}
    public String getEmoji(){return this.emoji;}
    public String getSummary(){return this.summary;}
    public int getOrder(){return this.order;}


    public void setRoutineNum(int num){this.routineNum = num;}
    public void setName(String name){this.name = name;}
    public void setHour(int hour){this.hour = hour;}
    public void setMinute(int minute){this.minute = minute;}
    public void setSecond(int second){this.second =second;}
    public void setEmoji(String emoji){this.emoji = emoji;}
    public void setSummary(String summary){this.summary = summary;}
    public void setOrder(int order){this.order = order;}

    public RoutineTaskDO(String name, int hour, int minute, int second, String emoji, String summary, int order){

        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.emoji = emoji;
        this.summary = summary;
        this.order = order;

    }

    public RoutineTaskDO(int num, String name, int hour, int minute, int second, String emoji, String summary, int order){

        this.routineNum = num;
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.emoji = emoji;
        this.summary = summary;
        this.order = order;

    }

}
