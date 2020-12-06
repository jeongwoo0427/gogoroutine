package com.example.gogoroutine.fragment_routines.dialog_routinetask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.R;

import java.util.ArrayList;

public class RoutineTaskDialogAdapter extends RecyclerView.Adapter<RoutineTaskDialogAdapter.ViewHolder> {

    ArrayList<RoutineTaskDialogAdapterDO> list = new ArrayList<RoutineTaskDialogAdapterDO>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_main_dialog,parent,false);
        RoutineTaskDialogAdapter.ViewHolder vh = new RoutineTaskDialogAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RoutineTaskDialogAdapterDO ado = list.get(position);
        holder.tvName.setText(ado.getsName());
        holder.tvTime.setText(ConvertTimeToString(ado.getHour(),ado.getMinute(),ado.getSecond()));
        holder.tvEmoji.setText(ado.getsEmoji());



    }

    @Override
    public int getItemCount() {
        return list.size();
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
            result += second+"초";
        }

        return result;
    }

    public void AddItem(int num,String name, int hour,int minute,int second,String emoji,String summary,int order){
        RoutineTaskDialogAdapterDO rdo = new RoutineTaskDialogAdapterDO();

        rdo.setiRoutineNum(num);
        rdo.setsName(name);
        rdo.setHour(hour);
        rdo.setMinute(minute);
        rdo.setSecond(second);
        rdo.setsEmoji(emoji);
        rdo.setsSummary(summary);
        rdo.setOrder(order);

        list.add(rdo);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvTime,tvEmoji;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_main_dialog_name);
            tvTime = itemView.findViewById(R.id.item_main_dialog_time);
            tvEmoji = itemView.findViewById(R.id.item_main_dialog_emoji);

        }
    }

}
