package com.example.bikroy;

import android.content.Context;

public class ValueCheck {

    public static boolean checkEmpty(String value) {
        if(value.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

}
