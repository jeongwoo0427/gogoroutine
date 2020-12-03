package com.example.gogoroutine.activity_taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_routinemanager.AddTaskDialog.TaskAddDialog;
import com.example.gogoroutine.others.TaskDAO;
import com.example.gogoroutine.others.TaskDO;

public class ActivityTaskManager extends AppCompatActivity {

    TaskAddDialog dialog;

    TaskDAO taskDAO;
    TaskDO taskDO;

    Button btnCancel, btnComplete;
    EditText etEmoji,etName;
    NumberPicker npHour,npMinute,npSecond;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        ViewSetting();
    }

    void ViewSetting(){
        btnCancel = (Button)findViewById(R.id.taskmanager_btn_cancel);
        btnComplete = (Button)findViewById(R.id.taskmanager_btn_complete);
        etEmoji = (EditText)findViewById(R.id.taskmanager_emoji);
        etName = (EditText)findViewById(R.id.taskmanager_name);
        npHour = (NumberPicker)findViewById(R.id.taskmanager_timehour);
        npMinute = (NumberPicker)findViewById(R.id.taskmanager_timeminute);
        npSecond = (NumberPicker)findViewById(R.id.taskmanager_timesecond);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);




        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnComplete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etName.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTaskManager.this);

                    builder.setTitle("주의");
                    builder.setMessage("작업의 이름을 입력하세요");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    builder.show();
                    return;

                }

                taskDAO = new TaskDAO(ActivityTaskManager.this);
                taskDO = new TaskDO(
                        etName.getText().toString().trim(),
                        npHour.getValue(),
                        npMinute.getValue(),
                        npSecond.getValue(),
                        etEmoji.getText().toString().trim(),
                        "",
                        2
                );

                taskDAO.InsertNewTask(taskDO);

                setResult(RESULT_OK);
                finish();
            }
        });

    }
}