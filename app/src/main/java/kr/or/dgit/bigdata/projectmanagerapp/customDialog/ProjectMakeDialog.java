package kr.or.dgit.bigdata.projectmanagerapp.customDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.or.dgit.bigdata.projectmanagerapp.R;

/**
 * Created by DGIT3-9 on 2018-04-30.
 */

public class ProjectMakeDialog extends Dialog {
    private TextView mTitleView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    public EditText title;
    public EditText explanation;
    public RadioGroup radioGroup;
    public LinearLayout layout;
    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;
    public int selectTemplate = 0;
    public String visibility = "0";
    public ProjectMakeDialog(Context context, View.OnClickListener leftListener,
                             View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.project_make_dialog);
        layout = findViewById(R.id.dialog_layout);
        mLeftButton = (Button) findViewById(R.id.btn_left);
        mRightButton = (Button) findViewById(R.id.btn_right);
        mTitleView = findViewById(R.id.txt_title);
        // 제목과 내용을 생성자에서 셋팅한다.
        title = findViewById(R.id.title);
        explanation = findViewById(R.id.explanation);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.pj_private:
                        visibility = "0";
                        break;
                    case R.id.pj_public:
                        visibility = "1";
                        break;
                }
            }
        });

        findViewById(R.id.empty_btn).setOnClickListener(templateClicked);
        findViewById(R.id.day_btn).setOnClickListener(templateClicked);
        findViewById(R.id.person_btn).setOnClickListener(templateClicked);
        findViewById(R.id.depart_btn).setOnClickListener(templateClicked);
        findViewById(R.id.kanban_btn).setOnClickListener(templateClicked);

        // 클릭 이벤트 셋팅
        mLeftButton.setOnClickListener(mLeftClickListener);
        mRightButton.setOnClickListener(mRightClickListener);
    }

    int wrapArr[] = {R.id.empty_wrap,R.id.day_wrap,R.id.person_wrap,R.id.depart_wrap,R.id.kanban_wrap};
    int imgArr[] = {R.id.empty_img,R.id.day_img,R.id.person_img,R.id.depart_img,R.id.kanban_img};
    int drawArr[] = {R.drawable.empty,R.drawable.day,R.drawable.person,R.drawable.department,R.drawable.kanban};


    private View.OnClickListener templateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Drawable unSelect = view.getResources().getDrawable(R.drawable.template_border);
            Drawable selected = view.getResources().getDrawable(R.drawable.template_border_click);
            ImageView imageView;
            Drawable img_clicked;
            for(int i=0; i<wrapArr.length; i++){
                ProjectMakeDialog.this.findViewById(wrapArr[i]).setBackground(unSelect);
                imageView= ProjectMakeDialog.this.findViewById(imgArr[i]);
                img_clicked = view.getResources().getDrawable(drawArr[i]);
                imageView.setImageDrawable(img_clicked);
            }

            switch (view.getId()){
                case R.id.empty_btn:
                    selectTemplate = 0;
                    ProjectMakeDialog.this.findViewById(R.id.empty_wrap).setBackground(selected);
                    imageView= ProjectMakeDialog.this.findViewById(R.id.empty_img);
                    img_clicked = view.getResources().getDrawable(R.drawable.empty_b);
                    imageView.setImageDrawable(img_clicked);
                    ((ImageView)ProjectMakeDialog.this.findViewById(R.id.preview_img)).setImageDrawable(view.getResources().getDrawable(R.drawable.preview_blank));
                    break;
                case R.id.day_btn:
                    selectTemplate = 1;
                    ProjectMakeDialog.this.findViewById(R.id.day_wrap).setBackground(selected);
                    imageView= ProjectMakeDialog.this.findViewById(R.id.day_img);
                    img_clicked = view.getResources().getDrawable(R.drawable.day_b);
                    imageView.setImageDrawable(img_clicked);
                    ((ImageView)ProjectMakeDialog.this.findViewById(R.id.preview_img)).setImageDrawable(view.getResources().getDrawable(R.drawable.preview_weekly));
                    break;
                case R.id.person_btn:
                    selectTemplate = 2;
                    ProjectMakeDialog.this.findViewById(R.id.person_wrap).setBackground(selected);
                    imageView= ProjectMakeDialog.this.findViewById(R.id.person_img);
                    img_clicked = view.getResources().getDrawable(R.drawable.person_b);
                    imageView.setImageDrawable(img_clicked);
                    ((ImageView)ProjectMakeDialog.this.findViewById(R.id.preview_img)).setImageDrawable(view.getResources().getDrawable(R.drawable.preview_people));
                    break;
                case R.id.depart_btn:
                    selectTemplate = 3;
                    ProjectMakeDialog.this.findViewById(R.id.depart_wrap).setBackground(selected);
                    imageView= ProjectMakeDialog.this.findViewById(R.id.depart_img);
                    img_clicked = view.getResources().getDrawable(R.drawable.department_b);
                    imageView.setImageDrawable(img_clicked);
                    ((ImageView)ProjectMakeDialog.this.findViewById(R.id.preview_img)).setImageDrawable(view.getResources().getDrawable(R.drawable.preview_department));
                    break;
                case R.id.kanban_btn:
                    selectTemplate = 4;
                    ProjectMakeDialog.this.findViewById(R.id.kanban_wrap).setBackground(selected);
                    imageView= ProjectMakeDialog.this.findViewById(R.id.kanban_img);
                    img_clicked = view.getResources().getDrawable(R.drawable.kanban_b);
                    imageView.setImageDrawable(img_clicked);
                    ((ImageView)ProjectMakeDialog.this.findViewById(R.id.preview_img)).setImageDrawable(view.getResources().getDrawable(R.drawable.preview_todo));
                    break;

            }
        }
    };
}
