package com.kkumsoft.gogoroutine.activity_routinemanager.AddTaskDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_routinemanager.ActivityRoutineManager;
import com.kkumsoft.gogoroutine.activity_routinemanager.OnTaskItemClickListener;

import java.util.ArrayList;

public class TaskAddDialogAdapter extends RecyclerView.Adapter<TaskAddDialogAdapter.ViewHolder> {

    OnTaskItemClickListener listener = null;

    ArrayList<TaskAddDialogAdapterDO> list = new ArrayList<TaskAddDialogAdapterDO>();

    Context context;

    int from = 1;//기본은 루틴메이져에서 호출한 것으로 가정

    public void setOnClickListener(OnTaskItemClickListener listener){
        this.listener = listener;
    }

    public TaskAddDialogAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_addtaskdialog, parent, false);

        TaskAddDialogAdapter.ViewHolder vh = new TaskAddDialogAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TaskAddDialogAdapterDO tDo = list.get(position);
        final int iPosition = position;

        if (tDo.getSummary().trim().equals("")) {
            holder.tvSummary.setVisibility(View.GONE);
        } else {
            holder.tvSummary.setText(tDo.getSummary());
        }

        holder.tvName.setText(tDo.getName());
        holder.tvTime.setText(ConvertTimeToString(tDo.getHour(),tDo.getMinute(),tDo.getSecond()));

        holder.tvEmoji.setText(tDo.getEmoji());

        if(from == 1){
            holder.ivAdd.setVisibility(View.VISIBLE);
        }else{
            holder.ivAdd.setVisibility(View.GONE);
        }

        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //여기 아이템 +버튼 누를때 작동되는 영역
                ActivityRoutineManager activityRoutineManager = (ActivityRoutineManager) context;
                activityRoutineManager.adapter.addItem(-1,  tDo.getName(), tDo.getHour(), tDo.getMinute(), tDo.getSecond(), tDo.getEmoji(), tDo.getSummary(), -1);

                activityRoutineManager.recyclerView.setAdapter(activityRoutineManager.adapter);


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener !=null){
                    listener.onItemClick(view,iPosition);
                }
            }
        });

    }

    public String ConvertTimeToString(int hour,int minute,int second){
        String result ="";

        if(hour>0){
            result += hour+"시간 ";
        }
        if(minute>0){
            result += minute+"분 ";
        }
        if(second>0){
            result += second+"초 ";
        }

        return result;
    }

    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addItem(int tasknum, String name, int hour, int minute, int second, String emoji, String summary, int category) {

        TaskAddDialogAdapterDO tDo = new TaskAddDialogAdapterDO();
        tDo.setTaskNum(tasknum);
        tDo.setName(name);
        tDo.setHour(hour);
        tDo.setMinute(minute);
        tDo.setSecond(second);
        tDo.setEmoji(emoji);
        tDo.setSummary(summary);
        tDo.setCategory(category);

        list.add(tDo);

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView tvName, tvTime, tvSummary, tvEmoji;
        ImageView ivAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_addtaskdialog_name);
            tvTime = itemView.findViewById(R.id.item_addtaskdialog_time);
            tvSummary = itemView.findViewById(R.id.item_addtaskdialog_summary);
            tvEmoji = itemView.findViewById(R.id.item_addtaskdialog_emoji);
            ivAdd = itemView.findViewById(R.id.addtaskdialog_add);
            this.itemView = itemView;

        }
    }

}
