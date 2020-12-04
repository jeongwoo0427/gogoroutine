package com.example.gogoroutine.activity_routinemanager.Recycler;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperListener listener;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener) { this.listener = listener; }



    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int drag_flags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipe_flag = 0;
        return makeMovementFlags(drag_flags,swipe_flag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return listener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }



    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        switch(actionState){

            case ItemTouchHelper.ACTION_STATE_DRAG : Log.d("드래그","드래그가 시작되었습니다");
                break;

            case ItemTouchHelper.ACTION_STATE_IDLE : Log.d("드래그","드래그가 중지되었습니다");
            listener.changed();
                break;


        }
    }



    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

}
