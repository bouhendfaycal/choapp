package com.google.firebase.quickstart.database.survey;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SurveyViewModel extends ViewModel {
    private MutableLiveData<List<Integer>> points;

    public void setValue(int position, int value){
        if (points == null) {
            points = new MutableLiveData<List<Integer>>();
            List<Integer> arr = new ArrayList<>();
            for (int i = 0 ; i<13; i++){
                arr.add(0);
            }
            points.setValue(arr);
        }
        List<Integer> olderPoints = points.getValue();
        olderPoints.set(position,value);
        points.setValue(olderPoints);
    }

    public int getPoint(int position){
        List<Integer> olderPoints = points.getValue();
        return olderPoints.get(position);
    }

    public int getTotalPoint(){
        List<Integer> points_ = points.getValue();
        int size = points_.size();
        int sum = 0;
        for (int i=0; i<size; i++ ){
            sum += points_.get(i);
        }
        return sum;
    }
}