package com.example.bikroy;

public class CurrentUser {

    public static String name="";
    public static String userID="";

    public static CurrentUser instance;

    public CurrentUser() {
        if(instance==null) {
              instance=this;
        }

    }
}
