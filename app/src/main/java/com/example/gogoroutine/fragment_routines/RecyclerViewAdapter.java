package com.example.gogoroutine.fragment_routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.activity_main.ActivityMain;
import com.example.gogoroutine.R;
import com.example.gogoroutine.fragment_routines.dialog_routinetask.RoutineTaskDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<RecyclerViewAdapterDO> mData = new ArrayList<RecyclerViewAdapterDO>();

    Context parentContext;

    FragmentRoutines fragmentRoutines;
    ActivityMain activityMain;



    public RecyclerViewAdapter(FragmentRoutines fragmentRoutines, ActivityMain activityMain){
        this.fragmentRoutines = fragmentRoutines;
        this.activityMain = activityMain;


    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //아이템레이아웃의 위치 사수

        parentContext = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_routine_recyclerview,parent,false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //글자값 적용
        //실제로 아이템에대한 정보를 적용하는 부분
        RecyclerViewAdapterDO rdo = mData.get(position);

            holder.tvName.setText(rdo.getName());
            // holder.tvStartTime.setText(convertTime(rdo.getStartHour(),rdo.getStartMinute())); 업데이트 전까지는 임시로 요일을 표시하도록 함
            holder.tvStartTime.setText(ConvertWeeks(rdo.getSelectedWeeks()));
            final int routineNum = rdo.getRoutineNum();

            holder.ivDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentRoutines_ItemDialog dialog = new FragmentRoutines_ItemDialog(parentContext, fragmentRoutines, activityMain);

                    dialog.showDialog(routineNum);

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    RoutineTaskDialog dialog = new RoutineTaskDialog(parentContext, fragmentRoutines, activityMain);
                    dialog.ShowDialog(routineNum);

                }
            });
        }




    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(int routineNum,String name,int isAlamEnable,int startHour,int startMinute, String selectedWeeks, int alamMode,int isSound,int isVibration){

        RecyclerViewAdapterDO rdo = new RecyclerViewAdapterDO();
        rdo.setRoutineNum(routineNum);
        rdo.setName(name);
        rdo.setIsAlamEnable(isAlamEnable);
        rdo.setStartHour(startHour);
        rdo.setStartMinute(startMinute);
        rdo.setSelectedWeeks(selectedWeeks);
        rdo.setAlameMode(alamMode);
        rdo.setIsSound(isSound);
        rdo.setIsVibration(isVibration);

        mData.add(rdo);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        //글자위치 사수
        //뷰 홀더를 오버라이드하여 위에서 아이템에 대한 객체들을 지정할 때 이 클래스를 사용하도록 함

        TextView tvName,tvStartTime;
        ImageView ivDialog;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_tv_name);
            tvStartTime = itemView.findViewById(R.id.item_tv_starttime);
            ivDialog = itemView.findViewById(R.id.item_iv_dialog);
            this.itemView = itemView;



        }
    }


    private String ConvertWeeks(String weeks){

        String convertedWeek="";

        if(weeks.contains("2")){
            convertedWeek +="월 ";
        }
        if(weeks.contains("3")){
            convertedWeek +="화 ";
        }
        if(weeks.contains("4")){
            convertedWeek +="수 ";
        }
        if(weeks.contains("5")){
            convertedWeek +="목 ";
        }
        if(weeks.contains("6")){
            convertedWeek +="금 ";
        }
        if(weeks.contains("7")){
            convertedWeek +="토 ";
        }
        if(weeks.contains("1")){
            convertedWeek +="일 ";
        }

        if(weeks.contains("1234567")){
            convertedWeek="매일";
        }

        return convertedWeek;
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
