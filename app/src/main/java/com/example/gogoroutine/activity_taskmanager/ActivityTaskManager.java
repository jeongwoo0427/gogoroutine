package com.example.gogoroutine.activity_taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.example.gogoroutine.R;
import com.example.gogoroutine.others.RoutineTaskDAO;
import com.example.gogoroutine.others.RoutineTaskDO;
import com.example.gogoroutine.others.TaskDAO;
import com.example.gogoroutine.others.TaskDO;
import com.github.data5tream.emojilib.EmojiGridView;
import com.github.data5tream.emojilib.EmojiPopup;
import com.github.data5tream.emojilib.emoji.Emojicon;


public class ActivityTaskManager extends AppCompatActivity {


    TaskDAO taskDAO;
    TaskDO taskDO;
    RoutineTaskDAO routineTaskDAO;
    RoutineTaskDO routineTaskDO;

    Button btnCancel, btnComplete;
    EditText etEmoji, etName, etSummary;
    NumberPicker npHour, npMinute, npSecond;
    ImageView ivDelete;

    int num = 0;
    int mode = 1;
    int category = 2;


    View rootView;
    EmojiPopup popup;
    InputMethodManager inputMethodManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorLight));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 1);
        taskDAO = new TaskDAO(this);
        routineTaskDAO = new RoutineTaskDAO(this);

        ViewSetting();
        EmojiKeyboardSetting();

        //새글
        if (mode == 1) {
            category = 2;
            ivDelete.setVisibility(View.GONE);


        } else if (mode == 2) { //수정
            ivDelete.setVisibility(View.VISIBLE);
            num = intent.getIntExtra("num", 0);

                taskDO = taskDAO.GetTaskDetail(num);
                etName.setText(taskDO.getName());
                etEmoji.setText(taskDO.getEmoji());
                npHour.setValue(taskDO.getHour());
                npMinute.setValue(taskDO.getMinute());
                npSecond.setValue(taskDO.getSecond());
                etSummary.setText(taskDO.getSummary());
                if (taskDO.getCategory() == 1) {
                    category = 1;
                    etName.setEnabled(false);
                    ivDelete.setVisibility(View.GONE);
                }

            btnComplete.setText("저장");
        }


    }

    void ViewSetting() {
        btnCancel = (Button) findViewById(R.id.taskmanager_btn_cancel);
        btnComplete = (Button) findViewById(R.id.taskmanager_btn_complete);
        etEmoji = (EditText) findViewById(R.id.taskmanager_emoji);
        etName = (EditText) findViewById(R.id.taskmanager_name);
        npHour = (NumberPicker) findViewById(R.id.taskmanager_timehour);
        npMinute = (NumberPicker) findViewById(R.id.taskmanager_timeminute);
        npSecond = (NumberPicker) findViewById(R.id.taskmanager_timesecond);
        ivDelete = (ImageView) findViewById(R.id.taskmanager_iv_delete);
        etSummary = (EditText) findViewById(R.id.taskmanager_et_summary);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);




        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mode == 1) {
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTaskManager.this);
                builder.setTitle("삭제");
                builder.setMessage("해당 할일을 정말로 삭제하시겠습니까? \n삭제후 복구가 불가능합니다.");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        taskDAO.DeleteTask(num);
                        Intent intent = new Intent();
                        intent.putExtra("tab", 2);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();

            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnComplete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().trim().equals("")) {
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


                TaskSave();

            }
        });



    }

    private void EmojiKeyboardSetting(){
        rootView = findViewById(R.id.taskmanager_root);
        popup = new EmojiPopup(rootView, ActivityTaskManager.this, getResources().getColor(R.color.colorAccent));
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        popup.setSizeForSoftKeyboard();


        popup.setOnSoftKeyboardOpenCloseListener(new EmojiPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {

                if(etEmoji.isFocused()) {
                    popup.setHeight(keyBoardHeight + 100);
                    popup.showAtBottom();
                }
            }

            @Override
            public void onKeyboardClose() {
                if (popup.isShowing())
                    popup.dismiss();
            }


        });

        etEmoji.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);

                }
            }
        });



        popup.setOnEmojiconClickedListener(new EmojiGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emojicon emojicon) {
                if (emojicon == null) {
                    return;
                }
                etEmoji.setText(emojicon.getEmoji());
                inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);


            }
        });

        popup.setOnEmojiconBackspaceClickedListener(new EmojiPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                etEmoji.setText("");
                inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);
            }
        });


        etEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popup.isShowing()){
                    inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);

                }else {

                    inputMethodManager.showSoftInput(etEmoji, InputMethodManager.SHOW_IMPLICIT);

                }
            }
        });

    }




    void TaskSave(){

        taskDAO = new TaskDAO(ActivityTaskManager.this);
        taskDO = new TaskDO(
                etName.getText().toString().trim(),
                npHour.getValue(),
                npMinute.getValue(),
                npSecond.getValue(),
                etEmoji.getText().toString().trim(),
                etSummary.getText().toString().trim(),
                category
        );

        Intent intent = new Intent();

        if (mode == 1) {
            taskDAO.InsertNewTask(taskDO);
            intent.putExtra("tab", 2);
        } else if (mode == 2) {
            taskDAO.UpdateTask(taskDO, num);
            if (category == 1) {
                intent.putExtra("tab", 1);
            } else {

                intent.putExtra("tab", 2);
            }
        }


        setResult(RESULT_OK, intent);
        finish();
    }


}