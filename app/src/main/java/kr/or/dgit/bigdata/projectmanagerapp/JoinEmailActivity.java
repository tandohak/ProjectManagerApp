package kr.or.dgit.bigdata.projectmanagerapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;

public class JoinEmailActivity extends BaseActivity {

    private final String TAG = "JoinEmailActivity";
    private LinearLayout sccessResultBox;
    private LinearLayout sendEmailBox;
    private TextView email_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_user);
        sendEmailBox = findViewById(R.id.sendEmailBox);
        sccessResultBox = findViewById(R.id.successResultBox);
        email_text = findViewById(R.id.email_text);
    }

    public void onClick(View v){
        if(v.getId() == R.id.sendEmailAuth){
            EditText text =  findViewById(R.id.email);
            String email = text.getText().toString();
            showProgressDialog();
            HttpRequestTask mHttpRequestTask = new HttpRequestTask(JoinEmailActivity.this ,"POST",email,handler,0);
            mHttpRequestTask.execute(RequestPref.pref+"/invite/emailAuth");
        }else if(v.getId() == R.id.backBtn){
            /*Intent intent = new Intent(JoinEmailActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);*/
            finish();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    hideProgressDialog();
                    String res = (String)msg.obj;
                    Log.d(TAG,"결과 : "+ res);
                    if(!res.equals("fail")){
                        sendEmailBox.setVisibility(View.GONE);
                        sccessResultBox.setVisibility(View.VISIBLE);
                        email_text.setText(res);
                    }else{
                        Toast.makeText(JoinEmailActivity.this, "이메일 전송에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
