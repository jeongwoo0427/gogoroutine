package com.example.gogoroutine.activity_routinemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.gogoroutine.R;
import com.example.gogoroutine.others.DbOpenHelper;

public class ActivityRoutineManager extends AppCompatActivity {

    Switch switchIsWholeWeeks,switchIsNotice;
    ToggleButton t1,t2,t3,t4,t5,t6,t7;
    View daypicker;
    LinearLayout ll2,daypickerlayout;
    Button btnStartTime,btnAlarmSound,btnCancel,btnComplete;
    EditText etName;
    ToggleButton tgIsSound, tgIsVibration;

    final static int MODE_NEW =1;
    final static int MODE_EDIT = 2;

    private int mode;


    //임시 데이터객체 Instance Data Object
    private int iNo = 0;
    private String sRoutineName = "";
    private int iIsNoticeEnable = 0;
    private int startHour =9;
    private int startMinute =0;
    private String sSelectedWeeks = "1234567";
    private int iAlamMode = 0;
    private int iIsSound = 0;
    private int iIsVibration = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_manager);
        viewAutoWorkSetting();//사용자에 의해 번경되는 모습으로 작동

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",1);

        if(mode == MODE_NEW){
            setWholeWeeks(true);
            switchIsWholeWeeks.setChecked(true);
            setIsNotice(false);
            switchIsNotice.setChecked(false);
            btnStartTime.setText(convertTime(startHour,startMinute));


        }else if(mode == MODE_EDIT){

        }

    }


    //통합 설정 적용
    private void viewAutoWorkSetting(){



        switchIsWholeWeeks = (Switch)findViewById(R.id.routinemanager_switch_iswholeweeks);
        t1 = (ToggleButton)findViewById(R.id.t1);
        t2 = (ToggleButton)findViewById(R.id.t2);
        t3 = (ToggleButton)findViewById(R.id.t3);
        t4 = (ToggleButton)findViewById(R.id.t4);
        t5 = (ToggleButton)findViewById(R.id.t5);
        t6 = (ToggleButton)findViewById(R.id.t6);
        t7 = (ToggleButton)findViewById(R.id.t7);
        daypicker = (View)findViewById(R.id.daypicker);

        btnStartTime = (Button)findViewById(R.id.routinemanager_btn_starttime);
        btnAlarmSound = (Button)findViewById(R.id.routinemanager_btn_alarmsound);

        switchIsNotice = (Switch)findViewById(R.id.routinemanager_switch_isNotice);
        ll2 = (LinearLayout)findViewById(R.id.routinemanager_ll2);
        daypickerlayout = (LinearLayout)findViewById(R.id.routinemanager_daypicker_layout);

        btnCancel = (Button)findViewById(R.id.routinemanager_btn_cancel);
        btnComplete = (Button)findViewById(R.id.routinemanager_btn_complete);

        etName = (EditText)findViewById(R.id.routinemanager_et_routinename);
        tgIsSound = (ToggleButton)findViewById(R.id.routinemanager_tg_sound);
        tgIsVibration = (ToggleButton)findViewById(R.id.routinemanager_tg_vibration);

        //요일버튼에 따라 스위치의 Checked 도 다르게 설정해야함. 사용x 비효율 코드
        /*
        {
            t1.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });

            t2.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });

            t3.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });

            t4.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });

            t5.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });

            t6.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });

            t7.setOnClickListener(new ToggleButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(t1.isChecked()&t2.isChecked()&t3.isChecked()&t4.isChecked()&t5.isChecked()&t6.isChecked()&t7.isChecked()){
                        switchIsWholeWeeks.setChecked(true);
                    }else{
                        switchIsWholeWeeks.setChecked(false);
                    }
                }
            });




        }

         */

        switchIsWholeWeeks.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setWholeWeeks(b);
            }
        });

        switchIsNotice.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setIsNotice(b);
            }
        });

        btnStartTime.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityRoutineManager.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        startHour = i;
                        startMinute = i1;
                        btnStartTime.setText(convertTime(startHour,startMinute));
                    }
                },startHour,startMinute,false);
                timePickerDialog.show();
            }
        });

        btnAlarmSound.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityRoutineManager_AlarmPickerDialog alarmDialog = new ActivityRoutineManager_AlarmPickerDialog(ActivityRoutineManager.this);

                alarmDialog.showDialogForResult(btnAlarmSound);




            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //btnComplete.setOnClickListener
        {
            btnComplete.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //완료버튼 클릭시 실행

                    //이름 입력 여부 확인
                    sRoutineName = etName.getText().toString().trim();
                    if (sRoutineName.equals("")) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRoutineManager.this);
                        alert.setTitle("주의");
                        alert.setMessage("루틴 이름을 입력하세요.");
                        alert.setPositiveButton("OK", null);
                        alert.show();


                        etName.requestFocus();
                        return;


                    }

                    //알림여부 저장
                    iIsNoticeEnable = (switchIsNotice.isChecked()) ? 1 : 0;

                    //선택 요일 저장
                    {

                        sSelectedWeeks = "";

                        if (t1.isChecked()) {
                            sSelectedWeeks = "1";
                        }
                        if (t2.isChecked()) {
                            sSelectedWeeks += "2";
                        }
                        if (t3.isChecked()) {
                            sSelectedWeeks += "3";
                        }
                        if (t4.isChecked()) {
                            sSelectedWeeks += "4";
                        }
                        if (t5.isChecked()) {
                            sSelectedWeeks += "5";
                        }
                        if (t6.isChecked()) {
                            sSelectedWeeks += "6";
                        }
                        if (t7.isChecked()) {
                            sSelectedWeeks += "7";
                        }
                    }

                    //알람음 지정 필요


                    //소리여부 저장
                    iIsSound = (tgIsSound.isChecked()) ? 1 : 0;

                    //진동여부 저장
                    iIsSound = (tgIsVibration.isChecked()) ? 1 : 0;

                    SQLiteDatabase database = new DbOpenHelper(ActivityRoutineManager.this).getWritableDatabase();

                    String qry = "INSERT INTO routine(name,isNoticeEnable,startHour,startMinute,selectedWeeks,alamMode,isSound,isVibration) VALUES('" +
                            sRoutineName.trim() + "'," +
                            iIsNoticeEnable + "," +
                            startHour + "," +
                            startMinute + ",'" +
                            sSelectedWeeks + "'," +
                            iAlamMode + "," +
                            iIsSound + "," +
                            iIsVibration +
                            ")";

                    database.execSQL(qry);

                    setResult(RESULT_OK);
                    finish();

                }


            });
        }

    }
    private void setWholeWeeks(boolean b){

        t1.setChecked(b);
        t2.setChecked(true);
        t3.setChecked(b);
        t4.setChecked(b);
        t5.setChecked(b);
        t6.setChecked(b);
        t7.setChecked(b);

        daypickerlayout.setVisibility(b==false?View.VISIBLE:View.GONE);
        /*
        //레이아웃 자체를 없애버림에 따라 필요없는 코드
        t1.setEnabled(!b);
        t2.setEnabled(!b);
        t3.setEnabled(!b);
        t4.setEnabled(!b);
        t5.setEnabled(!b);
        t6.setEnabled(!b);
        t7.setEnabled(!b);


         */

    }
    private void setIsNotice(boolean b){
        ll2.setVisibility(b==true?View.VISIBLE:View.GONE);
    }

    private String convertTime(int hour, int minute) {

        String convertedTime = "AM 09:00";

        //24시간 기준
        // 0은 AM 12시
        // 1은 AM 1시
        // 12는 PM 12시
        // 13은 PM 1시


        if(hour == 0){
            convertedTime = "AM 12:";
        }else if(1<=hour &&hour <10){
            convertedTime = "AM 0"+hour+":";
        }else if(10<= hour && hour <12){
            convertedTime ="AM "+hour+":";
        }else if(hour == 12){
            convertedTime = "PM 12:";
        }else if(13<=hour&& hour<22){
            convertedTime = "PM 0"+(hour-12)+":";
        }else if(22<=hour){
            convertedTime = "PM "+(hour-12)+":";
        }

        if(minute <10){
            convertedTime +="0"+minute;
        }else{
            convertedTime +=minute;
        }


        return convertedTime;
    }

}