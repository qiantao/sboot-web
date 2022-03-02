package com.qt.demo;

public class Demo {

    public static int[] quickSort(int[] a,int first,int last){
        if(first<last){                //至少两个位置
            int pivotIndex=partition(a,first,last);  //定义pivotIndex中间位置。partition是检索这个方法
            quickSort(a,first,pivotIndex-1);              //排序左半边
            quickSort(a,pivotIndex+1,last);               //排序右半边
        }
        return a;
    }
    public static  int partition(int[] a,int left,int right){//对数组A下标从first到last中选一个主元，确定其位置，左边小于，右边大于。

        int pivot=a[left];//先定义区间数组第一个元素为主元
        int i=left;   //定义最低的索引low是first+1。比主元大一位
        int j=right;     //定义最高的索引high是last
        while(i!=j){   //当low小于high的位置时，执行以下循环
            while(a[j]>pivot&&i<j){//当high的索引上的值比主元大时，且索引大于low时
                j--;                      //寻找比主元小的值的位置索引
            }
            while(a[i]<=pivot&&i<j){//当low的索引上的值比主元小时，索引小于high时
                i++;                       //寻找比主元大的值的位置索引。
            }

            if(i<j){   //交换low和high的值
                int t=a[i];
                a[i]=a[j];
                a[j]=t;
            }
        }

        a[left]=a[j];
        a[j]=pivot;
        return j;
    }
    public static void main(String[] args) {
        int[]a=new int[]{50,10,90,30,70,40,80,60,20};
        int [] k=quickSort(a,0,a.length-1);
        for(int i=0;i<k.length;i++){
            System.out.print(k[i]+" ");
        }
    }


}
