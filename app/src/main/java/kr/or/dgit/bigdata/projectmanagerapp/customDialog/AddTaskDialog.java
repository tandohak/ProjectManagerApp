package kr.or.dgit.bigdata.projectmanagerapp.customDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.or.dgit.bigdata.projectmanagerapp.R;

/**
 * Created by ghddb on 2018-05-01.
 */

public class AddTaskDialog extends Dialog {
    private TextView dateTextView;
    private Button addMember;
    private Button addDate;
    private Button addTaskCloseBtn;
    private Button makeTask;
    private View.OnClickListener mOnClickListener;
    private EditText taskname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog_layout);

        addMember = findViewById(R.id.addMember);
        addDate= findViewById(R.id.addDate);
        addTaskCloseBtn = findViewById(R.id.addTaskCloseBtn);
        makeTask = findViewById(R.id.make_task);
        dateTextView = findViewById(R.id.dateTextView);
        taskname = findViewById(R.id.taskname);

        dateTextView.setText("");

        makeTask.setOnClickListener(mOnClickListener);
        addMember.setOnClickListener(mOnClickListener);
        addDate.setOnClickListener(mOnClickListener);
        addTaskCloseBtn.setOnClickListener(mOnClickListener);


    }
    public String getTaskName(){
        return taskname.getText().toString();
    }

    public String getDate(){
        return dateTextView.getText().toString();
    }
    public  void  updateDateTextView(String text){
        dateTextView.setText(text);
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public AddTaskDialog(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mOnClickListener = singleListener;
    }

}

