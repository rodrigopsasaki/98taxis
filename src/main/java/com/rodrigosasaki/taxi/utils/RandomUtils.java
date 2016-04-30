package com.rodrigosasaki.taxi.utils;

public class RandomUtils {

    private RandomUtils(){
        // utility class
    }

    public static int randomNumber(int min, int max){
        int num = (int)(Math.random() * max);
        return num > min ? num : min+num;
    }

}
