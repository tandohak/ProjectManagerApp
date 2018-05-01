package kr.or.dgit.bigdata.projectmanagerapp.fragments;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.adapter.TaskListAdapter;
import kr.or.dgit.bigdata.projectmanagerapp.customDialog.AddTasKListDialog;
import kr.or.dgit.bigdata.projectmanagerapp.customDialog.TaskAddMemberDialog;
import kr.or.dgit.bigdata.projectmanagerapp.customDialog.AddTaskDialog;
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
import kr.or.dgit.bigdata.projectmanagerapp.observer.Observer;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Position;

/**
 * Created by ghddb on 2018-04-29.
 */

public class ProjectInnerFragment extends Fragment implements Observer, View.OnClickListener {
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
    public Position mPosition;
    AddTaskDialog mAddTaskDialog;
    AddTasKListDialog addTasKListDialog;
    TaskAddMemberDialog mTaskAddMemberDialog;
    List<MemberVO> memList;
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

        HttpRequestTask mHttpRequestTask2 = new HttpRequestTask(getContext(), "GET", "", handler, 2);
        mHttpRequestTask2.execute(RequestPref.pref + "/member/memAssList/" + projectVo.getPno());

        tasks = new ArrayList<>();
        mPosition = new Position();
        mPosition.attach(this);
        mAdapter = new TaskListAdapter(myList, tasks, mPosition, mOnClickListener);
        Log.d(TAG, "리스트 추가");

        mRecyclerView.setAdapter(mAdapter);
        Log.d(TAG, "어뎁터 추가");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTasKListDialog = new AddTasKListDialog(getContext(), ProjectInnerFragment.this);
                addTasKListDialog.show();
            }
        });

        return view;
    }

    DatePickerDialog datePickerDialog;
    Calendar dateAndTime = Calendar.getInstance();
    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    List<MemberVO> checkListMember = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTaskCloseBtn:
                mAddTaskDialog.dismiss();
                checkListMember = new ArrayList<>();
                date = "";
                break;
            case R.id.addMember:
                mTaskAddMemberDialog = new TaskAddMemberDialog(getContext(), ProjectInnerFragment.this, projectVo.getPno(), checkListMember);
                mTaskAddMemberDialog.show();
                break;
            case R.id.addDate:
                Calendar c = Calendar.getInstance();
                int years = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "00:00";
                        dateAndTime.set(Calendar.YEAR, year);
                        dateAndTime.set(Calendar.MONTH, monthOfYear);
                        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateAndTime();
                    }
                }, years, month, day);
                datePickerDialog.show();

                break;
            case R.id.addMemberCloseBtn:
                checkListMember = mTaskAddMemberDialog.getCheckListMember();

                mTaskAddMemberDialog.dismiss();
                Log.d(TAG, checkListMember.size() + "");
                break;
            case R.id.make_task:
                Log.d(TAG,"MAEK _ TASK");
                JSONObject obj = new JSONObject();
                List<String> jobAssList = new ArrayList<>();

                for (MemberVO memberVO: checkListMember){
                    jobAssList.add(String.valueOf(memberVO.getMassno()));
                }

                for(MemberVO memberVO : memList){
                    if(memberVO.getMno() == memVo.getMno()){
                        memVo.setMassno(memberVO.getMassno());
                        memVo.setFirstName(memberVO.getFirstName());
                        memVo.setLastName(memberVO.getLastName());
                    }
                }

                TaskListVO vo = mAdapter.getItem(position);
                try {
                    Log.d(TAG,memVo.toString());
                    String taskname = mAddTaskDialog.getTaskName();
                    String endDate = mAddTaskDialog.getDate();
                    obj.put("jobAssList",jobAssList);
                    obj.put("taskname",taskname);
                    obj.put("endDate",endDate);
                    obj.put("writer", memVo.getFirstName() + " " + memVo.getLastName());
                    obj.put("massno",memVo.getMassno());
                    obj.put("tlno",vo.getTlno());
                    Log.d(TAG,memVo.getMassno()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HttpRequestTask mHttpRequestTask = new HttpRequestTask(getContext(), "POST", obj.toString(), handler, 1);
                mHttpRequestTask.execute(RequestPref.pref + "/taskList/register/taskmake");

                 break;
            case R.id.make_taskList:
                JSONObject obj2 = new JSONObject();
                String taskListName = addTasKListDialog.getTaskListName();
                if (taskListName==""|| taskListName == null){
                    Toast.makeText(getContext(), "업무리스트 이름을 지정해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int list_order= mAdapter.getItemCount();
                if(list_order<0){
                    list_order = 0;
                }
                try {

                    obj2.put("name",taskListName);
                    obj2.put("pno",projectVo.getPno());
                    obj2.put("list_order",list_order);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpRequestTask mHttpRequestTask3 = new HttpRequestTask(getContext(), "POST", obj2.toString(), handler, 3);
                mHttpRequestTask3.execute(RequestPref.pref + "/taskList/register/taskListmake");
                break;
        }
    }

    String date = "";

    private void updateDateAndTime() {
        date = mDateFormat.format(dateAndTime.getTime());
        mAddTaskDialog.updateDateTextView(date);
    }


   public View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           /* switch (v.getId()) {
                case R.id.addTask:
                    mAddTaskDialog = new AddTaskDialog(getContext(), ProjectInnerFragment.this);
                    mAddTaskDialog.show();
                    break;
            }*/
        }
    };

    private JsonParserUtil<HashMap<String, Object>> parserUtil = new JsonParserUtil<HashMap<String, Object>>() {
        @Override
        public HashMap<String, Object> itemParse(JSONObject order) throws JSONException {
            HashMap<String, Object> map = new HashMap<>();

            JSONArray memObj = order.getJSONArray("member");
            List<MemberVO> memberList = new ArrayList<>();
            Object obj = new JsonParserMemberVO().parsingJsonArray(memObj.toString());
            if (obj != null) {
                memberList = (List<MemberVO>) obj;
            }
            map.put("memberList", memberList);

            JSONArray taskListObj = order.getJSONArray("taskList");
            List<TaskListVO> taskList = new ArrayList<>();
            obj = new JsonParserTaskList().parsingJsonArray(taskListObj.toString());
            if (obj != null) {
                taskList = (List<TaskListVO>) obj;
            }
            map.put("taskList", taskList);

            JSONArray tasksObj = order.getJSONArray("tasks");
            obj = new JsonParserTaskVO().parsingJsonArray(tasksObj.toString());
            List<TaskVO> tasksTemp = new ArrayList<>();
            if (obj != null) {
                tasksTemp = (List<TaskVO>) obj;
            }
            map.put("tasks", tasksTemp);

            return map;
        }
    };

    public TaskListAdapter getAdapter() {
        return mAdapter;
    }

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
                case 1:
                    res = (String) msg.obj;
                    Log.d(TAG,res);
                    mAddTaskDialog.dismiss();
                    update();
                    break;
                case 2:
                    res = (String) msg.obj;
                    Log.d(TAG,res);
                    JsonParserMemberVO jsonParser = new JsonParserMemberVO();
                    memList = jsonParser.parsingJsonArray(res);
                    break;
                case 3:
                    res = (String)msg.obj;
                    Log.d(TAG,res);
                    update();
                    addTasKListDialog.dismiss();
                    break;
            }
        }
    };

    int position;
    @Override
    public void update() {
        position = mPosition.getPosition();
        int onclick= mPosition.getOnClickId();
        switch (onclick){
            case R.id.addTask:
                mAddTaskDialog = new AddTaskDialog(getContext(), ProjectInnerFragment.this);
                mAddTaskDialog.show();
                mPosition.resetPosition();
                break;
        }
        Log.d(TAG, "업데이트");
        HttpRequestTask mHttpRequestTask = new HttpRequestTask(getContext(), "GET", "", handler, 0);
        mHttpRequestTask.execute(RequestPref.pref + "/taskList/select/taskList/" + projectVo.getPno());
    }


    class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            ViewCompat.setElevation(view, 10.0f);
        }

    }
}
