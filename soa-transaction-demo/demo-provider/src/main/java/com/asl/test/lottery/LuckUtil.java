package com.asl.test.lottery;


import com.asl.test.ContinueList;

import java.util.*;

public class LuckUtil {

    /**
     * 概率集合
     */
   private List<ContinueList> lotteryList;

    /**
     * 初始值0.0
     */
   private double maxEle;

    /**
     * 构造抽奖集合
     */
   public LuckUtil(Map<Integer,Double> map){
       lotteryList = new ArrayList<>();
       ContinueList continueList = null;
       double minEle = 0d;
       Set<Map.Entry<Integer, Double>> entries = map.entrySet();
       for(Map.Entry<Integer, Double> doubleEntry:entries){
           Double d = doubleEntry.getValue();
           Integer key = doubleEntry.getKey();
           minEle = maxEle;
           maxEle = maxEle+d;
           continueList = new ContinueList(minEle,maxEle,key);
           lotteryList.add(continueList);
       }
   }

    /**
     * 抽奖操作
     * 返回:奖品的概率list集合中的下标
     * @return
     */
   public MdIndexRes randomColunmIndex(){
       MdIndexRes res = new MdIndexRes();
       Random random = new Random();
       double d = random.nextDouble() * maxEle;
       if(d == 0d){
           d = random.nextDouble()*maxEle;
       }
       for (int i = 0; i < lotteryList.size(); i++) {
           if(lotteryList.get(i).isContainKey(d)){
               res.setIndex(lotteryList.get(i).getIndex());
               break;
           }
       }
       return res;
   }

    public List<ContinueList> getLotteryList() {
        return lotteryList;
    }

    public void setLotteryList(List<ContinueList> lotteryList) {
        this.lotteryList = lotteryList;
    }

    public double getMaxEle() {
        return maxEle;
    }

    public void setMaxEle(double maxEle) {
        this.maxEle = maxEle;
    }
}
