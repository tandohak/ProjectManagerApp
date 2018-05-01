package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskListVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Position;

/**
 * Created by ghddb on 2018-04-29.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>{
    private Position mPosition = new Position();
    private final String TAG = "TaskListAdapter";
    private List<TaskListVO> myList;
    private List<TaskVO> tasks;
    private TaskAdapter mAdapter;
    private TaskFinishAdapter mAdapter_finish;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager_finish;
    List<TaskVO> tempTasks = new ArrayList<>();
    List<TaskVO> tempTasks_finish = new ArrayList<>();
    private View.OnClickListener onClickListener;

    public TaskListAdapter(List<TaskListVO> myList, List<TaskVO> tasks, Position mPosition, View.OnClickListener onClickListener) {
        this.myList = myList;
        this.tasks = tasks;
        this.mPosition = mPosition;
        this.onClickListener = onClickListener;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_layout,parent,false);
        return new TaskListViewHolder(view,onClickListener);
    }

    @Override
    public void onBindViewHolder(TaskListViewHolder holder, int position) {
        TaskListVO vo = myList.get(position);
        holder.taskListName.setText(vo.getName());

        List<TaskVO> tempTasks = new ArrayList<>();
        List<TaskVO> tempTasks_finish = new ArrayList<>();

        for(TaskVO task : tasks){
            if(task.getTlno() == vo.getTlno()){
                if(task.getStatus() == 0){
                    tempTasks.add(task);
                }else if(task.getStatus() == 1){
                    tempTasks_finish.add(task);
                }
            }
        }
        mAdapter = new TaskAdapter(tempTasks,mPosition);
        holder.mRecyclerView.setAdapter(mAdapter);

        mAdapter_finish= new TaskFinishAdapter(tempTasks_finish,mPosition);
        holder.mRecyclerView_finish.setAdapter(mAdapter_finish);

        if (tempTasks_finish.size() <= 0){
            holder.finish_text_box.setVisibility(View.GONE);
        }else{
            holder.finish_text_box.setVisibility(View.VISIBLE);
        }

    }

    private HashMap<String,List<TaskVO>> checkStatus(List<TaskVO> tasks, int tlno){
        HashMap<String,List<TaskVO>> map = new HashMap<>();


        for(TaskVO task : tasks){
            if(task.getTlno() == tlno){
                if(task.getStatus() == 0){
                    tempTasks.add(task);
                }else if(task.getStatus() == 1){
                    tempTasks_finish.add(task);
                }
            }
        }

        map.put("tempTasks",tempTasks);
        map.put("tempTasks_finish",tempTasks_finish);
        return map;
    }
    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void  add(TaskListVO vo){
        myList.add(vo);
        notifyDataSetChanged();
    }

    public TaskVO getfinishTask(int position) {
        return tempTasks_finish.get(position);
    }

    public TaskVO getTask(int position) {
        return tempTasks.get(position);
    }

    public void add(List<TaskListVO> list){
        myList = list;
        notifyDataSetChanged();
    }


    public void  add(HashMap<String,Object> map){

        if (map.get("taskList") != null){
            myList = (List<TaskListVO>) map.get("taskList");
           for (int i =0; i<myList.size(); i++){
               TaskListVO vo = myList.get(i);
               HashMap<String,List<TaskVO>> map1 =  checkStatus((List<TaskVO>) map.get("tasks"), vo.getTlno());
               tempTasks = map1.get("tempTasks");
               tempTasks_finish = map1.get("tempTasks_finish");
            }
        }

        if(map.get("tasks")!=null){
            tasks = (List<TaskVO>) map.get("tasks");
        }

        notifyDataSetChanged();
    }

    public void updateTask(TaskVO taskVO) {
        tasks.remove(taskVO);
        tasks.add(taskVO);
        notifyDataSetChanged();
    }


    protected class TaskListViewHolder extends RecyclerView.ViewHolder{
        TextView taskListName;
        RecyclerView mRecyclerView;
        RecyclerView mRecyclerView_finish;
        TextView finish_text_box;
        TaskAdapter mAdapter;
        TaskFinishAdapter mAdapter_finish;
        Button taskAddBtn;
        Button taskListSetting;

        public TaskListViewHolder(final View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            finish_text_box = itemView.findViewById(R.id.finish_text_box);
            taskListName = itemView.findViewById(R.id.taskListName);
            mRecyclerView = itemView.findViewById(R.id.task_item_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(itemView.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView_finish = itemView.findViewById(R.id.task_finish_view);
            mRecyclerView_finish.setHasFixedSize(true);
            mLayoutManager_finish = new LinearLayoutManager(itemView.getContext());
            mRecyclerView_finish.setLayoutManager(mLayoutManager_finish);

            mAdapter = (TaskAdapter) mRecyclerView_finish.getAdapter();
            mAdapter_finish = (TaskFinishAdapter) mRecyclerView_finish.getAdapter();

            taskAddBtn = itemView.findViewById(R.id.addTask);
            taskAddBtn.setOnClickListener(onClickListener);
            taskListSetting = itemView.findViewById(R.id.task_list_setting);
            taskListSetting.setOnClickListener(onClickListener);

        }

    }



}
