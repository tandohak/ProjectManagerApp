package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;

/**
 * Created by ghddb on 2018-04-29.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder> {

    private List<ProjectVO> myList;

    public ProjectListAdapter(List<ProjectVO> myList) {
        this.myList = myList;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_layout,parent,false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        ProjectVO vo = myList.get(position);
        holder.projectName.setText(vo.getTitle());

        int status = vo.getStatus();
        String statusText = "";
        String colorString = "#ffffff";
        Drawable drawable = null;
        switch (status){
            case 1:
                statusText= "계획됨";
                drawable =  holder.status_text_view.getResources().getDrawable(R.drawable.state_back_1);
                break;

            case 2:
                statusText= "진행중";
                drawable =  holder.status_text_view.getResources().getDrawable(R.drawable.state_back_2);
                break;

            case 3:
                statusText= "완료됨";
                drawable =  holder.status_text_view.getResources().getDrawable(R.drawable.state_back_3);
                break;

            case 4:
                statusText= "보류";
                drawable =  holder.status_text_view.getResources().getDrawable(R.drawable.state_back_4);
                break;
            case 5:
                statusText= "취소";
                drawable =  holder.status_text_view.getResources().getDrawable(R.drawable.state_back_4);
                break;
            default:
                drawable = holder.status_text_view.getResources().getDrawable(R.drawable.state_back);
                colorString = "#9E9E9E";
                statusText= "상태 없음";
                break;
        }
        holder.status_text_view.setText(statusText);
        holder.status_text_view.setTextColor(Color.parseColor(colorString));
        holder.status_text_view.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    protected class ProjectViewHolder extends RecyclerView.ViewHolder{
        TextView projectName;
        FrameLayout frameLayout;
        TextView status_text_view;
        public ProjectViewHolder(final View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            frameLayout =itemView.findViewById(R.id.pj_setting);
            status_text_view = itemView.findViewById(R.id.status_text_view);

        }
    }

    public void  add(ProjectVO vo){
        myList.add(vo);
        notifyDataSetChanged();
    }

    public void  addList(List<ProjectVO> list){
        myList = list;
        notifyDataSetChanged();
    }
}
