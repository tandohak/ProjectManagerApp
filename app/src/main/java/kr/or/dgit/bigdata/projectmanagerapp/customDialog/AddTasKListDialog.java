package kr.or.dgit.bigdata.projectmanagerapp.customDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.or.dgit.bigdata.projectmanagerapp.R;

/**
 * Created by ghddb on 2018-05-01.
 */

public class AddTasKListDialog extends Dialog {
    private TextView dateTextView;
    private Button addTaskListCloseBtn;
    private Button makeTaskList;
    private View.OnClickListener mOnClickListener;
    private EditText taskListname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_tasklist_dialog_layout);

        addTaskListCloseBtn = findViewById(R.id.addTaskListCloseBtn);
        makeTaskList = findViewById(R.id.make_taskList);
        taskListname = findViewById(R.id.task_list_name);

        makeTaskList.setOnClickListener(mOnClickListener);
        addTaskListCloseBtn.setOnClickListener(mOnClickListener);
    }
    public String getTaskListName(){
        return taskListname.getText().toString();
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public AddTasKListDialog(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mOnClickListener = singleListener;
    }

}

