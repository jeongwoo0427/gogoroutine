package com.example.gogoroutine.activity_routinemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.R;
import com.example.gogoroutine.fragment_routines.RecyclerViewAdapter;

import java.util.ArrayList;

public class ActivityRoutineManagerAdapter extends RecyclerView.Adapter<ActivityRoutineManagerAdapter.ViewHolder> {


    ArrayList<ActivityRoutineManagerAdapterDO> list = new ArrayList<ActivityRoutineManagerAdapterDO>();

    @NonNull
    @Override
    public ActivityRoutineManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_routinemanager_recyclerview,parent,false);
        ActivityRoutineManagerAdapter.ViewHolder vh = new ActivityRoutineManagerAdapter.ViewHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ActivityRoutineManagerAdapter.ViewHolder holder, int position) {

        //포지션에 따라 뷰에 속성 적용
        ActivityRoutineManagerAdapterDO rdo = list.get(position);
        holder.tvName.setText(rdo.getsName());
        holder.tvTime.setText(rdo.getiTime()+"분"); //분 변환 함수 제작 필요
        holder.tvSummary.setText(rdo.getsSummary());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(int routineNum, int taskNum, String name, int time,String emoji,String summary){

        ActivityRoutineManagerAdapterDO rdo = new ActivityRoutineManagerAdapterDO();
        rdo.setiRoutineNum(routineNum);
        rdo.setiTaskNum(taskNum);
        rdo.setsName(name);
        rdo.setiTime(time);
        rdo.setsEmoji(emoji);
        rdo.setsSummary(summary);

        list.add(rdo);


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //뷰 사수
        //뷰 홀더를 오버라이드하여 위에서 아이템에 대한 객체들을 지정할 때 이 클래스를 사용하도록 함

        TextView tvName,tvTime,tvSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_routinemanager_name);
            tvTime = itemView.findViewById(R.id.item_routinemanager_time);
            tvSummary = itemView.findViewById(R.id.item_routinemanager_summary);

        }
    }
}
