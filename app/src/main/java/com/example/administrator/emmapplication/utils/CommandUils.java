package com.example.administrator.emmapplication.utils;

import java.io.IOException;

/**
 * Created by admine on 2018/5/17.
 */

public class CommandUils {

    public static void command(String com) {
        try {
            LogUtils.i("Command : " + com);
            Runtime.getRuntime().exec(com);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
