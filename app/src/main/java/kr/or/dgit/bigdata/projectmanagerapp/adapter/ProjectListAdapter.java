package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.ProjectVO;
import kr.or.dgit.bigdata.projectmanagerapp.observer.Position;

/**
 * Created by ghddb on 2018-04-29.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder>  {
    final String TAG = "ProjectListAdapter";
    private List<ProjectVO> myList;
    View.OnClickListener mOnClickListener;
    Position position;
    public ProjectListAdapter(List<ProjectVO> myList ,Position position) {
        this.myList = myList;
        this.position = position;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item_layout,parent,false);
        return new ProjectViewHolder(view,position);
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


    public ProjectVO getProjectVO(int position){
        return myList.get(position);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    protected class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView projectName;
        FrameLayout frameLayout;
        TextView status_text_view;
        Position position;
        public ProjectViewHolder(final View itemView, Position position) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            frameLayout =itemView.findViewById(R.id.pj_setting);
            status_text_view = itemView.findViewById(R.id.status_text_view);
            itemView.findViewById(R.id.project_item).setOnClickListener(this);
            itemView.findViewById(R.id.pj_setting).setOnClickListener(this);
            this.position= position;
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pj_setting:
                    position.addPosition(getAdapterPosition(),view.getId());
                    break;
                case R.id.project_item:
                    position.addPosition(getAdapterPosition(),view.getId());
                    break;
            }
        }

    }

    public void  add(ProjectVO vo){
        myList.add(vo);
        notifyDataSetChanged();
    }

    public void  addList(List<ProjectVO> list){
        myList = list;
        for (ProjectVO vo:myList) {
            Log.d(TAG,vo.toString());
        }
        notifyDataSetChanged();
    }
}
