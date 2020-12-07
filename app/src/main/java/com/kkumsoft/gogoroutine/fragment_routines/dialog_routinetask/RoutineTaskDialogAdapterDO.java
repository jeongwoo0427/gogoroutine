package com.kkumsoft.gogoroutine.fragment_routines.dialog_routinetask;

public class RoutineTaskDialogAdapterDO {

    private int iRoutineNum;
    private String sName;
    private int hour;
    private int minute;
    private int second;
    private String sEmoji;
    private String sSummary;
    private int order;

    public void setiRoutineNum(int iRoutineNum){this.iRoutineNum = iRoutineNum;}
    public void setsName(String sName){this.sName = sName;}
    public void setHour(int hour){this.hour = hour;}
    public void setMinute(int minute){this.minute = minute;}
    public void setSecond(int second){this.second = second;}
    public void setsEmoji(String sEmoji){this.sEmoji = sEmoji;}
    public void setsSummary(String sSummary){this.sSummary = sSummary;}
    public void setOrder(int order){this.order = order;}

    public int getiRoutineNum(){return this.iRoutineNum;}
    public String getsName(){return this.sName;}
    public int getHour(){return this.hour;}
    public int getMinute(){return this.minute;}
    public int getSecond(){return this.second;}
    public String getsEmoji(){return this.sEmoji;}
    public String getsSummary(){return this.sSummary;}
    public int getOrder(){return this.order;}

}
