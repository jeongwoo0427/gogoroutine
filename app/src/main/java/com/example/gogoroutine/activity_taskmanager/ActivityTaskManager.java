package com.example.gogoroutine.activity_taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_routinemanager.AddTaskDialog.TaskAddDialog;
import com.example.gogoroutine.others.TaskDAO;
import com.example.gogoroutine.others.TaskDO;

public class ActivityTaskManager extends AppCompatActivity {


    TaskDAO taskDAO;
    TaskDO taskDO;

    Button btnCancel, btnComplete;
    EditText etEmoji,etName;
    NumberPicker npHour,npMinute,npSecond;
    ImageView ivDelete;

    int num = 0;
    int mode = 1;
    int category = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",1);
        taskDAO = new TaskDAO(this);

        ViewSetting();

        //새글
        if(mode==1) {
            category = 2;
            ivDelete.setVisibility(View.GONE);




        }else if(mode == 2){ //수정
            ivDelete.setVisibility(View.VISIBLE);
            num = intent.getIntExtra("num",0);
            taskDO = taskDAO.GetTaskDetail(num);
            etName.setText(taskDO.getName());
            etEmoji.setText(taskDO.getEmoji());
            npHour.setValue(taskDO.getHour());
            npMinute.setValue(taskDO.getMinute());
            npSecond.setValue(taskDO.getSecond());
            btnComplete.setText("저장");
            if(taskDO.getCategory()==1){
                category = 1;
                etName.setEnabled(false);
                ivDelete.setVisibility(View.GONE);
            }

        }


    }

    void ViewSetting(){
        btnCancel = (Button)findViewById(R.id.taskmanager_btn_cancel);
        btnComplete = (Button)findViewById(R.id.taskmanager_btn_complete);
        etEmoji = (EditText)findViewById(R.id.taskmanager_emoji);
        etName = (EditText)findViewById(R.id.taskmanager_name);
        npHour = (NumberPicker)findViewById(R.id.taskmanager_timehour);
        npMinute = (NumberPicker)findViewById(R.id.taskmanager_timeminute);
        npSecond = (NumberPicker)findViewById(R.id.taskmanager_timesecond);
        ivDelete = (ImageView)findViewById(R.id.taskmanager_iv_delete);

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
                        category
                );

                Intent intent = new Intent();

                if(mode == 1) {
                    taskDAO.InsertNewTask(taskDO);
                    intent.putExtra("tab",2);
                }else if(mode == 2){
                    taskDAO.UpdateTask(taskDO,num);
                    if(category == 1){
                        intent.putExtra("tab",1);
                    }else{
                        intent.putExtra("tab",2);
                    }
                }


                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}