package com.example.gogoroutine.activity_routinemanager;

public class ActivityRoutineManagerAdapterDO {



    private int iRoutineNum;
    private int iTaskNum;
    private String sName;
    private int iTime;
    private String sEmoji;
    private String sSummary;

    public void setiRoutineNum(int iRoutineNum){this.iRoutineNum = iRoutineNum;}
    public void setiTaskNum(int iTaskNum){this.iTaskNum = iTaskNum;}
    public void setsName(String sName){this.sName = sName;}
    public void setiTime(int iTime){this.iTime = iTime;}
    public void setsEmoji(String sEmoji){this.sEmoji = sEmoji;}
    public void setsSummary(String sSummary){this.sSummary = sSummary;}

    public int getiRoutineNum(){return this.iRoutineNum;}
    public int getiTaskNum(){return this.iTaskNum;}
    public String getsName(){return this.sName;}
    public int getiTime(){return this.iTime;}
    public String getsEmoji(){return this.sEmoji;}
    public String getsSummary(){return this.sSummary;}




}
