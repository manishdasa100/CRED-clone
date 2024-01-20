package com.cred4.credbackend.utils;

public class LuhnValidation {
    
    public static boolean validate (String number) {

        int length = number.length();

        int total = 0;

        for (int i = 0; i < length; i++) {
            int val = number.charAt(i)-'0';
            if (i%2 == length%2) {
                val *=2;
                val = (val/10) + (val%10);
            }
            total += val;
        }

        if (total%10 == 0) return true;

        return false;
    }

}
