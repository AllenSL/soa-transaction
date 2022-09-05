package com.asl.test.test1;


import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuanyanzhao
 * @date 2022/8/9 10:30 上午
 * @description:
 */
public class Lottery {
    //[5,0.1%],[20,5.1%],[30,8.4%],[49,15.4%],[100,21.5%],[200,49.5%]
    /**
     * 3、在一个抽奖活动中，用户抽奖必中，一共有6种奖品，每种奖品得数量和中奖概率分别为
     * [5,0.1%],[20,5.1%],[30,8.4%],[40,15.4%],[100,21.5%],[200,49.5%]
     * 1         2          3          4        5           6
     * 个数   10,510,840,1540,2150,4950
     * 索引   10,520,1360,2900,5050,10000
     * 如果某一次抽奖抽中一类库存耗光的奖品后，则需要去除当前奖品后重新用剩余奖品得权重再算奖。
     * 请写一段代码来模拟这个抽奖，要求实际中奖概率可以基本符合设置要求
     */
    //奖项剩余数量
    private static HashMap<Integer, Integer> map = new HashMap<>();

    //标尺
    private static int[] valueArray = null;

    //元素总量
    private static Integer total = 0;

    //统计使用
    private static AtomicInteger a1 = new AtomicInteger(0);
    private static AtomicInteger a2 = new AtomicInteger(0);
    private static AtomicInteger a3 = new AtomicInteger(0);
    private static AtomicInteger a4 = new AtomicInteger(0);
    private static AtomicInteger a5 = new AtomicInteger(0);
    private static AtomicInteger a6 = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        map.put(1, 5);
        map.put(2, 20);
        map.put(3, 30);
        map.put(4, 40);
        map.put(5, 100);
        map.put(6, 200);
        firstBuildValueArray();

        try {
            for (int i = 0; i < 7200; i++) {
                Integer integer = chouJiang();
                decreAndRebuild(integer);
                System.out.println("抽中" + integer + "等奖");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("===================统计分割线=====================");
        System.out.println(1 + "===================" + a1.intValue());
        System.out.println(2 + "===================" + a2.intValue());
        System.out.println(3 + "===================" + a3.intValue());
        System.out.println(4 + "===================" + a4.intValue());
        System.out.println(5 + "===================" + a5.intValue());
        System.out.println(6 + "===================" + a6.intValue());
    }

    private static void firstBuildValueArray() {
        //10,520,1360,2900,5050,10000
        total = 10000;
        valueArray = new int[10000];
        for (int i = 0; i < 10000; i++){
            if (i < 10){
                valueArray[i] = 1;
                continue;
            }
            if (i < 520){
                valueArray[i] = 2;
                continue;
            }
            if (i < 1360){
                valueArray[i] = 3;
                continue;
            }
            if (i < 2900){
                valueArray[i] = 4;
                continue;
            }
            if (i < 5050){
                valueArray[i] = 5;
                continue;
            }
            if (i < 10000){
                valueArray[i] = 6;
                continue;
            }
        }
    }

    private static void writeLog(Integer integer) {
        if (integer == 1) {
            a1.incrementAndGet();
        }
        if (integer == 2) {
            a2.incrementAndGet();
        }
        if (integer == 3) {
            a3.incrementAndGet();
        }
        if (integer == 4) {
            a4.incrementAndGet();
        }
        if (integer == 5) {
            a5.incrementAndGet();
        }
        if (integer == 6) {
            a6.incrementAndGet();
        }
    }

    //重构比例，构建标尺  【0-10】 -》1  【11-520】 -》2
    //[1,1,1,1,1,1,1,1,1,1,     2,2,2,2,2,2,2,2,2,2,2,2,2,2,...     ]
    //|                   10|                              520|
    private static void buildValueArray() throws Exception {
        int temp = 0;
        //剩余元素总量
        total = 0;
        for (Integer integer : map.keySet()) {
            total += map.get(integer);
        }
        if (total== 0){
            throw new Exception("奖品已经抽完");
        }
        valueArray = new int[total];
        for (Integer integer : map.keySet()) {
            if (map.get(integer) > 0) {
                //奖项对应的数量数量
                int num = map.get(integer);
                for (int i = temp; i < temp + num; i++) {
                    //temp到temp+num填充对应的奖项到数组
                    valueArray[i] = integer;
                }
                temp += num;
            }
        }
        System.out.println("剩余奖品总量：" + temp);
    }


    //抽奖
    public static Integer chouJiang() {
        int integer = new Random().nextInt(total);
        Integer value = 0;
        int result = valueArray[integer];
        return result;
    }

    //奖项减去1，如果某项奖品没有了，重置比例
    public static void decreAndRebuild(Integer integer) throws Exception{
        Integer integer1 = map.get(integer);
        if (integer > 0) {
            map.put(integer, integer1 - 1);
        }
        writeLog(integer);
        if (integer1 == 1) {
            //重构
            buildValueArray();
        }
    }


}
