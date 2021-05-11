package org.geektimes;

import java.util.Objects;

/**
 * @description
 * @autor 吴光熙
 * @date 2021/3/7  14:55
 **/
public class Test {

    public static void main(String[] args) {
        int num1 = 123;
        int num2 = 121;
        System.out.println(num1 + "是回文数？" + isFormatted(num1));
        System.out.println(num2 + "是回文数？" + isFormatted(num2));
    }

    private static boolean isFormatted(int num){
        String str = "" + num;
        if(str.length() < 1){
            return true;
        }
        int j = str.length() - 1;
        for(int i = 0; i < str.length(); i++, j--){
            if(i >= j){
                break;
            }
            if(!Objects.equals(str.charAt(i), str.charAt(j))){
                return false;
            }
        }
        return true;
    }
}
