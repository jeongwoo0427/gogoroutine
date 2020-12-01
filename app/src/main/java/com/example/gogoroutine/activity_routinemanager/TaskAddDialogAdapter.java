package com.example.gogoroutine.activity_routinemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.R;

import java.util.ArrayList;

public class TaskAddDialogAdapter extends RecyclerView.Adapter<TaskAddDialogAdapter.ViewHolder> {

    ArrayList<TaskAddDialogAdapterDO> list = new ArrayList<TaskAddDialogAdapterDO>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_addtaskdialog,parent,false);

        TaskAddDialogAdapter.ViewHolder vh = new TaskAddDialogAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TaskAddDialogAdapterDO tDo = list.get(position);

        holder.tvName.setText(tDo.getName());
        holder.tvTime.setText(tDo.getTime()+"분");
        holder.tvSummary.setText(tDo.getSummary());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addItem(int tasknum,String name, int time,String emoji,String summary, int category){

        TaskAddDialogAdapterDO tDo = new TaskAddDialogAdapterDO();
        tDo.setTaskNum(tasknum);
        tDo.setName(name);
        tDo.setTime(time);
        tDo.setEmoji(emoji);
        tDo.setSummary(summary);
        tDo.setCategory(category);

        list.add(tDo);

    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvTime,tvSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_addtaskdialog_name);
            tvTime = itemView.findViewById(R.id.item_addtaskdialog_time);
            tvSummary = itemView.findViewById(R.id.item_addtaskdialog_summary);

        }
    }

}
