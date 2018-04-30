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
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.TaskListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.customDialog.ProjectMakeDialog;
import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskListVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserMemberVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserTaskList;
import kr.or.dgit.bigdata.projectmanagerapp.network.util.JsonParserTaskVO;
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
    final String TAG = "ProjectInnerFragment";
    private ProjectMakeDialog mCustomDialog;
    private MemberVO memVo;
    private String wcode;
    private ProjectVO projectVo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mRecyclerView = view.findViewById(R.id.task_list_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayout.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mProgressBar = ProgressDialog.show(this.getActivity(), "Wait", "Uploding...");
        wcode = getArguments().getString("wcode");
        Log.d(TAG, "wcode : " + wcode);
        memVo = getArguments().getParcelable("memVo");
        projectVo = getArguments().getParcelable("projectVo");

        myList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TaskListVO vo = new TaskListVO();
            vo.setName("네임");
            vo.setTlno(i);
            myList.add(vo);
        }

        HttpRequestTask mHttpRequestTask = new HttpRequestTask(view.getContext(), "GET", "", handler, 0);
        mHttpRequestTask.execute(RequestPref.pref + "/taskList/select/taskList/" + projectVo.getPno());

        tasks = new ArrayList<>();

        mAdapter = new TaskListAdapter(myList, tasks, mOnClickListener);
        Log.d(TAG, "리스트 추가");

        mRecyclerView.setAdapter(mAdapter);
        Log.d(TAG, "어뎁터 추가");

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

    private JsonParserUtil<HashMap<String, Object>> parserUtil = new JsonParserUtil<HashMap<String, Object>>() {
        @Override
        public HashMap<String, Object> itemParse(JSONObject order) throws JSONException {
            HashMap<String, Object> map = new HashMap<>();

            JSONArray memObj = order.getJSONArray("member");
            List<MemberVO> memberList = new ArrayList<>();
            Object obj = new JsonParserMemberVO().parsingJsonArray(memObj.toString());
            if (obj != null){
                memberList = (List<MemberVO>) obj;
            }
            map.put("memberList", memberList);

            JSONArray taskListObj = order.getJSONArray("taskList");
            List<TaskListVO> taskList = new ArrayList<>();
            obj =  new JsonParserTaskList().parsingJsonArray(taskListObj.toString());
            if (obj != null){
                taskList = (List<TaskListVO>) obj;
            }
            map.put("taskList", taskList);

            JSONArray tasksObj = order.getJSONArray("tasks");
            obj = new JsonParserTaskVO().parsingJsonArray(tasksObj.toString());
            List<TaskVO> tasksTemp = new ArrayList<>();
            if (obj != null){
                tasksTemp = (List<TaskVO>) obj;
            }
            map.put("tasks", tasksTemp);

            return map;
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String res = "";

            switch (msg.what) {
                case 0:
                    res = (String) msg.obj;
                    Log.d(TAG, res);
                    HashMap<String, Object> map = parserUtil.parsingJson(res);
                    mAdapter.add(map);
                    mProgressBar.dismiss();
                    break;
            }
        }
    };

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            ViewCompat.setElevation(view, 10.0f);
        }

    }
}
