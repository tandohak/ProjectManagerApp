package kr.or.dgit.bigdata.projectmanagerapp.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DGIT3-9 on 2018-04-30.
 */

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void detach(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        for(Observer o : observers){
            o.update();
        }
    }

}
