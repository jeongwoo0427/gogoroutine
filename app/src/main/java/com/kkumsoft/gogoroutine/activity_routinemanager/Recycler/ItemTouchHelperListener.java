package com.kkumsoft.gogoroutine.activity_routinemanager.Recycler;

public interface ItemTouchHelperListener {
    boolean onItemMove(int form_position, int to_position);
    void changed();
    //void onItemSwipe(int position);
}
