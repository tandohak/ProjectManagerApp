package kr.or.dgit.bigdata.projectmanagerapp.fragments;


import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.ProjectListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserUtil;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workspace,container,false);

        mRecyclerView = view.findViewById(R.id.project_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mProgressBar = ProgressDialog.show(this.getActivity(), "Wait", "Uploding...");
        String wcode = getArguments().getString("wcode");
        Log.d(TAG,"wcode : " + wcode);

        myList = new ArrayList<>();

        Log.d(TAG,"시작");
         HttpRequestTask mHttpRequestTask = new HttpRequestTask(view.getContext() ,"GET","",handler,0);
        mHttpRequestTask.execute(RequestPref.pref+"/project/select/projectList/"+wcode);

        mAdapter = new ProjectListAdapter(myList);   Log.d(TAG,"리스트 추가");

        mRecyclerView.setAdapter(mAdapter);  Log.d(TAG,"어뎁터 추가");

        return view;
    }

    private JsonParserUtil<ProjectVO> parserUtil = new JsonParserProjectVO();

     private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String res = (String)msg.obj;
                    myList =   parserUtil.parsingJsonArray(res);
                    mProgressBar.dismiss();
                    mAdapter.addList(myList);
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
