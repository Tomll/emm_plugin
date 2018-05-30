package com.example.administrator.emmapplication.execute;

import org.json.JSONException;
import org.json.JSONObject;
public
 abstract class ExecuteOption {
    JSONObject data;
    int action;
    public ExecuteOption(JSONObject data){
        this.data=data;
        try {
            this.action=data.getInt("action");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void action();
}
