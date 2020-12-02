package com.example.gogoroutine.activity_routinemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.gogoroutine.R;
import com.example.gogoroutine.others.RoutineDAO;
import com.example.gogoroutine.others.RoutineDO;
import com.example.gogoroutine.others.RoutineTaskDAO;

public class ActivityRoutineManager extends AppCompatActivity {

    public static int REQUEST_TASKMANAGER =1;

    public TaskAddDialog taskdialog;

    RoutineDO routineDO;
    RoutineDAO routineDAO;

    TextView tvMenuName;

    Switch switchIsWholeWeeks, switchIsNotice;
    ToggleButton t1, t2, t3, t4, t5, t6, t7;
    View daypicker;
    LinearLayout ll2, daypickerlayout;
    Button btnStartTime, btnAlarmSound, btnCancel, btnComplete;
    EditText etName;
    ToggleButton tgIsSound, tgIsVibration;
    RecyclerView recyclerView;

    Button btnTaskAdd,btnTaskSelect;

    final static int MODE_NEW = 1;
    final static int MODE_EDIT = 2;

    private int mode;
    private int iRoutineNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_manager);


        viewAutoWorkSetting();//사용자에 의해 번경되는 모습으로 작동


        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", MODE_NEW);
        routineDAO = new RoutineDAO(this);

        if (mode == MODE_NEW) {
            setWholeWeeks(true);
            switchIsWholeWeeks.setChecked(true);
            setIsNotice(false);
            switchIsNotice.setChecked(false);
            btnStartTime.setText(convertTime(routineDO.getStartHour(), routineDO.getStartMinute()));


        } else if (mode == MODE_EDIT) {

            tvMenuName.setText("루틴수정");
            btnComplete.setText("저장");
            iRoutineNum = intent.getIntExtra("num", 0);
            routineDO = routineDAO.getRoutineDetails(iRoutineNum);

            etName.setText(routineDO.getName());

            String selectedWeeks = routineDO.getSelectedWeeks().trim();

            if (selectedWeeks.equals("1234567")) {
                switchIsWholeWeeks.setChecked(setWholeWeeks(true));
            } else {
                switchIsWholeWeeks.setChecked(setWholeWeeks(false));

                t1.setChecked(selectedWeeks.contains("1"));
                t2.setChecked(selectedWeeks.contains("2"));
                t3.setChecked(selectedWeeks.contains("3"));
                t4.setChecked(selectedWeeks.contains("4"));
                t5.setChecked(selectedWeeks.contains("5"));
                t6.setChecked(selectedWeeks.contains("6"));
                t7.setChecked(selectedWeeks.contains("7"));

            }

            switchIsNotice.setChecked(setIsNotice(routineDO.getIsAlamEnable()==1?true:false));
            btnStartTime.setText(convertTime(routineDO.getStartHour(), routineDO.getStartMinute()));
            tgIsSound.setChecked(routineDO.getIsSound()==1?true:false);
            tgIsVibration.setChecked(routineDO.getIsVibration()==1?true:false);


        }

        displayRoutineTaskList();


    }


    //통합 설정 적용
    private void viewAutoWorkSetting() {


        routineDO = new RoutineDO(0, "", 0, 9, 0, "1234567", 0, 0, 0);

        tvMenuName = (TextView) findViewById(R.id.main_tv_menuname);

        switchIsWholeWeeks = (Switch) findViewById(R.id.routinemanager_switch_iswholeweeks);
        t1 = (ToggleButton) findViewById(R.id.t1);
        t2 = (ToggleButton) findViewById(R.id.t2);
        t3 = (ToggleButton) findViewById(R.id.t3);
        t4 = (ToggleButton) findViewById(R.id.t4);
        t5 = (ToggleButton) findViewById(R.id.t5);
        t6 = (ToggleButton) findViewById(R.id.t6);
        t7 = (ToggleButton) findViewById(R.id.t7);
        daypicker = (View) findViewById(R.id.daypicker);

        recyclerView = (RecyclerView)findViewById(R.id.routinemanager_recyclerview);

        btnStartTime = (Button) findViewById(R.id.routinemanager_btn_starttime);
        btnAlarmSound = (Button) findViewById(R.id.routinemanager_btn_alarmsound);

        switchIsNotice = (Switch) findViewById(R.id.routinemanager_switch_isNotice);
        ll2 = (LinearLayout) findViewById(R.id.routinemanager_ll2);
        daypickerlayout = (LinearLayout) findViewById(R.id.routinemanager_daypicker_layout);

        btnCancel = (Button) findViewById(R.id.routinemanager_btn_cancel);
        btnComplete = (Button) findViewById(R.id.routinemanager_btn_complete);

        etName = (EditText) findViewById(R.id.routinemanager_et_routinename);
        tgIsSound = (ToggleButton) findViewById(R.id.routinemanager_tg_sound);
        tgIsVibration = (ToggleButton) findViewById(R.id.routinemanager_tg_vibration);

        btnTaskAdd = (Button)findViewById(R.id.routinemanager_btn_taskadd);
        btnTaskSelect = (Button)findViewById(R.id.routinemanager_btn_taskselect);

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
                        routineDO.setStartHour(i);
                        routineDO.setStartMinute(i1);
                        btnStartTime.setText(convertTime(routineDO.getStartHour(), routineDO.getStartMinute()));
                    }
                }, routineDO.getStartHour(), routineDO.getStartMinute(), false);
                timePickerDialog.show();
            }
        });

        btnAlarmSound.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmPickerDialog alarmDialog = new AlarmPickerDialog(ActivityRoutineManager.this);

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

                    if (etName.getText().toString().trim().equals("")) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRoutineManager.this);
                        alert.setTitle("주의");
                        alert.setMessage("루틴 이름을 입력하세요.");
                        alert.setPositiveButton("OK", null);
                        alert.show();


                        etName.requestFocus();
                        return;


                    }

                    routineDO.setName(etName.getText().toString().trim());

                    //알림여부 저장
                    routineDO.setIsAlamEnable((switchIsNotice.isChecked()) ? 1 : 0);

                    String sSelectedWeeks;

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

                    routineDO.setSelectedWeeks(sSelectedWeeks);

                    //알람음 지정 필요


                    //소리여부 저장
                    routineDO.setIsSound((tgIsSound.isChecked()) ? 1 : 0);

                    //진동여부 저장
                    routineDO.setIsVibration((tgIsVibration.isChecked()) ? 1 : 0);

                    if(mode == MODE_NEW) {
                        routineDAO.insertNewRoutine(routineDO);
                    }else if(mode == MODE_EDIT){
                        routineDO.setRoutineNum(iRoutineNum);
                        routineDAO.updateRoutine(routineDO);
                    }

                    setResult(RESULT_OK);
                    finish();

                }


            });
        }



        btnTaskAdd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskdialog = new TaskAddDialog(ActivityRoutineManager.this);
                taskdialog.showDialog(ActivityRoutineManager.this);
            }
        });



    }


    private void displayRoutineTaskList(){

        ActivityRoutineManagerAdapter adapter = new ActivityRoutineManagerAdapter();

        Cursor cursor = new RoutineTaskDAO(this).getRoutinTaskList(iRoutineNum);


        while(cursor.moveToNext()){
            adapter.addItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8));
        }

        //테스트 코드

        /*
        for(int i =0 ; i<30; i++){
            adapter.addItem(1,2,"프로"+i,i,"");
        }
         */


        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        recyclerView.setAdapter(adapter);
    }

    private boolean setWholeWeeks(boolean b) {

        t1.setChecked(b);
        t2.setChecked(true);
        t3.setChecked(b);
        t4.setChecked(b);
        t5.setChecked(b);
        t6.setChecked(b);
        t7.setChecked(b);

        daypickerlayout.setVisibility(b == false ? View.VISIBLE : View.GONE);

        return b;

    }

    private boolean setIsNotice(boolean b) {
        ll2.setVisibility(b == true ? View.VISIBLE : View.GONE);
        return b;
    }



    private String convertTime(int hour, int minute) {

        String convertedTime = "AM 09:00";

        //24시간 기준
        // 0은 AM 12시
        // 1은 AM 1시
        // 12는 PM 12시
        // 13은 PM 1시


        if (hour == 0) {
            convertedTime = "AM 12:";
        } else if (1 <= hour && hour < 10) {
            convertedTime = "AM 0" + hour + ":";
        } else if (10 <= hour && hour < 12) {
            convertedTime = "AM " + hour + ":";
        } else if (hour == 12) {
            convertedTime = "PM 12:";
        } else if (13 <= hour && hour < 22) {
            convertedTime = "PM 0" + (hour - 12) + ":";
        } else if (22 <= hour) {
            convertedTime = "PM " + (hour - 12) + ":";
        }

        if (minute < 10) {
            convertedTime += "0" + minute;
        } else {
            convertedTime += minute;
        }


        return convertedTime;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_TASKMANAGER){

            if(resultCode == RESULT_OK){
                taskdialog.setSwitchButton(2);
            }

        }

    }
}