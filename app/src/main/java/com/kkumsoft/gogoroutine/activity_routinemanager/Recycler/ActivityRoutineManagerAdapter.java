package com.kkumsoft.gogoroutine.activity_routinemanager.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_routinemanager.OnTaskItemClickListener;

import java.util.ArrayList;

public class ActivityRoutineManagerAdapter extends RecyclerView.Adapter<ActivityRoutineManagerAdapter.ViewHolder> implements ItemTouchHelperListener{





    OnTaskItemClickListener mListener=null;
    public ArrayList<ActivityRoutineManagerAdapterDO> list = new ArrayList<ActivityRoutineManagerAdapterDO>();
    public boolean isDeleteMode = false;





    public void setOnItemClickListener(OnTaskItemClickListener listener){
        this.mListener = listener;
    }


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

        final int iPosition = position;

        //포지션에 따라 뷰에 속성 적용
        ActivityRoutineManagerAdapterDO rdo = list.get(position);
        holder.tvName.setText(rdo.getsName());
        holder.tvTime.setText(ConvertTimeToString(rdo.getHour(),rdo.getMinute(),rdo.getSecond())); //분 변환 함수 제작 필요
        holder.tvEmoji.setText(rdo.getsEmoji());
        holder.btnDelete.setVisibility(isDeleteMode?View.VISIBLE:View.GONE);
        holder.btnDelete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                   // notifyDataSetChanged();
                    list.remove(iPosition);
                    notifyItemRemoved(iPosition);
                    notifyItemRangeChanged(iPosition, list.size());
                    //removes the row
                } catch (Exception e){
                    notifyDataSetChanged();
                    e.printStackTrace();
                }
            }
        });
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mListener != null) {
                    mListener.onItemClick(view, iPosition);
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
            result += second+"초";
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Object getItem(int position){return list.get(position);}

    public void addItem(int routineNum, String name, int hour,int minute, int second,String emoji,String summary,int order){

        ActivityRoutineManagerAdapterDO rdo = new ActivityRoutineManagerAdapterDO();
        rdo.setiRoutineNum(routineNum);
        rdo.setsName(name);
        rdo.setHour(hour);
        rdo.setMinute(minute);
        rdo.setSecond(second);
        rdo.setsEmoji(emoji);
        rdo.setsSummary(summary);
        rdo.setOrder(order);

        list.add(rdo);
    }

    @Override
    public boolean onItemMove(int form_position, int to_position) {

        ActivityRoutineManagerAdapterDO item = list.get(form_position);
        list.remove(form_position);
        list.add(to_position,item);
        notifyItemMoved(form_position, to_position);
     // routineManager.recyclerView.setAdapter(routineManager.adapter);


        return true;
    }

    @Override
    public void changed() {
        notifyDataSetChanged();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{
        //뷰 사수
        //뷰 홀더를 오버라이드하여 위에서 아이템에 대한 객체들을 지정할 때 이 클래스를 사용하도록 함

        View itemview;
        TextView tvName,tvTime,tvEmoji;
        Button btnDelete;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_routinemanager_name);
            tvTime = itemView.findViewById(R.id.item_routinemanager_time);
            tvEmoji = itemView.findViewById(R.id.item_routinemanager_emoji);
            btnDelete = itemView.findViewById(R.id.item_routinemanager_btn_delete);
            this.itemview = itemView;


        }
    }
}
