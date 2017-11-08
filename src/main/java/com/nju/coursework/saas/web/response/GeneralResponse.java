package com.nju.coursework.saas.web.response;

import java.util.Map;

/**
 * Created by guhan on 17/11/8.
 */
public class GeneralResponse {
    private boolean success;
    private String msg;
    private Map<String,Object> data;

    public GeneralResponse(boolean success, String msg){
        this.success = success;
        this.msg = msg;
    }

    public void setData(Map<String,Object> data){
        this.data = data;
    }
}
