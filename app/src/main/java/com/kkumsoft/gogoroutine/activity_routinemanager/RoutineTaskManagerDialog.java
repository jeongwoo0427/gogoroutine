package com.kkumsoft.gogoroutine.activity_routinemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;


import com.kkumsoft.gogoroutine.R;
import com.kkumsoft.gogoroutine.activity_routinemanager.Recycler.ActivityRoutineManagerAdapterDO;
import com.github.data5tream.emojilib.EmojiGridView;
import com.github.data5tream.emojilib.EmojiPopup;
import com.github.data5tream.emojilib.emoji.Emojicon;


public class RoutineTaskManagerDialog {

    Button btnCancel, btnComplete;
    EditText etEmoji, etName, etSummary;
    NumberPicker npHour, npMinute, npSecond;
    ImageView ivDelete;

    Context context;

    ActivityRoutineManagerAdapterDO rdo;


    View rootView;
    EmojiPopup popup;
    InputMethodManager inputMethodManager;


    public RoutineTaskManagerDialog(Context context){
        this.context = context;
    }

    public void ShowDialog(ActivityRoutineManagerAdapterDO routineTaskDO,int position){

        this.rdo = routineTaskDO;

        Dialog dialog = new Dialog(context, View.SYSTEM_UI_FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.activity_task_manager);
        dialog.getWindow().setWindowAnimations(R.style.AnimationPopupStyle);
        dialog.show();

        ViewSetting(dialog,position);
        EmojiKeyboardSetting(dialog);
        etEmoji.setText(rdo.getsEmoji());
        etName.setText(rdo.getsName());
        npHour.setValue(rdo.getHour());
        npMinute.setValue(rdo.getMinute());
        npSecond.setValue(rdo.getSecond());
        etSummary.setText(rdo.getsSummary());
        btnComplete.setText("저장");
        ivDelete.setVisibility(View.GONE);

    }



    void ViewSetting(final Dialog dialog,final int position) {
        btnCancel = (Button) dialog.findViewById(R.id.taskmanager_btn_cancel);
        btnComplete = (Button) dialog.findViewById(R.id.taskmanager_btn_complete);
        etEmoji = (EditText) dialog.findViewById(R.id.taskmanager_emoji);
        etName = (EditText) dialog.findViewById(R.id.taskmanager_name);
        npHour = (NumberPicker) dialog.findViewById(R.id.taskmanager_timehour);
        npMinute = (NumberPicker) dialog.findViewById(R.id.taskmanager_timeminute);
        npSecond = (NumberPicker) dialog.findViewById(R.id.taskmanager_timesecond);
        ivDelete = (ImageView) dialog.findViewById(R.id.taskmanager_iv_delete);
        etSummary = (EditText) dialog.findViewById(R.id.taskmanager_et_summary);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);

        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnComplete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etName.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("경고");
                    builder.setMessage("이름을 입력하세요");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                    etName.requestFocus();
                    return;
                }

                rdo.setsName(etName.getText().toString().trim());
                rdo.setsEmoji(etEmoji.getText().toString().trim());
                rdo.setHour(npHour.getValue());
                rdo.setMinute(npMinute.getValue());
                rdo.setSecond(npSecond.getValue());
                rdo.setsSummary(etSummary.getText().toString().trim());

                ActivityRoutineManager routineManager = (ActivityRoutineManager)context;
                routineManager.ApplyDialogResult(rdo,position);
                dialog.dismiss();

            }
        });

    }

    private void EmojiKeyboardSetting(Dialog dialog){
        rootView = dialog.findViewById(R.id.taskmanager_root);
        popup = new EmojiPopup(rootView, context, context.getResources().getColor(R.color.colorAccent));
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        popup.setSizeForSoftKeyboard();

        popup.setOnSoftKeyboardOpenCloseListener(new EmojiPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {

                if(etEmoji.isFocused()) {
                    // popup.setHeight(keyBoardHeight + 100);
                    //popup.showAtBottom();
                }
            }

            @Override
            public void onKeyboardClose() {

                popup.dismiss();

            }


        });


        etEmoji.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    //inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);
                    popup.dismiss();
                }else{
                    //inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);
                    popup.setWidth(rootView.getWidth());

                    popup.setHeight(rootView.getHeight());

                    popup.showAtBottom();




                }
            }
        });

        etEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup.setWidth(rootView.getWidth());

                popup.setHeight(rootView.getHeight());

                popup.showAtBottom();
            }
        });



        popup.setOnEmojiconClickedListener(new EmojiGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emojicon emojicon) {
                if (emojicon == null) {
                    return;
                }
                etEmoji.setText(emojicon.getEmoji());
                inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);
                popup.dismiss();


            }
        });

        popup.setOnEmojiconBackspaceClickedListener(new EmojiPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                etEmoji.setText("");
                inputMethodManager.hideSoftInputFromWindow(etEmoji.getWindowToken(), 0);
            }
        });
    }


}
