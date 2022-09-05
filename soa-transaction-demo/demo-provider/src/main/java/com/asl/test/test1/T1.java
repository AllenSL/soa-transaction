package com.asl.test.test1;

/**
 * 比如数组[1,3,3,3,4,5,6,7,8,8,8,8,8,9,10,10,11,11,12,23,33,33,10,2,2,2,2,2,2,1,1,1]
 */
public class T1 {


    public static void main(String[] args) {
        int[] arr = {1, 3, 3, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 9, 10, 10, 11, 11, 12, 23, 33, 33,34, 10, 2, 2, 2, 2, 2, 2, 1, 1, 1};
        int maxNum = 0;
        int num = 1;
        int a = 0;
        for (int i = 0; i < arr.length; i++) {
            a++;
            if (arr[i] > arr[i + 1]) {
                while (i > 0 && arr[i] == arr[i - 1]) {
                    i--;
                    num++;
                    a++;
                }
                maxNum = arr[i];
                break;
            }
        }
        System.out.println("maxNum: " + maxNum + " num: " + num+" a:"+a);
    }

}
