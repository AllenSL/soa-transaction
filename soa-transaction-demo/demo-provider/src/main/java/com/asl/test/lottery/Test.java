package com.asl.test.lottery;

import java.util.*;

public class Test {

    static final int TIME = 395;

    /**
     * 概率集合
     */
    static Map<Integer,Double> glCache = new HashMap<>();

    /**
     * 奖品个数集合
     */
    static Map<Integer,Integer> jpCache = new HashMap<>();

    public static void main(String[] args) {
        glCache.put(0,0.1d);
        glCache.put(1,5.1d);
        glCache.put(2,8.4d);
        glCache.put(3,15.4d);
        glCache.put(4,21.5d);
        glCache.put(5,49.5d);

        jpCache.put(0,5);
        jpCache.put(1,20);
        jpCache.put(2,30);
        jpCache.put(3,40);
        jpCache.put(4,100);
        jpCache.put(5,200);

        //对结果进行概率统计集合
        List<Double> list = new ArrayList<>();
        list.add(0.1d);
        list.add(5.1d);
        list.add(8.4d);
        list.add(15.4d);
        list.add(21.5d);
        list.add(49.5d);

        LuckUtil util = new LuckUtil(glCache);
        Map<Integer,Integer> map = new HashMap<>();
        double maxEle = util.getMaxEle();
        for (int i = 0; i < TIME; i++) {
            MdIndexRes res = util.randomColunmIndex();
            Integer index = res.getIndex();
            System.out.println("本次抽奖结果:"+index);
            if(glCache.containsKey(index) && map.containsKey(index)){
                Integer count = map.get(index)+1;
                if(jpCache.get(index) <= count){
                    System.out.println("抽完了: "+index);
                    //奖品抽完，重新计算中奖概率
                  util = reCalculate(index);
                }
                map.put(index,count);
            }else {
                map.put(index,1);
            }
        }

        for (int i = 0; i < list.size(); i++) {
             double probability = list.get(i)/maxEle;
             list.set(i,probability);
        }
        itertorMap(map,list);
    }


    static LuckUtil reCalculate(Integer key){
        glCache.remove(key);
        jpCache.remove(key);
        Set<Map.Entry<Integer,Integer>> entries = jpCache.entrySet();
        int sum = 0;
        for(Map.Entry<Integer,Integer> map:entries){
           sum+=map.getValue();
        }
        Set<Map.Entry<Integer,Double>> g = glCache.entrySet();
        Map<Integer,Double> newMap = new HashMap<>();
        for(Map.Entry<Integer, Double> map:g){
            newMap.put(map.getKey(),map.getValue()/sum);
        }
        return new LuckUtil(newMap);
    }


    private static void itertorMap(Map<Integer, Integer> map, List<Double> list) {
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for(Map.Entry<Integer, Integer> m:entries){
            Integer index = m.getKey();
            Integer time = m.getValue();
            Result result = null;
            try {
                result = new Result(index,TIME,time,list.get(index),(double)time/TIME);
            } catch (Exception e) {
                System.out.println(index);
                e.printStackTrace();
            }
            System.out.println(result);
        }
    }
}
