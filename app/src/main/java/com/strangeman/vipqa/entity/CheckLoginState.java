package com.strangeman.vipqa.entity;

/**
 * Created by panzhi on 2017/5/19.
 */

public class CheckLoginState {
    private static User user = null;

    public static void setUser(User user) {
        CheckLoginState.user = user;
    }

    public static User getUser() {
        return user;
    }
}
