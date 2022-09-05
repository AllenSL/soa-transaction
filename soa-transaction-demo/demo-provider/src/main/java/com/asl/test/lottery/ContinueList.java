package com.asl.test;

public class ContinueList {

    private double minEle;

    private double maxEle;

    private Integer index;

    public ContinueList(double minEle,double maxEle,Integer index){
        if(maxEle < minEle){
            throw new RuntimeException("。。。");
        }
         this.minEle = minEle;
         this.maxEle = maxEle;
         this.index = index;
    }

    public double getMinEle() {
        return minEle;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setMinEle(double minEle) {
        this.minEle = minEle;
    }

    public double getMaxEle() {
        return maxEle;
    }

    public void setMaxEle(double maxEle) {
        this.maxEle = maxEle;
    }

    public boolean isContainKey(double key){
        return key < maxEle && key > minEle;
    }

}
