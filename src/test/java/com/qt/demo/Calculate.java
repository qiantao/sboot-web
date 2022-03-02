package com.qt.demo;

public class Calculate {
    public static void main(String[] args) {
        int i = 3;
        System.out.println(6 >> 2);
        System.out.println(Integer.toBinaryString(10));
        System.out.println(Integer.toBinaryString(-10));
        System.out.println(Integer.toBinaryString(-10).length());
        System.out.println(Integer.toBinaryString(0));
        //00000000000000000000000000000000
        //11111111111111111111111111110110
        //10000000000000000000000000001010 原
        //11111111111111111111111111110101 反
        //11111111111111111111111111110110 补
        //00000000000000000000000000001010


        System.out.println(012);
        System.out.println(0xF);
    }
}
