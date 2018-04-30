package kr.or.dgit.bigdata.projectmanagerapp.observer;

/**
 * Created by ghddb on 2018-04-30.
 */

public class Position extends Subject {
    private int position;
    private int onClickId;

    public void addPosition(int position, int onClickId){
        this.position = position;
        this.onClickId = onClickId;
        notifyObservers();
    }

    public int getPosition(){
        return position;
    }

    public int getOnClickId(){
        return onClickId;
    }
}
