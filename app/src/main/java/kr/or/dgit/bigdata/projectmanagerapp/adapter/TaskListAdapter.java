package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
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


        mAdapter_finish= new TaskFinishAdapter(tempTasks_finish,mOnClickListener);
        holder.mRecyclerView_finish.setAdapter(mAdapter_finish);
        if (tempTasks_finish.size() <= 0){
            holder.finish_text_box.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    protected class TaskListViewHolder extends RecyclerView.ViewHolder{
        TextView taskListName;
        RecyclerView mRecyclerView;
        RecyclerView mRecyclerView_finish;
        TextView finish_text_box;
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
        }
    }

    public void  add(TaskListVO vo){
        myList.add(vo);
        notifyDataSetChanged();
    }

}
