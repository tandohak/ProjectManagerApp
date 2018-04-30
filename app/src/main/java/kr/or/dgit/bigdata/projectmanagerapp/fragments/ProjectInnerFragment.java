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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.ProjectListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.TaskListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.customDialog.ProjectMakeDialog;
import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskListVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserUtil;

/**
 * Created by ghddb on 2018-04-29.
 */

public class ProjectInnerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TaskListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<TaskListVO> myList;
    private List<TaskVO> tasks;
    private ProgressDialog mProgressBar;
    final String TAG = "WorkspaceFragment";
    private ProjectMakeDialog mCustomDialog;
    private MemberVO memVo;
    private String wcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task_list,container,false);

        mRecyclerView = view.findViewById(R.id.task_list_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mProgressBar = ProgressDialog.show(this.getActivity(), "Wait", "Uploding...");
//        wcode = getArguments().getString("wcode");
//        Log.d(TAG,"wcode : " + wcode);
//        memVo = getArguments().getParcelable("memVo");

        myList = new ArrayList<>();
        for(int i=0; i<5; i++){
            TaskListVO vo = new TaskListVO();
            vo.setName("네임");
            vo.setTlno(i);
            myList.add(vo);
        }

       /* HttpRequestTask mHttpRequestTask = new HttpRequestTask(view.getContext() ,"GET","",handler,0);
        mHttpRequestTask.execute(RequestPref.pref+"/project/select/projectList/"+wcode);*/

       tasks = new ArrayList<>();
       for(int i=0; i<3; i++){
           TaskVO vo = new TaskVO();
           vo.setTlno(0);
           vo.setTaskname("task item " + i);
           tasks.add(vo);
       }
        for(int i=0; i<3; i++){
            TaskVO vo = new TaskVO();
            vo.setTlno(2);
            vo.setTaskname("task item " + i);
            vo.setStatus(1);
            tasks.add(vo);
        }

        mAdapter = new TaskListAdapter(myList,tasks,mOnClickListener);   Log.d(TAG,"리스트 추가");

        mRecyclerView.setAdapter(mAdapter);  Log.d(TAG,"어뎁터 추가");

        /*FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new ProjectMakeDialog(v.getContext(),leftListener,rightListener);
                mCustomDialog.show();
            }
        });
*/
        return view;
    }

    public View.OnClickListener mOnClickListener;

   /* private JsonParserUtil<ProjectVO> parserUtil = new JsonParserProjectVO();

     private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String res = "";

            switch (msg.what){
                case 0:
                    res = (String)msg.obj;
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
    };*/

    class MyItemDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            ViewCompat.setElevation(view,10.0f);

        }

    }
}
