package kr.or.dgit.bigdata.projectmanagerapp.observer;

/**
 * Created by ghddb on 2018-04-30.
 */

public class Position extends Subject {
    private int position = -1;
    private int onClickId = -1;

    public void addPosition(int position, int onClickId){
        this.position = position;
        this.onClickId = onClickId;
        notifyObservers();
    }

    public int getPosition(){
        return position;
    }

    public  void resetPosition(){
        position = -1;
        onClickId = -1;
    }

    Object vo;
    public void addObject(Object vo){
        this.vo = vo;
    }

    public Object getObject(){
        return vo;
    }


    public int getOnClickId(){
        return onClickId;
    }
}
