package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.TaskVO;
import kr.or.dgit.bigdata.projectmanagerapp.network.HttpRequestTask;
import kr.or.dgit.bigdata.projectmanagerapp.network.RequestPref;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Position;

/**
 * Created by ghddb on 2018-04-29.
 */

public class TaskFinishAdapter extends RecyclerView.Adapter<TaskFinishAdapter.TaskViewHolder>{

    private Position mPosition;
    private List<TaskVO> myList;
    public TaskFinishAdapter(List<TaskVO> myList,Position mPosition) {
        this.mPosition = mPosition;
        this.myList = myList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_finish_layout,parent,false);
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
        CheckBox checkbox;
        public TaskViewHolder(final View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            checkbox = itemView.findViewById(R.id.checkbox_finish);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TaskVO vo = myList.get(getAdapterPosition());
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("taskno", vo.getTaskno());
                        obj.put("status", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HttpRequestTask mHttpRequestTask = new HttpRequestTask(itemView.getContext(), "PUT", obj.toString(), handler, 0);
                    mHttpRequestTask.execute(RequestPref.pref + "/taskList/update/taskStatus");
                }
            });
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String res = "";
            switch (msg.what) {
                case 0:
                    res = (String) msg.obj;

                    if(res.equals("success")){
                        mPosition.notifyObservers();
                    }
                    break;
            }
        }
    };

    public void  add(TaskVO vo){
        myList.add(vo);
        notifyDataSetChanged();
    }

    public void  add(List<TaskVO> list){
        myList = list;
        notifyDataSetChanged();
    }

}
