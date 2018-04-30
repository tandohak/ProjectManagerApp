package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskVO> myList;
    View.OnClickListener mOnClickListener;

    public TaskAdapter(List<TaskVO> myList, View.OnClickListener mOnClickListener) {
        this.myList = myList;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout,parent,false);
        view.setOnClickListener(mOnClickListener);
        view.findViewById(R.id.checkbox_uncheck).setOnClickListener(mOnClickListener);
        return new TaskViewHolder(view);
    }



    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskVO vo = myList.get(position);
        holder.taskName.setText(vo.getTaskname());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    protected class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskName;

        public TaskViewHolder(final View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);

        }
    }

    public void  add(TaskVO vo){
        myList.add(vo);
        notifyDataSetChanged();
    }

    public void  add(List<TaskVO> list){
        myList = list;
        notifyDataSetChanged();
    }

}
