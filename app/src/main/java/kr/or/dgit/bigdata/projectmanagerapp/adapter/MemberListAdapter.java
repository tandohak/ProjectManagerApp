package kr.or.dgit.bigdata.projectmanagerapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import kr.or.dgit.bigdata.projectmanagerapp.R;
import kr.or.dgit.bigdata.projectmanagerapp.domain.MemberVO;

/**
 * Created by DGIT3-9 on 2018-05-01.
 */

public class MemberListAdapter extends BaseAdapter {
    private final String TAG =  "MemberListAdapter";
    private LayoutInflater inflater;
    private List<MemberVO> data;
    private int layout;
    private List<MemberVO> checkListMember;

    public MemberListAdapter(Context context, List<MemberVO> data, List<MemberVO> checkListMember , int layout) {
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
        this.checkListMember = checkListMember;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MemberVO getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(List<MemberVO> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view =inflater.inflate(layout,parent,false);
        }

        ImageView imageView =  view.findViewById(R.id.checked_mem);
        MemberVO vo =data.get(position);
        for (MemberVO chckVo : checkListMember){
            if (vo.getMno() == chckVo.getMno()){
                Log.d(TAG,"SAME -- " + vo.toString());
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.GONE);
            }
        }
        TextView name=(TextView)view.findViewById(R.id.memName);
        name.setText(vo.getFirstName() + " "+ vo.getLastName());

        return view;
    }
}
