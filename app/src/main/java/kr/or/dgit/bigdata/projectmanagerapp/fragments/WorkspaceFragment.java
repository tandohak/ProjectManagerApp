package kr.or.dgit.bigdata.projectmanagerapp.fragments;


import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.ProjectListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.customDialog.ProjectMakeDialog;
import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserUtil;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Position;

/**
 * Created by ghddb on 2018-04-29.
 */

public class WorkspaceFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ProjectListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ProjectVO> myList ;
    private ProgressDialog mProgressBar;
    final String TAG = "WorkspaceFragment";
    private ProjectMakeDialog mCustomDialog;
    private MemberVO memVo;
    private String wcode;
    public Position position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_workspace,container,false);


        mRecyclerView = view.findViewById(R.id.project_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mProgressBar = ProgressDialog.show(this.getActivity(), "Wait", "Uploding...");
        wcode = getArguments().getString("wcode");
        Log.d(TAG,"wcode : " + wcode);
        memVo = getArguments().getParcelable("memVo");

        myList = new ArrayList<>();

        Log.d(TAG,"시작");
        HttpRequestTask mHttpRequestTask = new HttpRequestTask(view.getContext() ,"GET","",handler,0);
        mHttpRequestTask.execute(RequestPref.pref+"/project/select/projectList/"+wcode);

        mAdapter = new ProjectListAdapter(myList,position);   Log.d(TAG,"리스트 추가");

        mRecyclerView.setAdapter(mAdapter);  Log.d(TAG,"어뎁터 추가");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new ProjectMakeDialog(v.getContext(),leftListener,rightListener);
                mCustomDialog.show();
            }
        });

        return view;
    }

    public ProjectVO getProjectVO(int position){
        return mAdapter.getProjectVO(position);
    }

    int dialogStack  =  0;

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Button btn = (Button) v;
            String title =  ((TextView)mCustomDialog.findViewById(R.id.title)).getText().toString();
            String explanation =  ((TextView)mCustomDialog.findViewById(R.id.explanation)).getText().toString();

            if(dialogStack == 0){

                if(title=="" ||title.equals("")){
                    Toast.makeText(WorkspaceFragment.this.getContext(), "프로젝트명을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialogStack = 1;
                btn.setText("프로젝트 만들기");
                mCustomDialog.findViewById(R.id.project_template).setVisibility(View.VISIBLE);
                mCustomDialog.findViewById(R.id.project_name).setVisibility(View.GONE);
            }else if(dialogStack == 1){
                //프로젝트 만들기
                List<Integer> memList = new ArrayList<>();
                memList.add(memVo.getMno());

                JSONObject order = new JSONObject();
                try {
                    order.put("wcode",wcode);
                    order.put("title",title);
                    order.put("explanation",explanation);
                    order.put("visibility",mCustomDialog.visibility);
                    order.put("memList",memList);
                    order.put("makerMno",memVo.getMno());
                    order.put("template",mCustomDialog.selectTemplate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                HttpRequestTask mHttpRequestTask = new HttpRequestTask(v.getContext() ,"POST",order.toString(),handler,1);
                mHttpRequestTask.execute(RequestPref.pref+"/project/make");
                mProgressBar.show();
            }

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getContext(), "오른쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();

            if(dialogStack == 0){
                dialogStack = 0;
                mCustomDialog.dismiss();
            }else if(dialogStack == 1){
                //이전으로 이동
                dialogStack = 0;
                ((Button) mCustomDialog.findViewById(R.id.btn_left)).setText("다음:템플릿 선택→");
                mCustomDialog.findViewById(R.id.project_template).setVisibility(View.GONE);
                mCustomDialog.findViewById(R.id.project_name).setVisibility(View.VISIBLE);
            }
        }
    };

    private JsonParserUtil<ProjectVO> parserUtil = new JsonParserProjectVO();

     private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String res = "";
            switch (msg.what){
                case 0:
                    res = (String)msg.obj;
                    Log.d(TAG,res);
                    myList =   parserUtil.parsingJsonArray(res);
                    mProgressBar.dismiss();
                    mAdapter.addList(myList);
                    break;
                case 1:
                    res = (String)msg.obj;
                    Log.d(TAG,res);
                    ProjectVO vo = parserUtil.parsingJson(res);
                    Log.d(TAG,vo.toString());
                    mAdapter.add(vo);
                    mProgressBar.dismiss();
                    mCustomDialog.dismiss();
                    break;
            }
        }
    };



    class MyItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            ViewCompat.setElevation(view,10.0f);

        }

    }
}
