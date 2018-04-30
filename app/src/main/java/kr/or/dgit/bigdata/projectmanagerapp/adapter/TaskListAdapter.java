package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskListVO;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private List<TaskListVO> myList;
    private List<TaskVO> tasks;
    private TaskAdapter mAdapter;
    private TaskFinishAdapter mAdapter_finish;
    View.OnClickListener mOnClickListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager_finish;
    private List<TaskAdapter> mAdapterList = new ArrayList<>();

    public TaskListAdapter(List<TaskListVO> myList ,List<TaskVO> tasks, View.OnClickListener mOnClickListener) {
        this.myList = myList;
        this.tasks = tasks;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_layout,parent,false);
       /* view.setOnClickListener(mOnClickListener);
        view.findViewById(R.id.project_setting).setOnClickListener(mOnClickListener);*/
        return new TaskListViewHolder(view);
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

        mAdapter = new TaskAdapter(tempTasks,mOnClickListener);
        holder.mRecyclerView.setAdapter(mAdapter);
        mAdapterList.add(mAdapter);

        mAdapter_finish= new TaskFinishAdapter(tempTasks_finish,mOnClickListener);
        holder.mRecyclerView_finish.setAdapter(mAdapter_finish);

        if (tempTasks_finish.size() <= 0){
            holder.finish_text_box.setVisibility(View.GONE);
        }

    }

    private HashMap<String,List<TaskVO>> checkStatus(List<TaskVO> tasks, int tlno){
        HashMap<String,List<TaskVO>> map = new HashMap<>();
        List<TaskVO> tempTasks = new ArrayList<>();
        List<TaskVO> tempTasks_finish = new ArrayList<>();

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

    public void add(List<TaskListVO> list){
        myList = list;
        notifyDataSetChanged();
    }

    public void  add(HashMap<String,Object> map){

        if (map.get("taskList") != null){
            myList = (List<TaskListVO>) map.get("taskList");
            /*for (int i =0; i<myList.size(); i++){
                TaskListVO vo = myList.get(i);
                TaskAdapter tempAdapter = mAdapterList.get(i);
                HashMap<String,List<TaskVO>> map1 =  checkStatus((List<TaskVO>) map.get("tasks"), vo.getTlno());
                tempAdapter.add((List<TaskVO>) map1.get("tempTasks"));
            }*/
        }

       /*if(map.get("tasks")!=null){
            HashMap<String,List<TaskVO>> map1 =  checkStatus((List<TaskVO>) map.get("tasks"), );
            mAdapter.add((List<TaskVO>) map1.get("tempTasks"));
            mAdapter_finish.add((List<TaskVO>) map1.get("tempTasks_finish"));
        }*/

        notifyDataSetChanged();
    }

    protected class TaskListViewHolder extends RecyclerView.ViewHolder{
        TextView taskListName;
        RecyclerView mRecyclerView;
        RecyclerView mRecyclerView_finish;
        TextView finish_text_box;
        TaskAdapter mAdapter;
        TaskFinishAdapter mAdapter_finish;

        public TaskListViewHolder(final View itemView) {
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
        }

    }



}
