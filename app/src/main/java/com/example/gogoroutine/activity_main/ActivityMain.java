package com.example.gogoroutine.activity_main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gogoroutine.R;
import com.example.gogoroutine.activity_routinemanager.ActivityRoutineManager;
import com.example.gogoroutine.activity_routinemanager.AddTaskDialog.TaskAddDialog;
import com.example.gogoroutine.fragment_routines.FragmentRoutines;
import com.example.gogoroutine.fragment_status.FragmentStatus;

public class ActivityMain extends AppCompatActivity {

    public final static int REQUEST_TASKMANAGER_OF_DIALOG = 3;
    private final static int ROUTINEMANAGER_REQUEST_CODE = 2;
    private final static int CHOOSE_REQUEST_CODE = 1;

    private final static int CHOOSE_RESULT_ROUTINE = 1;
    private final static int CHOOSE_RESULT_GO =2;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentRoutines fragmentRountines;
    private FragmentStatus fragmentStatus;

    private TaskAddDialog taskDialog;


    ImageButton btnRoutines, btnStatus,btnAdd,btnAddRoutine,btnAddGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.FOREGROUND_SERVICE},1);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);


        btnAdd = (ImageButton)findViewById(R.id.main_btn_add);
        btnAddRoutine = (ImageButton)findViewById(R.id.main_choose_btn_add_routine);
        btnAddGo = (ImageButton)findViewById(R.id.main_choose_btn_add_go);

        btnAdd.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this,ActivityChoose.class);
                //건너갈 액티비티를 정해둔 인텐트 객체 선언

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(btnAddRoutine,"routineTransition");
                pairs[1] = new Pair<View,String>(btnAddGo,"goTransition");

                //액티비티에서 움직일 뷰와 트랜지션이름을 Pair배열에 담아둔다.

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ActivityMain.this, pairs);

                //액티비티 옵션을 적용하기 위해 ActivityOptions객체를 만들고 트랜지션 에니메이션에 대한 설정을 넣는다

                startActivityForResult(intent,CHOOSE_REQUEST_CODE,options.toBundle());
            }
        });

        fragmentManager = getSupportFragmentManager();

        fragmentRountines = new FragmentRoutines(ActivityMain.this);
        fragmentStatus = new FragmentStatus();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fl,fragmentRountines).commitAllowingStateLoss(); //프래그먼터 루틴으로 변경



    }


    public void onFragmentBtnClicked(View view){

        fragmentTransaction = fragmentManager.beginTransaction();


        switch(view.getId()){
            case R.id.main_btn_routines:
                fragmentTransaction.setCustomAnimations( R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right,R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);
                fragmentTransaction.replace(R.id.main_fl,fragmentRountines).commitAllowingStateLoss();
                break;

            case R.id.main_btn_status:
                fragmentTransaction.setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left, R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);
                fragmentTransaction.replace(R.id.main_fl,fragmentStatus).addToBackStack(null).commitAllowingStateLoss(); //뒤로가기 누를 시 이전 이전프래그먼트로 이동하게
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_REQUEST_CODE){

            if(resultCode==CHOOSE_RESULT_ROUTINE){
                //Toast.makeText(getApplicationContext(),"Routine",Toast.LENGTH_LONG).show(); //테스트코드
                Intent intent = new Intent(ActivityMain.this, ActivityRoutineManager.class);

                startActivityForResult(intent,ROUTINEMANAGER_REQUEST_CODE);

            }else if(resultCode==CHOOSE_RESULT_GO){

                taskDialog  = new TaskAddDialog(ActivityMain.this,0);
                taskDialog.showDialog();


            }

        }

        if(requestCode == ROUTINEMANAGER_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                //Toast.makeText(getApplicationContext(), "정상적으로 추가되었습니다.", Toast.LENGTH_LONG).show(); //테스트코드


                fragmentRountines.DisplayRoutineList();


            }

        }else if(requestCode == REQUEST_TASKMANAGER_OF_DIALOG){

            if(resultCode == RESULT_OK){
                taskDialog.setSwitchButton(data.getIntExtra("tab",2));

            }

        }
    }



}