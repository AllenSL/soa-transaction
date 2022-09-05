package com.asl.test.test1;

/**
 * @author yuanyanzhao
 * @date 2022/8/9 11:37 上午
 * @description:
 */
public class MaxValue {
    //4、一个数组，里面全是数字，从左致右的特征是：从小到大，然后从大到小，数组中可能会出现连续重复的数字。
    //请写一段代码<最快>找出数组中最大的数值（最大值可能为1到多个），输出最大数字和个数
    //比如数组[1,3,3,3,4,5,6,7,8,8,8,8,8,9,10,10,11,11,12,23,33,33,10,2,2,2,2,2,2,1,1,1]
    private static int leftIndex = 0;
    private static int rightIndex = 0;

    public static void main(String[] args) {
        int[] array = {1, 3, 3, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 9, 10, 10, 11, 11, 12, 23, 33, 33, 10, 2, 2, 2, 2, 2, 2, 1, 1, 1};
        //int[] array = {1,  1, 1, 1, 1, 1, 1, 1, 1, 1};
        getMax(array);
        System.out.println(leftIndex);
        System.out.println(rightIndex);
        int i = array[leftIndex];
        int i1 = rightIndex - leftIndex;
        System.out.println("最大值=" + i + ";个数=" + (i1 + 1) );

    }

    public static void getMax(int[] arr) {

        int temp = arr[0];
        int a = 0;
        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            if (value > temp){
                leftIndex = i;
                rightIndex = i;
                temp = value;
                continue;
            }
            if (value == temp){
                rightIndex++;
            }
            if (value < temp){
                return;
            }
            a++;
        }
        System.out.println(a);
    }
}
