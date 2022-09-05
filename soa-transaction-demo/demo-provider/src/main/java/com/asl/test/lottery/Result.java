package com.asl.test.lottery;

public class Result {

    /**
     * 中奖区间下标
     */
    private Integer index;

    private Integer sumTime;

    private Integer time;

    private Double initProbability;

    private Double realProbability;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSumTime() {
        return sumTime;
    }

    public void setSumTime(Integer sumTime) {
        this.sumTime = sumTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getInitProbability() {
        return initProbability;
    }

    public void setInitProbability(Double initProbability) {
        this.initProbability = initProbability;
    }

    public Double getRealProbability() {
        return realProbability;
    }

    public void setRealProbability(Double realProbability) {
        this.realProbability = realProbability;
    }

    public Result(Integer index, Integer sumTime, Integer time, Double initProbability, Double realProbability) {
        this.index = index;
        this.sumTime = sumTime;
        this.time = time;
        this.initProbability = initProbability;
        this.realProbability = realProbability;
    }

    @Override
    public String toString() {
        return "Result{" +
                "index=" + index +
                ", sumTime=" + sumTime +
                ", time=" + time +
                ", initProbability=" + initProbability +
                ", realProbability=" + realProbability +
                '}';
    }
}
