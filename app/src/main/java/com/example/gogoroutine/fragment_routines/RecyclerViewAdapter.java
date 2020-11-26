package com.example.gogoroutine.fragment_routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.ActivityMain;
import com.example.gogoroutine.R;

import java.util.ArrayList;

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
        holder.tvStartTime.setText(convertTime(rdo.getStartHour(),rdo.getStartMinute()));
        final int routineNum = rdo.getRoutineNum();

        holder.btnDialog.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentRoutines_ItemDialog dialog = new FragmentRoutines_ItemDialog(parentContext,fragmentRoutines,activityMain);

                dialog.showDialog(routineNum);

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
        ImageButton btnDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_tv_name);
            tvStartTime = itemView.findViewById(R.id.item_tv_starttime);
            btnDialog = itemView.findViewById(R.id.item_btn_dialog);

        }
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
