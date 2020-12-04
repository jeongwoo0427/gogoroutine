package com.example.gogoroutine.fragment_routines.dialog_routinetask;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogoroutine.R;
import com.example.gogoroutine.others.RoutineDAO;
import com.example.gogoroutine.others.RoutineTaskDAO;

public class RoutineTaskDialog {

    RoutineTaskDialogAdapter adapter;
    RoutineTaskDAO routineTaskDAO;

    RecyclerView recyclerView;
    TextView tvName, tvTime;

    Context context;

    private int iRoutineNum = 0;

    public RoutineTaskDialog(Context context) {
        this.context = context;
    }

    public void ShowDialog(int routineNum) {

        this.iRoutineNum = routineNum;

        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_main_dialog);
        dialog.show();

        ViewSetting(dialog);

    }


    void ViewSetting(Dialog dialog) {

        adapter = new RoutineTaskDialogAdapter();
        routineTaskDAO = new RoutineTaskDAO(context);
        recyclerView = dialog.findViewById(R.id.main_dialog_recycler);
        tvName = dialog.findViewById(R.id.main_dialog_tv_title);
        tvTime = dialog.findViewById(R.id.main_dialog_tv_time);

        tvName.setText(new RoutineDAO(context).getRoutineName(iRoutineNum));

        Cursor cursor = routineTaskDAO.GetRoutineTaskList(iRoutineNum);

        while(cursor.moveToNext()){
            adapter.AddItem(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
                    );
        }

        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setAdapter(adapter);


    }


}
