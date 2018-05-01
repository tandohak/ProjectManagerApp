package kr.or.dgit.bigdata.projectmanagerapp.customDialog;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.MemberListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserMemberVO;

/**
 * Created by ghddb on 2018-05-01.
 */

public class TaskAddMemberDialog extends Dialog {
    final String TAG = "TaskAddMemberDialog";
    private int pno;
    List<MemberVO> memList = new ArrayList<>();
    private Button addMemberCloseBtn;
    private View.OnClickListener mOnClickListener;
    private EditText mSearchView;
    private ListView mListView;
    private List<MemberVO> checkListMember;

    MemberListAdapter memSearchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.add_member_search_dialog_layout);

        addMemberCloseBtn = findViewById(R.id.addMemberCloseBtn);
        mSearchView = findViewById(R.id.search_member);
        mListView = findViewById(R.id.memList);
        addMemberCloseBtn.setOnClickListener(mOnClickListener);
        Log.d(TAG,checkListMember.size()+"AAA");

        HttpRequestTask mHttpRequestTask = new HttpRequestTask(getContext(), "GET", "", handler, 0);
        mHttpRequestTask.execute(RequestPref.pref + "/member/memAssList/" + pno);

        memSearchAdapter = new MemberListAdapter(getContext(),memList,checkListMember,R.layout.member_item_view);
        mListView.setAdapter(memSearchAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 ImageView imageView =  view.findViewById(R.id.checked_mem);
                MemberVO vo = (MemberVO) adapterView.getAdapter().getItem(position);
                Log.d(TAG,vo.toString());
                 int visibility = imageView.getVisibility();
                 if (visibility == View.GONE){
                     imageView.setVisibility(View.VISIBLE);
                     checkListMember.add(vo);
                     Log.d(TAG , checkListMember.size()+"");
                 }else{
                     imageView.setVisibility(View.GONE);
                     checkListMember.remove(vo);
                     Log.d(TAG , checkListMember.size()+"");
                 }
            }
        });

    }

    public List<MemberVO> getMemList() {
        return memList;
    }

    public List<MemberVO> getCheckListMember() {
        return checkListMember;
    }

    public void setCheckListMember(List<MemberVO> checkListMember) {
        this.checkListMember = checkListMember;
    }

    JsonParserMemberVO jsonParser = new JsonParserMemberVO();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String res = "";
            switch (msg.what) {
                case 0:
                    res = (String) msg.obj;
                    Log.d(TAG,res);
                    memList = jsonParser.parsingJsonArray(res);
                    memSearchAdapter.add(memList);
                    break;
            }
        }
    };



    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public TaskAddMemberDialog(Context context, View.OnClickListener singleListener, int pno, List<MemberVO> checkListMember) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mOnClickListener = singleListener;
        this.pno =pno;
        this.checkListMember = checkListMember;
    }

}

